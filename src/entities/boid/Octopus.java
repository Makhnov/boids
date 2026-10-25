package entities.boid;

import images.ParticleIcon;
import images.OctopusIcon;

import gui.GUISimulator;

import java.awt.*;

/**
 * Boid prédateur ultime (poulpe géant).
 * Très lent, extrêmement intimidant, forte portée de chasse.
 *
 * Utilise le même pattern d'event manager que Shark :
 *  - hungerLevel / onTick
 *  - startEat / endEat / isFrozen
 * Le BoidsSimulator se charge de programmer les événements via EventManager.
 */
public class Octopus extends Boid {

    // --------- Vitesse ---------
    private static final int TICK_NUM = 1;
    private static final int TICK_DEN = 7;

    // --------- Rendu ---------
    private static final int    MIN_SPRITE_PX      = 12;
    private static final double SPRITE_SCALE       = 7.0;
    private static final int    MAX_SPRITE_PX      = 64;
    private static final int    MIN_ABS_SPRITE_PX  = 32;

    /** Couleur "normale" du poulpe : rouge rouille foncé */
    public  static final Color  BASE_COLOR         = new Color(115, 42, 10);

    /** Couleur pendant qu'il mange. : rouge rouille */
    private static final Color  EAT_COLOR          = new Color(185, 65, 14);

    // --------- Social / séparation / prédation ---------

    // Alignement :
    // - avec des proies / boids peu prédatifs : léger alignement
    // - avec des gros prédateurs : quasi aucun alignement
    private static final double ALIGN_WITH_PREY        = 0.3;
    private static final double ALIGN_WITH_BIG_PRED    = 0.0;

    // Cohésion :
    // - vers les proies : attraction forte (pour concentrer la chasse)
    // - vers gros prédateurs : anti-cohésion très forte
    private static final double ATTR_TO_PREY           = 5.0;
    private static final double ANTI_COH_WITH_BIG_PRED = -15.0;

    // Séparation :
    // - rayon de base légèrement augmenté
    // - très grand rayon vis-à-vis des gros prédateurs
    private static final double BASE_RSEP_MULT         = 1.5;
    private static final double BIG_PRED_RSEP_MULT     = 8.0;

    // Peur ressentie par les autres gros prédateurs (quand CE poulpe les considère comme menaçants)
    private static final double BIG_PRED_FEAR_MULT     = 15.0;

    // --------- Chasse / meute ---------
    private static final double OCTO_HUNT_GAIN          = 12.0;
    private static final double OCTO_ORBIT_GAIN         = 0.6;
    private static final double OCTO_ANTI_CENTROID_GAIN = 3.0;
    private static final double OCTO_FOCUS_GAIN         = 3.5;

    // --------- Panique (pour CE poulpe) ---------
    // Il craint un peu les autres gros prédateurs, mais beaucoup moins que Fish/Shark.
    private static final double OCTO_PANIC_RADIUS_FACTOR = 1.2;

    // --------- Faim / état interne ---------
    private boolean eating = false;
    private double hunger  = 0.0;

    private static final double HUNGER_INC_PER_TICK = 0.015;  // monte lentement
    private static final double HUNGER_MAX          = 4.0;

    /** Octopus par défaut : très lent, mais énorme fearWeight. */
    public Octopus(int x, int y, int vx, int vy, Color color) {
        // speedScale = 0.25, forceScale = 0.7, fearWeight = 50.0        
        super(x, y, vx, vy, color, 0.25, 0.70, 50.0);
        setTickPeriod(TICK_NUM, TICK_DEN);
    }

    /**
     * Octopus avec paramètres individuels explicites.
     *
     * @param x          position x initiale
     * @param y          position y initiale
     * @param vx         vitesse x initiale
     * @param vy         vitesse y initiale
     * @param color      couleur d'affichage
     * @param speedScale facteur multiplicatif sur la vitesse max globale
     * @param forceScale facteur multiplicatif sur la force max globale
     * @param fearWeight intimidation ressentie par les autres boids
     */
    public Octopus(int x, int y, int vx, int vy, Color color,
                   double speedScale, double forceScale, double fearWeight) {
        super(x, y, vx, vy, color, speedScale, forceScale, fearWeight);
        setTickPeriod(TICK_NUM, TICK_DEN);
    }

