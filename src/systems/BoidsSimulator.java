package systems;

import core.Simulator;
import core.events.Event;
import core.events.EventManager;

import entities.boid.*;

import world.Board.BoundsMode;
import world.Space;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import java.util.Set;
import java.util.HashSet;

/** Boids (discret) — héritage : Fish (proies) & Shark (prédateurs). */
public class BoidsSimulator extends Simulator<Boid, Space> {

    // Résolution logique (par cellule graphique)
    static int k = 20; 
    
    private final int nbBoids;

    // Paramètres globaux (unités = cellules logiques)
    private final int radius;          // rayon "de base" (référence)
    private final int desiredSep;      // distance souhaitée pour la séparation
    private final int maxSpeed;        // vitesse max globale (cap individuel = factor * maxSpeed)
    private final double maxForce;     // intensité max globale (cap individuel = factor * maxForce)

    // Poids des règles (globaux)
    private final double wAlign;
    private final double wCoh;
    private final double wSep;
    private final double wBound = 1.0;   // boundaries
    private final double wWander = 0.15; // bruit léger

    // Rayons différenciés (séparation/alignement/cohésion) — dérivés des globaux
    private final int rSep, rAlign, rCoh;
    private final int rSep2, rAlign2, rCoh2;

    // Perception angulaire (FOV global)
    private final boolean useFov = true;
    private final double  cosFov;

    // Ratio de prédateurs (répartition initiale)
    private final double sharksRatio = 0.015;     // ~1.5%
    private final double octopusRatio = 0.0025;   // ~0.25%

    // Couleur de base requin (constante d'espèce dans Shark)
    private static final Color SHARK_BASE = Shark.BASE_COLOR;

    // Effet visuel “eat” + cooldown (immobilisation)
    private static final int EAT_TIMER = 30;    // durée de la digestion (en dixieme de secondes)

    private final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    // --- Gestionnaire d'événements (§5.2) ---
    private final EventManager em = new EventManager();
    private static final long TICK_DT = 1L;
    // Accélération du délai sans modification GUI pour lisser le comportement des boids (on passe en 20ms, soit du x5 par rapport à la valeur par défaut).
    private final int TICK_RATE = 5;

    /** Tick périodique : exécute update() puis se replanifie à t+dt. */
    private final class Tick extends Event {
        private final long dt;
        Tick(long date, long dt) { super(date); this.dt = dt; }
        @Override public void execute() {
            update();
            em.addEvent(new Tick(em.getCurrentDate() + dt, dt));
        }
    }

    /** Fin de cooldown de digestion : le prédateur redevient actif. */
    private final class EndEatEvent extends Event {
        private final Boid boid;

        EndEatEvent(long date, Boid boid) {
            super(date);
            this.boid = boid;
        }

        @Override
        public void execute() {
            boid.endEat();
        }
    }

    public BoidsSimulator(
            int width, int height, int n, int cellSize,
            int radius, int desiredSep,
            int maxSpeed, int maxStep_IGNORED,
            double wAlign, double wCoh, double wSep) {

        super(width, height,
              Math.max(1, (height / cellSize) * k),
              Math.max(1, (width  / cellSize) * k),

              // bleu sombre océan pour le fond extérieur
              new Color(10, 20, 80),

              // Bleu clair pour l'aire jouable
              new Space(BoundsMode.BOUNCE, new Color(200, 220, 255), new Color(200, 220, 255)));

        this.nbBoids    = n;
        this.radius     = Math.max(1, radius);
        this.desiredSep = Math.max(0, desiredSep);
        this.maxSpeed   = Math.max(1, maxSpeed);
        this.maxForce   = 0.25;

        this.wAlign = wAlign;
        this.wCoh   = wCoh;
        this.wSep   = wSep;

        // Rayons différenciés
        this.rSep   = Math.max(1, this.desiredSep);
        this.rAlign = Math.max(this.rSep + 1, this.radius);
        this.rCoh   = Math.max(this.rAlign + 1, (int)Math.round(this.radius * 1.5));

        this.rSep2   = rSep   * rSep;
        this.rAlign2 = rAlign * rAlign;
        this.rCoh2   = rCoh   * rCoh;

        // FOV resserré à 120°
        double fovDeg = 120.0;
        this.cosFov = Math.cos(Math.toRadians(fovDeg));

        this.entities = new ArrayList<>(n * 2);
        init();
    }

