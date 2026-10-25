package entities.boid;

import images.ParticleIcon;
import images.FishIcon;

import gui.GUISimulator;

import java.awt.*;

/** Boid proie (non-prédateur). */
public class Fish extends Boid {

    // --------- Vitesse ---------
    private static final int TICK_NUM = 1;
    private static final int TICK_DEN = 5;

    // --------- Rendu ---------
    private static final int    MIN_SPRITE_PX      = 9;
    private static final double SPRITE_SCALE       = 1.0;
    private static final int    MAX_SPRITE_PX      = 14;
    private static final int    MIN_ABS_SPRITE_PX  = 0;

    // --------- Social / séparation ---------
    private static final double ALIGN_CROSS_KIND      = 0.6; // alignement vers boids très prédateurs
    private static final double SMALL_HEAVY_RSEP_MULT = 2.5; // rayon de sep vis-à-vis des gros prédateurs

    // --------- Panique ---------
    private static final double PANIC_RADIUS_FACTOR = 2.5;
    private static final double PANIC_FLEE_GAIN     = 3.0;
    private static final double PANIC_JITTER_GAIN   = 0.8;
    private static final double PANIC_SPEED_BOOST   = 0.5;

    /** Fish par défaut : (speed=1.0, force=1.0, fear=1.0). */
    public Fish(int x, int y, int vx, int vy, Color color) {
        super(x, y, vx, vy, color, 1.0, 1.0, 1.0);
        setTickPeriod(TICK_NUM, TICK_DEN);
    }

    /**
     * Fish avec paramètres individuels explicites.
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
    public Fish(int x, int y, int vx, int vy, Color color, double speedScale, double forceScale, double fearWeight) {
        super(x, y, vx, vy, color, speedScale, forceScale, fearWeight);
        setTickPeriod(TICK_NUM, TICK_DEN);
    }

    // --------- Affinités sociales / attraction ---------

    /**
     * Modifie l'alignement : neutre entre poissons, réduit vis-à-vis des prédateurs.
     *
     * @param other voisin considéré
     * @return facteur d'alignement interpolé entre 1.0 et ALIGN_CROSS_KIND
     */
    @Override
    public double alignment(Boid other) {
        double p = other.predatorScore(); // 0..1

        // p = 0  -> 1.0      (congénère inoffensif)
        // p = 1  -> ALIGN_CROSS_KIND (gros prédateur)
        return 1.0 - p * (1.0 - ALIGN_CROSS_KIND);
    }

    /** {@inheritDoc} */
    @Override
    public double cohesion(Boid other) {
        // Cohésion neutre dans ton modèle original
        return 1.0;
    }

    /**
     * Agrandit le rayon de séparation proportionnellement au score de prédation du voisin.
     *
     * @param other voisin considéré
     * @return multiplicateur de rayon de séparation
     */
    @Override
    public double separationRadius(Boid other) {
        double p = other.predatorScore();
        // p = 0 -> rayon normal ; p = 1 -> SMALL_HEAVY_RSEP_MULT
        return 1.0 + p * (SMALL_HEAVY_RSEP_MULT - 1.0);
    }

    /** {@inheritDoc} */
    @Override
    public double separation(Boid other) {
        // Poids de peur tel quel (toute la différence passe par predatorScore et fearWeight)
        return 1.0;
    }

    // --------- Panique / menace ---------

    /** {@inheritDoc} */
    @Override
    public double panicRadiusFactor() {
        return PANIC_RADIUS_FACTOR;
    }

    /** {@inheritDoc} */
    @Override
    public double panicFleeGain() {
        return PANIC_FLEE_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double panicJitterGain() {
        return PANIC_JITTER_GAIN;
    }

    /** {@inheritDoc} */
    @Override
    public double panicSpeedBoost() {
        return PANIC_SPEED_BOOST;
    }

    /**
     * Sensibilité à la panique : proportionnelle à la dangerosité et à l'intimidation du voisin.
     *
     * @param other voisin évalué
     * @return niveau de menace perçu (0 si aucune menace)
     */
    @Override
    public double panicSensitivity(Boid other) {
        double p = other.predatorScore();
        if (p <= 0.0) return 0.0;

        double intimidation = other.getFearWeight();
        double hungerFactor = 1.0 + other.hungerLevel();

        // Panique proportionnelle à "à quel point c'est un prédateur" * intimidation * faim
        return p * intimidation * hungerFactor;
    }

    /** {@inheritDoc} */
    @Override
    public double antiPackWeight(Boid other) {
        // Pas d'anti-pack spécifique pour les Fish dans ce modèle.
        return 0.0;
    }

    // --------- Rendu ---------

    /**
     * Dessine le poisson avec une orientation basée sur sa vitesse continue
     * et une sprite dont la taille suit l'échelle de la grille.
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

        FishIcon icon = new FishIcon();
        icon.setFill(getColor());
        icon.setDimension(new Dimension(sprite, sprite));
        gui.addGraphicalElement(new ParticleIcon(cx, cy, icon, theta));
    }
}