    // --------- Chasse rapprochée (morsure) ---------

    /**
     * Désir de morsure : très agressif, proportionnel à l'écart d'intimidation et à la faim.
     *
     * @param other voisin considéré comme cible potentielle
     * @return désir de morsure (0 si la cible intimide trop)
     */
    @Override
    public double biteDesire(Boid other) {
        // Même principe que Shark, mais plus agressif :
        // il peut s'attaquer à pratiquement tout ce qui a un intimidation plus faible.
        double myIntimidation    = this.getFearWeight();
        double theirIntimidation = other.getFearWeight();

        double margin = myIntimidation - theirIntimidation;
        if (margin <= 0.0) {
            return 0.0;
        }

        double baseDesire   = margin / myIntimidation;
        double hungerFactor = 1.0 + 1.5 * hunger; // plus sensible à la faim que le requin

        return baseDesire * hungerFactor;
    }

    // --------- Chasse longue portée ---------

    /** {@inheritDoc} */
    @Override
    public double huntRadiusFactor() {
        // Rayon de chasse très large en multiples de rCoh.
        return 3.0;
    }

    /**
     * Attire le poulpe vers les boids qui paniquent face à lui, pondéré par sa faim.
     *
     * @param other voisin évalué
     * @return attraction de chasse longue portée (>0 si c'est une proie)
     */
    @Override
    public double huntAttraction(Boid other) {
        // Proxy de "proie" : ceux qui ont PEUR de moi.
        double fearOfMe = other.panicSensitivity(this);
        if (fearOfMe <= 0.0) return 0.0;

        double hungerFactor = 1.0 + hunger;
        return fearOfMe * hungerFactor;
    }