    @Override
    protected void init() {
        entities.clear();
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        int minX = board.minXUnits(), maxX = board.maxXUnits(cols);
        int minY = board.minYUnits(), maxY = board.maxYUnits(rows);

        if (minX > maxX) { int mid = cols / 2; minX = maxX = mid; }
        if (minY > maxY) { int mid = rows / 2; minY = maxY = mid; }

        int sharks = (int)Math.round(nbBoids * sharksRatio);
        int octopuses = (int)Math.round(nbBoids * octopusRatio);

        for (int i = 0; i < nbBoids; i++) {
            int x = (minX == maxX) ? minX : rnd.nextInt(minX, maxX + 1);
            int y = (minY == maxY) ? minY : rnd.nextInt(minY, maxY + 1);

            int sx = rnd.nextInt(1, Math.min(3, maxSpeed) + 1);
            int sy = rnd.nextInt(1, Math.min(3, maxSpeed) + 1);
            int vx = rnd.nextBoolean() ? sx : -sx;
            int vy = rnd.nextBoolean() ? sy : -sy;
            if (vx == 0 && vy == 0) vx = 1;

            final boolean makeShark = (i < sharks);
            final boolean makeOctopus = (i >= sharks && i < sharks + octopuses);
            
            Color c = makeShark || makeOctopus
                    ? SHARK_BASE
                    : new Color(rnd.nextInt(40,256), rnd.nextInt(40,256), rnd.nextInt(40,256));

            if (makeShark) {
                entities.add(new Shark(x, y, vx, vy, c));
            } else if (makeOctopus) {
                entities.add(new Octopus(x, y, vx, vy, c));
            } else {
                entities.add(new Fish(x, y, vx, vy, c));
            }
        }
        // (Re)démarre la boucle événementielle
        em.restart();
        em.addEvent(new Tick(em.getCurrentDate() + 1, TICK_DT));
    }

    @Override
    public void next() {
        for (int i = 0; i < TICK_RATE; i++) {
            em.next();  // avance la date et exécute les évènements échus
        }
        draw();     // rend l'état courant
    }

    @Override
    protected void clear() {
        for (Boid B : entities) B.init_entity();
    }

