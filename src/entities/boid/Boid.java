package entities.boid;

import entities.Particle;
import java.awt.Color;

/**
 * Boid générique (base commune) : position/vitesse entières + cinématique continue
 * pour l'orientation.
 *
 * Chaque Boid possède :
 *  - des paramètres individuels (speedScale, forceScale, fearWeight)
 *  - des comportements polymorphes (alignment/cohesion/separation, panique, chasse...)
 *
 * Spécialisations prévues :
 *  - Fish  : proie
 *  - Shark : prédateur
 */
public class Boid extends Particle {
    // --------- Vitesse / gestion fractionnaire ---------
    private int periodNumerator = 1;
    private int periodDenominator = 1;
    private int periodCounter = 0;

    // --- Paramètres individuels ---
    
    // On conserve la speedScale uniquement pour l'historique et si besoin de retour arriere, mais pour les boids elle n'est plus utilisée. 
    // C'est désormais la fraction (periodNumerator/periodDenominator) qui détermine la vitesse effective du boid, en passant par l'event manager.
    private final double speedScale;   // facteur sur la vitesse max globale
    
    private final double forceScale;   // facteur sur la force max globale
    private final double fearWeight;   // “poids de peur” que les autres ressentent pour MOI

    // Cinématique continue (pour orientation/FOV)
    protected double cvx = 0.0, cvy = 0.0;

    // Accumulateurs pour la quantification douce (-> stepX/stepY)
    private double accX = 0.0, accY = 0.0;

    // --------- Construction ---------

    // Constructeur polymorphe
    /**
     * Initialise un boid avec position/vitesse entières et facteurs individuels.
     *
     * @param x           position x initiale
     * @param y           position y initiale
     * @param vx          vitesse x initiale (entier)
     * @param vy          vitesse y initiale (entier)
     * @param color       couleur d'affichage
     * @param speedScale  facteur multiplicatif sur la vitesse max globale
     * @param forceScale  facteur multiplicatif sur la force max globale
     * @param fearWeight  poids d'intimidation ressenti par les autres
     */
    protected Boid(int x, int y, int vx, int vy, Color color, double speedScale, double forceScale, double fearWeight) {
        super(x, y, vx, vy, color);
        this.speedScale = speedScale;
        this.forceScale = forceScale;
        this.fearWeight = fearWeight;
    }

    // --------- Nature / paramètres ---------

    /** @return facteur multiplicatif appliqué à la vitesse max globale. */
    public double getSpeedScale()  { return speedScale; }
    /** @return facteur multiplicatif appliqué à la force de steering max globale. */
    public double getForceScale()  { return forceScale; }
    /** @return poids d'intimidation ressenti par les autres boids à mon sujet. */
    public double getFearWeight()  { return fearWeight; }

    // --------- Cinématique continue ---------

    /**
     * Définit la vitesse continue (double précision), utilisée pour l'affichage/l'orientation.
     *
     * @param vx composante continue de vitesse en x
     * @param vy composante continue de vitesse en y
     */
    public void setContinuousVelocity(double vx, double vy) { this.cvx = vx; this.cvy = vy; }
    /** @return composante continue vx utilisée pour orienter le sprite. */
    public double getContVx() { return cvx; }
    /** @return composante continue vy utilisée pour orienter le sprite. */
    public double getContVy() { return cvy; }

    /**
     * Ajoute une vitesse continue vx et retourne le pas entier à effectuer ce tick (arrondi au plus proche).
     *
     * @param vx incrément continu de vitesse en x
     * @return pas entier à appliquer sur l'axe x pour ce tick
     */
    public int stepX(double vx) {
        accX += vx;
        int dx = (int) Math.floor(accX + (accX >= 0 ? 0.5 : -0.5));
        accX -= dx;
        return dx;
    }

    /**
     * Ajoute une vitesse continue vy et retourne le pas entier à effectuer ce tick (arrondi au plus proche).
     *
     * @param vy incrément continu de vitesse en y
     * @return pas entier à appliquer sur l'axe y pour ce tick
     */
    public int stepY(double vy) {
        accY += vy;
        int dy = (int) Math.floor(accY + (accY >= 0 ? 0.5 : -0.5));
        accY -= dy;
        return dy;
    }

    // --------- Fréquence de mise à jour (par défaut : à chaque tick) ---------

    /**
     * Nombre de ticks EventManager entre deux mises à jour "physiques" de ce boid.
     * 1 => mis à jour à chaque tick, 2 => un tick sur deux, etc.
     */
    // public int getTickPeriod() {
    //     return 1;
    // }

    /**
     * Définit la fréquence de mise à jour physique en fraction de ticks EventManager.
     *
     * @param numerator   ticks actifs dans une période
     * @param denominator nombre total de ticks dans la période
     */
    public void setTickPeriod(int numerator, int denominator) {
        this.periodNumerator = numerator;
        this.periodDenominator = denominator;
    }
    /**
     * @return true si ce boid doit être mis à jour sur ce tick courant, et incrémente son compteur interne.
     */
    public final boolean isActive() {
        // Position dans le cycle
        boolean ia = (periodCounter < periodNumerator);
        periodCounter = (periodCounter + 1) % periodDenominator;
        return ia;
    }