    /** {@inheritDoc} */
    @Override
    public double huntGlobalGain() {
        return OCTO_HUNT_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double orbitGain() {
        return OCTO_ORBIT_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double antiCentroidGain() {
        return OCTO_ANTI_CENTROID_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double focusGain() {
        return OCTO_FOCUS_GAIN;
    }

    // --------- Affinités sociales / attraction ---------

    /**
     * Alignement : léger avec les proies, quasi nul avec les très gros prédateurs.
     *
     * @param other voisin considéré
     * @return facteur d'alignement interpolé entre ALIGN_WITH_PREY et ALIGN_WITH_BIG_PRED
     */
    @Override
    public double alignment(Boid other) {
        double p = other.predatorScore(); // 0..1

        // p = 0 -> ALIGN_WITH_PREY (légère tendance à se caler sur les proies / petits boids)
        // p = 1 -> ALIGN_WITH_BIG_PRED (autres gros prédateurs : pas d'alignement)
        return ALIGN_WITH_PREY + p * (ALIGN_WITH_BIG_PRED - ALIGN_WITH_PREY);
    }

    /**
     * Cohésion ou anti-cohésion selon la dangerosité du voisin.
     *
     * @param other voisin considéré
     * @return facteur de cohésion interpolé entre ATTR_TO_PREY et ANTI_COH_WITH_BIG_PRED
     */
    @Override
    public double cohesion(Boid other) {
        double p = other.predatorScore();

        // p = 0 -> ATTR_TO_PREY    (forte attraction vers proies / petites cibles)
        // p = 1 -> ANTI_COH_WITH_BIG_PRED (repousse les gros prédateurs)
        return ATTR_TO_PREY + p * (ANTI_COH_WITH_BIG_PRED - ATTR_TO_PREY);
    }

    /**
     * Rayon de séparation plus large avec les gros prédateurs.
     *
     * @param other voisin considéré
     * @return multiplicateur de rayon de séparation
     */
    @Override
    public double separationRadius(Boid other) {
        double p = other.predatorScore();
        // p = 0 -> BASE_RSEP_MULT ; p = 1 -> BIG_PRED_RSEP_MULT
        return BASE_RSEP_MULT + p * (BIG_PRED_RSEP_MULT - BASE_RSEP_MULT);
    }

    /**
     * Renforce la séparation proportionnellement à la dangerosité du voisin.
     *
     * @param other voisin considéré
     * @return multiplicateur de séparation
     */
    @Override
    public double separation(Boid other) {
        double p = other.predatorScore();
        // p = 0 -> 1.0 ; p = 1 -> BIG_PRED_FEAR_MULT
        return 1.0 + p * (BIG_PRED_FEAR_MULT - 1.0);
    }

    // --------- Panique / foule ---------

    /** {@inheritDoc} */
    @Override
    public double panicRadiusFactor() {
        // Il garde une certaine méfiance vis-à-vis des autres très gros prédateurs.
        return OCTO_PANIC_RADIUS_FACTOR;
    }

    /** {@inheritDoc} */
    @Override
    public double hungerLevel() {
        return hunger;
    }

    /** {@inheritDoc} */
    @Override
    public void onTick() {
        if (!eating) {
            hunger += HUNGER_INC_PER_TICK;
            if (hunger > HUNGER_MAX) hunger = HUNGER_MAX;
        }
    }

    /**
     * Sensibilité limitée face aux prédateurs dangereux : reste faible mais non nulle.
     *
     * @param other voisin évalué
     * @return niveau de menace perçu (0 si aucune menace)
     */
    @Override
    public double panicSensitivity(Boid other) {
        double p = other.predatorScore();
        if (p <= 0.0) return 0.0;

        double intimidation = other.getFearWeight();
        double hungerOther  = 1.0 + other.hungerLevel();

        // Panique limitée (c'est l'ultime prédateur), mais non nulle face à un autre "monstre".
        double base = p * intimidation * hungerOther;
        return 0.3 * base;
    }

    /**
     * Poids utilisé dans l'anti-pack : accentue fortement l'effet des très gros prédateurs.
     *
     * @param other voisin considéré
     * @return poids quadratique sur le score de prédation
     */
    @Override
    public double antiPackWeight(Boid other) {
        // Les boids très prédatifs comptent beaucoup dans le centroïde à fuir.
        double p = other.predatorScore();
        return p * p; // accentue les très gros prédateurs
    }

    // --------- Morsure / récupération (EventManager) ---------

    /** {@inheritDoc} */
    @Override
    public boolean isFrozen() {
        // Pendant la phase de récupération après une morsure, le poulpe est "gelé".
        return eating;
    }

    /** {@inheritDoc} */
    @Override
    public void startEat() {
        eating  = true;
        hunger  = 0.0;           // vient de manger : plus faim
        setContinuousVelocity(0.0, 0.0);
        setVelocity(0, 0);
        setColor(EAT_COLOR);
        // Le BoidsSimulator planifie ensuite un Event endEat(...) via EventManager.
    }

    /** {@inheritDoc} */
    @Override
    public void endEat() {
        eating = false;
        setColor(BASE_COLOR);
    }

    // --------- Rendu ---------

    /**
     * Dessine le poulpe en orientant le sprite sur la vitesse continue
     * et en adaptant la taille à la grille.
     *
     * @param gui interface graphique cible
     */
    @Override
    public final void draw(GUISimulator gui) {
        final int s    = SIZE;
        final int cellX = offX() + getX() * s;
        final int cellY = offY() + getY() * s;

        int sprite = (int)Math.round(s * SPRITE_SCALE);
        if (sprite < MIN_SPRITE_PX) sprite = MIN_SPRITE_PX;
        sprite = Math.min(sprite, MAX_SPRITE_PX);
        if (MIN_ABS_SPRITE_PX > 0) sprite = Math.max(sprite, MIN_ABS_SPRITE_PX);

        final int cx = cellX + s / 2;
        final int cy = cellY + s / 2;

        double theta = (Math.abs(cvx) < 1e-9 && Math.abs(cvy) < 1e-9)
                ? 0.0 : Math.atan2(cvy, cvx);

        OctopusIcon icon = new OctopusIcon();
        icon.setFill(getColor());
        icon.setDimension(new Dimension(sprite, sprite));

        gui.addGraphicalElement(new ParticleIcon(cx, cy, icon, theta));
    }
}