    @Override
    protected void update() {
        final long now = em.getCurrentDate();
        final boolean wrap = (board.getBoundsMode() == BoundsMode.WRAP);

        final int minX = board.minXUnits(), maxX = board.maxXUnits(cols);
        final int minY = board.minYUnits(), maxY = board.maxYUnits(rows);

        final int n = entities.size();
        final int[] nextVX = new int[n];
        final int[] nextVY = new int[n];
        final int[] nextX  = new int[n];
        final int[] nextY  = new int[n];

        Set<Boid> victims = null;

        for (int i = 0; i < n; i++) {
            Boid B = entities.get(i);

            // Mise à jour interne (faim, etc.)
            B.onTick();

            int xi = B.getX(), yi = B.getY();
            int vxi = B.getVx(), vyi = B.getVy();

            // Boid "gelé" (par ex. prédateur en cooldown de digestion)
            if (B.isFrozen()) {
                B.setContinuousVelocity(0.0, 0.0);
                B.setVelocity(0, 0);
                nextVX[i] = 0; nextVY[i] = 0;
                nextX[i]  = xi; nextY[i] = yi;
                continue;
            }

            if (!B.isActive()) {
                // Dans une période de non-mise à jour : on conserve l'état en cours
                nextVX[i] = vxi;
                nextVY[i] = vyi;
                nextX[i]  = xi;
                nextY[i]  = yi;
                continue; // on passe au boid suivant
            }
            
            double vxiC = B.getContVx(), vyiC = B.getContVy();
            List<Boid> neigh = entities;

            // ---------- PANIC + anti-pack (polymorphe) ----------
            double panic = 0.0;
            double fleeX = 0.0, fleeY = 0.0;

            final int rPanic  = Math.max(1, (int)Math.round(B.panicRadiusFactor() * rAlign));
            final int rPanic2 = rPanic * rPanic;

            final int rHH  = (int)Math.round(B.antiCentroidRadMult() * rAlign);
            final int rHH2 = rHH * rHH;

            double cx = 0.0, cy = 0.0, wCentroid = 0.0;

            for (Boid b : neigh) {
                if (b == B) continue;

                int bx = b.getX();
                int by = b.getY();

                // Vecteur de moi vers la menace pour la panique
                int dx = xi - bx;
                int dy = yi - by;
                int d2 = dx*dx + dy*dy;

                // PANIC : intensité locale en fonction de la sensibilité au voisin
                if (d2 == 0) {
                    double danger = B.panicSensitivity(b);
                    if (danger > 0.0) panic = 1.0;
                } else if (d2 <= rPanic2) {
                    double danger = B.panicSensitivity(b);
                    if (danger > 0.0) {
                        double d = Math.sqrt(d2);
                        double base = 1.0 - (d / (double) rPanic);
                        if (base > 0.0) {
                            double local = base * danger;
                            if (local > panic) panic = local;

                            double d3 = d * d * d + 1e-9;
                            fleeX += (dx / d3) * danger;
                            fleeY += (dy / d3) * danger;
                        }
                    }
                }

                // ANTI-PACK : centroïde des voisins "pertinents" pour l'anti-agglomération
                int dxC = bx - xi;
                int dyC = by - yi;
                int d2C = dxC*dxC + dyC*dyC;
                if (d2C > 0 && d2C <= rHH2) {
                    double w = B.antiPackWeight(b);
                    if (w > 0.0) {
                        cx += bx * w;
                        cy += by * w;
                        wCentroid += w;
                    }
                }
            }

            if (panic > 1.0) panic = 1.0;

            double[] fAntiCentroid = Z();
            double antiCentroidGain = B.antiCentroidGain();
            if (wCentroid > 0.0 && antiCentroidGain != 0.0) {
                cx /= wCentroid;
                cy /= wCentroid;
                double ax = (xi - cx), ay = (yi - cy);
                double an = Math.hypot(ax, ay);
                if (an > 0) {
                    int speedCap = Math.max(1, maxSpeed);
                    ax = ax * speedCap / an;
                    ay = ay * speedCap / an;
                    fAntiCentroid = limitByIndividual(
                        antiCentroidGain * (ax - B.getVx()),
                        antiCentroidGain * (ay - B.getVy()),
                        B
                    );
                }
            }

            // ---------- Chasse longue portée (polymorphe) ----------
            double huntX = 0.0, huntY = 0.0;
            int     huntCnt = 0;

            double huntFactor = B.huntRadiusFactor();
            double huntRadius  = (huntFactor > 0.0) ? huntFactor * rCoh : 0.0;
            double huntRadius2 = huntRadius * huntRadius;

            double huntGain = B.huntGlobalGain();

            if (huntRadius > 0.0 && huntGain > 0.0) {
                for (Boid b : neigh) {
                    if (b == B) continue;

                    double wTarget = B.huntAttraction(b);
                    if (wTarget <= 0.0) continue;

                    double dx = (double)b.getX() - xi;
                    double dy = (double)b.getY() - yi;
                    double d2 = dx*dx + dy*dy;
                    if (d2 == 0.0 || d2 > huntRadius2) continue;

                    // Pas de FOV ici : la chasse longue portée "voit" à 360°
                    double d = Math.sqrt(d2) + B.huntDistEps();
                    double w = wTarget / d;  // plus près => plus fort

                    huntX += dx * w;
                    huntY += dy * w;
                    huntCnt++;
                }
            }

            double[] fHunt = Z();
            double[] fOrbit = Z();

            // ------- Focus + Eat (polymorphe) -------
            Boid   target    = null;
            int    bestD2    = Integer.MAX_VALUE;
            double bestScore = 0.0;

            int focusR  = Math.max(rAlign * 2, rCoh);
            int focusR2 = focusR * focusR;

            for (Boid b : neigh) {
                if (b == B) continue;

                int dx = b.getX() - xi;
                int dy = b.getY() - yi;
                int d2 = dx*dx + dy*dy;
                if (d2 > focusR2) continue;

                double desire = B.biteDesire(b);
                if (desire <= 0.0) continue; // ce voisin n'est pas une proie

                if (d2 == 0) {
                    // Proie sur la même case → choix immédiat
                    target = b;
                    bestD2 = 0;
                    break;
                }

                // Score = désir pondéré par la proximité (plus près ⇒ mieux).
                double score = desire / (d2 + 1.0);
                if (score > bestScore) {
                    bestScore = score;
                    bestD2    = d2;
                    target    = b;
                }
            }

            if (target != null) {
                // Steering renforcé vers la cible choisie
                double[] fFocus = steerSeek(B, target.getX(), target.getY());
                double focusGain = B.focusGain();
                fHunt[0] += focusGain * fFocus[0];
                fHunt[1] += focusGain * fFocus[1];

                // "Morsure" uniquement si même case (contact strict)
                if (bestD2 == 0) {
                    if (victims == null) victims = new HashSet<>();
                    victims.add(target);

                    B.startEat();
                    long endDate = em.getCurrentDate() + EAT_TIMER * 5L;
                    em.addEvent(new EndEatEvent(endDate, B));

                    nextVX[i] = 0; nextVY[i] = 0;
                    nextX[i]  = xi; nextY[i] = yi;
                    continue;
                }
            }

            // ---------- Forces locales ----------
            double[] fAlign = steerAlignment(B, neigh, rAlign2, rAlign, vxiC, vyiC);
            double[] fCoh   = steerCohesion (B, neigh, rCoh2,   rCoh,   vxiC, vyiC);
            double[] fSep   = steerSeparation(B, neigh, rSep2);

            double[] fBound  = steerBoundaries(B, minX, minY, maxX, maxY, 2);
            double[] fWander = smallWander();

            // ---------- Fuite de panique ----------
            double[] fPanicVec = Z();
            double panicFleeGain   = B.panicFleeGain();
            double panicSpeedBoost = B.panicSpeedBoost();
            double panicJitterGain = B.panicJitterGain();

            if (panic > 0.0 && panicFleeGain > 0.0) {
                double norm = Math.hypot(fleeX, fleeY);

                int speedCap = Math.max(1, (int)Math.round(
                    maxSpeed * (1.0 + panicSpeedBoost * panic)
                ));

                double px = 0.0, py = 0.0;
                if (norm > 0) {
                    px = fleeX * speedCap / norm;
                    py = fleeY * speedCap / norm;
                }
                double ang = rnd.nextDouble(0.0, 2.0 * Math.PI);
                double jx  = Math.cos(ang) * speedCap * panicJitterGain * panic;
                double jy  = Math.sin(ang) * speedCap * panicJitterGain * panic;
                fPanicVec = limitByIndividual(
                    (px + jx) - B.getVx(),
                    (py + jy) - B.getVy(),
                    B
                );
            }

            // ---------- Chasse longue portée + Décorrélation ----------
            if (huntCnt > 0 && huntGain > 0.0) {
                double hNorm = Math.hypot(huntX, huntY);
                if (hNorm > 0.0) {
                    double packBoost = Math.pow(huntCnt, B.huntPackExp());
                    int speedCap = Math.max(1, maxSpeed);
                    double hx = (huntX / hNorm) * speedCap;
                    double hy = (huntY / hNorm) * speedCap;


                    double hungerFactor = 1.0 + B.hungerLevel();

                    double[] addHunt = limitByIndividual(
                        huntGain * hungerFactor * packBoost * (hx - B.getVx()),
                        huntGain * hungerFactor * packBoost * (hy - B.getVy()),
                        B
                    );
                    fHunt[0] += addHunt[0];
                    fHunt[1] += addHunt[1];

                    int sign = ((System.identityHashCode(B) & 1) == 0) ? 1 : -1;
                    double ox = -hy * sign;
                    double oy =  hx * sign;

                    double orbitGainLocal = B.orbitGain() * (0.5 + 0.5 * (1.0 / (1.0 + hNorm)));
                    orbitGainLocal = Math.max(B.orbitFarDamp(), orbitGainLocal);
                    orbitGainLocal *= hungerFactor; // plus faim => orbite plus marquée

                    fOrbit = limitByIndividual(orbitGainLocal * ox, orbitGainLocal * oy, B);
                }
            }

            // ---------- Combinaison pondérée ----------
            double alignScale = 1.0 - panic;
            double cohScale   = 1.0 - panic;
            double panicGain  = panicFleeGain * panic;

            double ax = alignScale * wAlign * fAlign[0]
                      + cohScale   * wCoh   * fCoh[0]
                      + wSep   * fSep[0]
                      + wBound * fBound[0]
                      + wWander * fWander[0]
                      + panicGain * fPanicVec[0]
                      + fHunt[0] + fOrbit[0] + fAntiCentroid[0];

            double ay = alignScale * wAlign * fAlign[1]
                      + cohScale   * wCoh   * fCoh[1]
                      + wSep   * fSep[1]
                      + wBound * fBound[1]
                      + wWander * fWander[1]
                      + panicGain * fPanicVec[1]
                      + fHunt[1] + fOrbit[1] + fAntiCentroid[1];

            // Intégration + caps
            double svx = vxi + ax, svy = vyi + ay;
            int speedCap = Math.max(1, maxSpeed);
            double vmax2 = (double)speedCap * (double)speedCap;
            double s2 = svx*svx + svy*svy;
            if (s2 > vmax2 && s2 > 0) {
                double k = Math.sqrt(vmax2 / s2);
                svx *= k; svy *= k;
            }
            svx *= 0.95; svy *= 0.95;

            B.setContinuousVelocity(svx, svy);
            int nvx = B.stepX(svx);
            int nvy = B.stepY(svy);
            if (nvx == 0 && nvy == 0) {
                if (Math.abs(svx) > Math.abs(svy)) nvx = (svx >= 0 ? 1 : -1);
                else if (Math.abs(svy) > 0)        nvy = (svy >= 0 ? 1 : -1);
            }

            int nx = xi + nvx, ny = yi + nvy;
            if (wrap) {
                if (nx < minX) nx = maxX; else if (nx > maxX) nx = minX;
                if (ny < minY) ny = maxY; else if (ny > maxY) ny = minY;
            } else {
                if (nx < minX || nx > maxX) { nx = Math.max(minX, Math.min(nx, maxX)); nvx = -nvx; }
                if (ny < minY || ny > maxY) { ny = Math.max(minY, Math.min(ny, maxY)); nvy = -nvy; }
            }

            nextVX[i] = nvx; nextVY[i] = nvy;
            nextX[i]  = nx;  nextY[i]  = ny;
            B.setVelocity(nvx, nvy);
        }

        for (int i = 0; i < n; i++) {
            Boid B = entities.get(i);
            B.setVelocity(nextVX[i], nextVY[i]);
            B.setPosition(nextX[i], nextY[i]);
        }

        // Suppression des victimes "mangées"
        if (victims != null && !victims.isEmpty()) {
            entities.removeIf(victims::contains);
        }
    }