    // --------- Score de prédation, peut être surchargé ---------
    /**
     * Calcule un score 0..1 représentant la dangerosité prédatrice de ce boid
     * en combinant capacité de chasse, intimidation et faim.
     *
     * @return score prédatif borné, utilisé par les autres boids pour adapter leurs comportements
     */
    public double predatorScore() {
        double fear   = getFearWeight();   // intimidation globale
        double hunt   = huntGlobalGain();  // capacité de chasse longue portée
        double hunger = 1.0 + hungerLevel();

        // Combinaison linéaire simple
        double raw    = 0.4 * hunt + 0.3 * fear;
        double scaled = raw * hunger;

        // Échelle empirique : ajustable si besoin (Shark ~ proche de 1, Fish ~ proche de 0)
        double scale = 25.0;
        double s = scaled / scale;
        if (s < 0.0) s = 0.0;
        if (s > 1.0) s = 1.0;
        return s;
    }

    // --------- Affinités sociales (polymorphes) ----------

    /**
     * Facteur multiplicatif pour l'alignement sur un voisin.
     *
     * @param other voisin pris en compte
     * @return multiplicateur d'alignement
     */
    public double alignment(Boid other) {
        return 1.0;
    }

    /**
     * Facteur multiplicatif pour la cohésion vers un voisin.
     *
     * @param other voisin pris en compte
     * @return multiplicateur de cohésion
     */
    public double cohesion(Boid other) {
        return 1.0;
    }

    /**
     * Multiplicateur de rayon de séparation vis-à-vis d'un voisin.
     *
     * @param other voisin pris en compte
     * @return multiplicateur sur le rayon de séparation
     */
    public double separationRadius(Boid other) {
        return 1.0;
    }

    /**
     * Facteur multiplicatif sur le "poids de peur" de other dans la séparation.
     *
     * @param other voisin pris en compte
     * @return multiplicateur sur la séparation
     */
    public double separation(Boid other) {
        return 1.0;
    }

    // --------- Panique / foule ---------

    /** Facteur de rayon de panique (multiplicateur de rAlign). 0 => pas de panique. */
    public double panicRadiusFactor()   { return 0.0; }

    /** Gain de fuite de panique (vitesse / steering). */
    public double panicFleeGain()       { return 0.0; }

    /** Jitter aléatoire ajouté pendant la panique. */
    public double panicJitterGain()     { return 0.0; }

    /** Boost de vitesse max sous panique. */
    public double panicSpeedBoost()     { return 0.0; }

    /**
     * Mesure dans quelle mesure le voisin "other" me fait paniquer.
     * 0 ⇒ aucune menace, >0 ⇒ menace croissante.
     *
     * @param other voisin évalué
     * @return mesure de menace perçue
     */
    public double panicSensitivity(Boid other) {
        return 0.0;
    }

    /**
     * Poids avec lequel "other" compte dans mon anti-pack (anti-centroïde).
     * 0 ⇒ ne compte pas, >0 ⇒ contribue davantage au centroïde local à fuir.
     *
     * @param other voisin évalué
     * @return poids dans le calcul de l'anti-pack
     */
    public double antiPackWeight(Boid other) {
        return 0.0;
    }

    // --------- Chasse / meute de prédateurs ---------

    /**
     * Facteur de rayon pour la chasse longue portée (multiplicateur de rCoh global).
     * <= 0 => chasse longue portée désactivée pour ce boid.
     */
    public double huntRadiusFactor() {
        return 0.0;
    }

    /**
     * Poids de "cible de chasse" pour le voisin other :
     * 0  => pas une proie intéressante,
     * >0 => attire ce boid en chasse longue portée.
     *
     * @param other voisin évalué
     * @return poids d'attraction de chasse
     */
    public double huntAttraction(Boid other) {
        return 0.0;
    }

    /** Gain global de la force de chasse. */
    public double huntGlobalGain() {
        return 0.0;
    }

    /** Exposant pour le boost de pack (communs : 0.75 dans tes Params). */
    public double huntPackExp() {
        return 0.75;
    }

    /** Petit epsilon pour la distance (commun : 1e-6). */
    public double huntDistEps() {
        return 1e-6;
    }

    /** Gain de l’orbite autour de la direction de chasse. */
    public double orbitGain() {
        return 0.0;
    }

    /** Amortissement minimal de l’orbite quand la cible est loin (commun : 0.2). */
    public double orbitFarDamp() {
        return 0.2;
    }

    /** Gain de fuite du centroïde (anti-pack). */
    public double antiCentroidGain() {
        return 0.0;
    }

    /** Facteur de rayon pour l’anti-centroïde (commun : 3.0). */
    public double antiCentroidRadMult() {
        return 3.0;
    }

    /** Gain de focus sur la proie sélectionnée dans la chasse. */
    public double focusGain() {
        return 0.0;
    }

    // --------- Chasse rapprochée (morsure) ---------

    /**
     * Désir de "mordre" le voisin other.
     * 0  ⇒ ce boid ne considérera jamais other comme proie.
     * >0 ⇒ plus c'est grand, plus other est une cible prioritaire.
     *
     * @param other voisin évalué comme proie potentielle
     * @return désir de morsure (0 si aucune morsure)
     */
    public double biteDesire(Boid other) {
        return 0.0;
    }

    // --------- Faim / état interne ---------

    /** Niveau de faim – par défaut nul. */
    public double hungerLevel() {
        return 0.0;
    }

    /** Méthode appelée à chaque tick avant les calculs de forces. */
    public void onTick() {
        // par défaut : rien
    }

    /** Indique si le boid est "gelé" (par ex. en train de manger). */
    public boolean isFrozen() {
        // par défaut : non gelé
        return false;
    }

    /**
     * Appelé quand le boid commence à manger une proie. */
    public void startEat() {
        // par défaut : rien
    }

    /** Appelé à la fin du délai de récupération quand les prédateurs ont fini de manger. */
    public void endEat() {
        // par défaut : rien
    }
}
