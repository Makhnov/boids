package entities.boid;

import images.ParticleIcon;
import images.SharkIcon;

import gui.GUISimulator;

import java.awt.*;

/** Boid prédateur (requin). */
public class Shark extends Boid {

    // --------- Vitesse ---------
    private static final int TICK_NUM = 1;
    private static final int TICK_DEN = 6;

    // --------- Rendu ---------
    private static final int    MIN_SPRITE_PX      = 9;

    public  static final Color  BASE_COLOR         = new Color(0, 0, 0);
    private static final double SPRITE_SCALE       = 5.0;
    private static final int    MAX_SPRITE_PX      = 48;
    private static final int    MIN_ABS_SPRITE_PX  = 24;

    /** Couleur temporaire lors d'une morsure. */
    private static final Color EAT_COLOR = new Color(220, 30, 30);

    // --------- Social ---------
    private static final double ALIGN_CROSS_KIND   = 0.6;
    private static final double SAME_ALIGN_FACTOR  = 0.0;   // pas d'alignement entre gros prédateurs
    private static final double SAME_COH_FACTOR    = -10.0; // anti-cohésion entre gros prédateurs
    private static final double SAME_RSEP_MULT     = 5.0;   // rayon de séparation ×5
    private static final double SAME_FEAR_MULT     = 10.0;  // peur mutuelle forte
    private static final double ATTR_TO_OTHER      = 4.0;   // attraction vers proies (cohésion locale)

    // --------- Chasse / meute ---------
    private static final double SHARK_HUNT_GAIN          = 8.0;
    private static final double SHARK_ORBIT_GAIN         = 0.8;
    private static final double SHARK_ANTI_CENTROID_GAIN = 2.0;
    private static final double SHARK_FOCUS_GAIN         = 2.5;

    // --------- Faim / état ---------
    /** Vrai si le requin est en phase de récupération après une morsure. */
    private boolean eating = false;

    /** Faim croissante : augmente sa dangerosité et son comportement d'évitement. */
    private double hunger = 0.0;
    private static final double HUNGER_INC_PER_TICK = 0.02;
    private static final double HUNGER_MAX          = 3.0;

    /** Shark par défaut (lent, force un peu réduite, très “effrayant”). */
    public Shark(int x, int y, int vx, int vy, Color color) {
        super(x, y, vx, vy, color, 0.60, 0.75, 20.0);
        setTickPeriod(TICK_NUM, TICK_DEN);
    }

    /**
     * Shark avec paramètres individuels explicites.
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
    public Shark(int x, int y, int vx, int vy, Color color, double speedScale, double forceScale, double fearWeight) {
        super(x, y, vx, vy, color, speedScale, forceScale, fearWeight);
        setTickPeriod(TICK_NUM, TICK_DEN);
    }

    // --------- Chasse rapprochée ---------

    /**
     * Évalue la priorité de morsure d'un voisin en fonction de l'écart d'intimidation
     * et de la faim du requin.
     *
     * @param other voisin évalué comme proie potentielle
     * @return désir de morsure (0 si la cible est trop intimidante)
     */
    @Override
    public double biteDesire(Boid other) {
        double myIntimidation    = this.getFearWeight();
        double theirIntimidation = other.getFearWeight();

        double margin = myIntimidation - theirIntimidation;
        if (margin <= 0.0) {
            return 0.0;
        }

        double baseDesire   = margin / myIntimidation;
        double hungerFactor = 1.0 + hunger;

        return baseDesire * hungerFactor;
    }

    // --------- Chasse longue portée ---------

    /** {@inheritDoc} */
    @Override
    public double huntRadiusFactor() {
        // Rayon de chasse "logique" en multiples de rCoh.
        return 2.5;
    }

    /**
     * Attire le requin vers les boids intimidés par lui, pondéré par sa faim.
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
        return SHARK_HUNT_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double orbitGain() {
        return SHARK_ORBIT_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double antiCentroidGain() {
        return SHARK_ANTI_CENTROID_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double focusGain() {
        return SHARK_FOCUS_GAIN;
    }

    // --------- Affinités sociales / attraction ---------

    /**
     * Ajuste l'alignement : faible avec les gros prédateurs, modéré avec les proies.
     *
     * @param other voisin considéré
     * @return facteur d'alignement interpolé entre ALIGN_CROSS_KIND et SAME_ALIGN_FACTOR
     */
    @Override
    public double alignment(Boid other) {
        double p = other.predatorScore(); // 0..1

        // p = 0 → ALIGN_CROSS_KIND (proies / petits boids)
        // p = 1 → SAME_ALIGN_FACTOR (autres gros prédateurs : pas d'alignement)
        return ALIGN_CROSS_KIND + p * (SAME_ALIGN_FACTOR - ALIGN_CROSS_KIND);
    }