    // --------------------- Steering helpers (qui consultent les comportements polymorphes) ---------------------

    private double[] steerAlignment(Boid B, List<Boid> neigh, int r2, int r, double vxiC, double vyiC) {
        double sumVx = 0.0, sumVy = 0.0, sumW = 0.0;
        int xi = B.getX(), yi = B.getY();

        for (Boid b : neigh) {
            if (b == B) continue;

            int dx = b.getX() - xi;
            int dy = b.getY() - yi;
            int d2 = dx*dx + dy*dy;
            if (d2 == 0 || d2 > r2) continue;
            if (useFov && !inFov(dx, dy, vxiC, vyiC)) continue;

            double d = Math.sqrt(d2);
            double w = 1.0 - (d / (double) r);   // poids géométrique
            if (w <= 0.0) continue;

            // Poids social polymorphe (Fish/Shark décident comment ils s’alignent)
            double social = B.alignment(b);
            if (social <= 0.0) continue;         // <=0 ⇒ on ignore ce voisin pour l’alignement

            w *= social;

            sumVx += b.getContVx() * w;
            sumVy += b.getContVy() * w;
            sumW  += w;
        }

        if (sumW <= 0.0) return Z();

        double dvx = sumVx / sumW;
        double dvy = sumVy / sumW;
        double n   = Math.hypot(dvx, dvy);

        if (n > 0.0) {
            int speedCap = Math.max(1, maxSpeed);
            dvx = dvx * speedCap / n;
            dvy = dvy * speedCap / n;
        }

        double sx = dvx - B.getVx();
        double sy = dvy - B.getVy();
        return limitByIndividual(sx, sy, B);
    }

    private double[] steerCohesion(Boid B, List<Boid> neigh, int r2, int r, double vxiC, double vyiC) {
        double sumX = 0.0, sumY = 0.0, sumW = 0.0;
        int xi = B.getX(), yi = B.getY();

        for (Boid b : neigh) {
            if (b == B) continue;

            int dx = b.getX() - xi;
            int dy = b.getY() - yi;
            int d2 = dx*dx + dy*dy;
            if (d2 == 0 || d2 > r2) continue;
            if (useFov && !inFov(dx, dy, vxiC, vyiC)) continue;

            double d = Math.sqrt(d2);

            // poids géométrique (plus proche => plus fort)
            double w = 1.0 - (d / (double) r);
            if (w <= 0.0) continue;

            // poids social polymorphe (Fish/Shark décident comment ils se regroupent)
            double social = B.cohesion(b);
            if (social <= 0.0) continue;  // 0 ou négatif => on ignore ce voisin dans la cohésion

            w *= social;

            sumX += b.getX() * w;
            sumY += b.getY() * w;
            sumW += w;
        }

        if (sumW <= 0.0) return Z();

        // centre de masse pondéré des voisins pertinents
        double cx = (sumX / sumW) - xi;
        double cy = (sumY / sumW) - yi;
        double n  = Math.hypot(cx, cy);

        if (n > 0.0) {
            int speedCap = Math.max(1, maxSpeed);
            cx = cx * speedCap / n;
            cy = cy * speedCap / n;
        }

        double sx = cx - B.getVx();
        double sy = cy - B.getVy();
        return limitByIndividual(sx, sy, B);
    }