    /**
     * Cohésion : attraction vers les proies, répulsion entre gros prédateurs.
     *
     * @param other voisin considéré
     * @return facteur de cohésion interpolé entre ATTR_TO_OTHER et SAME_COH_FACTOR
     */
    @Override
    public double cohesion(Boid other) {
        double p = other.predatorScore();

        // p = 0 → ATTR_TO_OTHER (proies : on est attiré)
        // p = 1 → SAME_COH_FACTOR (gros prédateurs : anti-cohésion)
        return ATTR_TO_OTHER + p * (SAME_COH_FACTOR - ATTR_TO_OTHER);
    }

    /**
     * Agrandit le rayon de séparation face aux boids très prédateurs.
     *
     * @param other voisin considéré
     * @return multiplicateur de rayon de séparation
     */
    @Override
    public double separationRadius(Boid other) {
        double p = other.predatorScore();
        // p = 0 → rayon normal ; p = 1 → SAME_RSEP_MULT
        return 1.0 + p * (SAME_RSEP_MULT - 1.0);
    }

    /**
     * Augmente la force de séparation en fonction de la dangerosité du voisin.
     *
     * @param other voisin considéré
     * @return multiplicateur de séparation
     */
    @Override
    public double separation(Boid other) {
        double p = other.predatorScore();
        // p = 0 → 1.0 ; p = 1 → SAME_FEAR_MULT
        return 1.0 + p * (SAME_FEAR_MULT - 1.0);
    }

    // --------- Panique / foule ---------

    /** {@inheritDoc} */
    @Override
    public double panicRadiusFactor() {
        // rayon de "méfiance" vis-à-vis des autres gros prédateurs
        return 1.5;
    }

    /** {@inheritDoc} */
    @Override
    public double hungerLevel() {
        return hunger;
    }

    /**
     * Augmente progressivement la faim quand le requin n'est pas en train de manger.
     */
    @Override
    public void onTick() {
        if (!eating) {
            hunger += HUNGER_INC_PER_TICK;
            if (hunger > HUNGER_MAX) hunger = HUNGER_MAX;
        }
    }

    /**
     * Sensibilité à la panique vis-à-vis d'un voisin prédateur.
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

        // Peur proportionnelle à la dangerosité prédatrice * intimidation * faim de l'autre
        return p * intimidation * hungerOther;
    }

    /**
     * Poids d'un voisin dans le centroïde d'anti-pack : augmente avec son score de prédation.
     *
     * @param other voisin considéré
     * @return poids utilisé pour repousser les regroupements de prédateurs
     */
    @Override
    public double antiPackWeight(Boid other) {
        // Plus l'autre est "gros prédateur", plus il compte dans le centroïde à fuir.
        return other.predatorScore();
    }

    // --------- Morsure / récupération ---------

    /** {@inheritDoc} */
    @Override
    public boolean isFrozen() {
        return eating;
    }

    /** {@inheritDoc} */
    @Override
    public void startEat() {
        eating = true;
        hunger = 0.0; // vient de manger : plus faim
        setContinuousVelocity(0.0, 0.0);
        setVelocity(0, 0);
        setColor(EAT_COLOR);
    }

    /** {@inheritDoc} */
    @Override
    public void endEat() {
        eating = false;
        // retour à la couleur d'espèce
        setColor(BASE_COLOR);
    }

    // --------- Rendu ---------

    /**
     * Dessine le requin en orientant le sprite sur la vitesse continue
     * et en adaptant la taille au pas de grille.
     *
     * @param gui interface graphique cible
     */
    @Override
    public final void draw(GUISimulator gui) {
        final int s  = SIZE;
        final int cellX = offX() + getX() * s;
        final int cellY = offY() + getY() * s;

        int sprite = (int)Math.round(s * SPRITE_SCALE);
        if (sprite < MIN_SPRITE_PX) sprite = MIN_SPRITE_PX;
        sprite = Math.min(sprite, MAX_SPRITE_PX);
        if (MIN_ABS_SPRITE_PX > 0) sprite = Math.max(sprite, MIN_ABS_SPRITE_PX);

        final int cx = cellX + s / 2;
        final int cy = cellY + s / 2;

        double theta = (Math.abs(cvx) < 1e-9 && Math.abs(cvy) < 1e-9) ? 0.0 : Math.atan2(cvy, cvx);

        SharkIcon icon = new SharkIcon();
        icon.setFill(getColor());
        icon.setDimension(new Dimension(sprite, sprite));

        gui.addGraphicalElement(new ParticleIcon(cx, cy, icon, theta));
    }
}