    private double[] steerSeparation(Boid B, List<Boid> neigh, int r2) {
        double rx = 0.0, ry = 0.0;
        int xi = B.getX(), yi = B.getY();

        for (Boid b : neigh) {
            if (b == B) continue;

            int dx = xi - b.getX();
            int dy = yi - b.getY();
            int d2 = dx*dx + dy*dy;
            if (d2 == 0 || d2 > r2) continue;

            double d = Math.sqrt(d2);

            // Rayon de séparation "effectif" pour le couple (B, b)
            double localSep = rSep * B.separationRadius(b);

            if (d <= Math.max(1.0, localSep)) {
                double d3 = d * d * d + 1e-9;

                // Poids de peur polymorphe : how much B veut s'écarter de ce voisin-là
                double fear = b.getFearWeight() * B.separation(b);

                rx += (dx / d3) * fear;
                ry += (dy / d3) * fear;
            }
        }

        double n = Math.hypot(rx, ry);
        if (n == 0.0) return Z();

        int speedCap = Math.max(1, maxSpeed);
        rx = rx * speedCap / n;
        ry = ry * speedCap / n;

        double sx = rx - B.getVx();
        double sy = ry - B.getVy();
        return limitByIndividual(sx, sy, B);
    }

    private double[] steerBoundaries(Boid B, int minX, int minY, int maxX, int maxY, int offset) {
        double sx = 0.0, sy = 0.0;
        int x = B.getX(), y = B.getY();

        if (x < minX + offset) {
            double[] f = steerSeek(B, minX + offset, y);
            sx += f[0]; sy += f[1];
        } else if (x > maxX - offset) {
            double[] f = steerSeek(B, maxX - offset, y);
            sx += f[0]; sy += f[1];
        }
        if (y < minY + offset) {
            double[] f = steerSeek(B, x, minY + offset);
            sx += f[0]; sy += f[1];
        } else if (y > maxY - offset) {
            double[] f = steerSeek(B, x, maxY - offset);
            sx += f[0]; sy += f[1];
        }
        return limitByIndividual(sx, sy, B);
    }

    private double[] smallWander() {
        double jx = rnd.nextDouble(-1.0, 1.0);
        double jy = rnd.nextDouble(-1.0, 1.0);
        return limit(jx, jy, maxForce * 0.5);
    }

    private double[] steerSeek(Boid B, double tx, double ty) {
        double dx = tx - B.getX(), dy = ty - B.getY();
        double n = Math.hypot(dx, dy);
        if (n == 0) return Z();

        int speedCap = Math.max(1, maxSpeed);
        double dvx = dx * speedCap / n, dvy = dy * speedCap / n;
        double sx  = dvx - B.getVx(),  sy  = dvy - B.getVy();
        return limitByIndividual(sx, sy, B);
    }

    // --------------------- utilitaires ---------------------

    private boolean inFov(int dx, int dy, double vxiC, double vyiC) {
        double s2v = vxiC*vxiC + vyiC*vyiC;
        double s2d = (double)dx*dx  + (double)dy*dy;
        if (s2v == 0.0 || s2d == 0.0) return true; // si vitesse nulle, ignorer FOV
        double dot = (dx * vxiC + dy * vyiC) / (Math.sqrt(s2d) * Math.sqrt(s2v));
        return dot >= cosFov;
    }

    private double[] limitByIndividual(double x, double y, Boid B) {
        double n = Math.hypot(x, y);
        double cap = maxForce * B.getForceScale();
        if (n > cap && n > 0) { x *= cap / n; y *= cap / n; }
        return new double[]{x, y};
    }

    private static double[] limit(double x, double y, double m) {
        double n = Math.hypot(x, y);
        if (n > m && n > 0) { x *= m / n; y *= m / n; }
        return new double[]{x, y};
    }

    private static double[] Z() { return new double[]{0.0, 0.0}; }
}
