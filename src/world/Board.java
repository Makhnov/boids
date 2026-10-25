package world;

import gui.GUISimulator;

/**
 * environnement commun pour les jeux. Il sert de 
 * base aux classes Grid et Space.
 */
public abstract class Board {
    public enum BoundsMode { WRAP, BOUNCE }

    protected int size = 1;
    protected BoundsMode boundsMode = BoundsMode.BOUNCE;

    // Marges LOGIQUES (en unités)
    protected int marginTop = 0;
    protected int marginRight = 0;
    protected int marginBottom = 0;
    protected int marginLeft = 0;

    // Marges VISUELLES (en pixels) pour réserver de la place dans la fenêtre GUI
    protected int marginTopPx = 0, marginRightPx = 0, marginBottomPx = 0, marginLeftPx = 0;

    // Offset pixel du coin haut-gauche de l’aire jouable (défini par le Simulator)
    protected int playOffX = 0, playOffY = 0;

    // Dessine le fond / bordure / grille éventuelle (jamais les entités).
    public abstract void draw(GUISimulator gui, int rows, int cols);

    // --- taille de cellule ---
    public int getSize() { return size; }
    public void setSize(int s) { size = Math.max(1, s); }

    // --- marges LOGIQUES (en unités) ---
    public int getMarginTop()       { return marginTop; }
    public int getMarginRight()     { return marginRight; }
    public int getMarginBottom()    { return marginBottom; }
    public int getMarginLeft()      { return marginLeft; }

    public void setMargins(int top, int right, int bottom, int left) {
        marginTop    = Math.max(0, top);
        marginRight  = Math.max(0, right);
        marginBottom = Math.max(0, bottom);
        marginLeft   = Math.max(0, left);
    }

    // --- marges VISUELLES (en pixels) : utilisées par le Simulator pour fixer l’aire jouable ---
    public void setMarginsPx(int top, int right, int bottom, int left) {
        marginTopPx    = Math.max(0, top);
        marginRightPx  = Math.max(0, right);
        marginBottomPx = Math.max(0, bottom);
        marginLeftPx   = Math.max(0, left);
    }
    public int getMarginTopPx()    { return marginTopPx; }
    public int getMarginRightPx()  { return marginRightPx; }
    public int getMarginBottomPx() { return marginBottomPx; }
    public int getMarginLeftPx()   { return marginLeftPx; }

    // --- offset pixel du coin haut-gauche de l’aire jouable ---
    public void setPlayAreaOffsetPx(int x, int y) { playOffX = x; playOffY = y; }
    public int getPlayOffX() { return playOffX; }
    public int getPlayOffY() { return playOffY; }

    // --- bornes LOGIQUES pour les systèmes (Balls, etc.) ---
    public int minXUnits()          { return marginLeft; }
    public int minYUnits()          { return marginTop; }
    public int maxXUnits(int cols)  { return cols - marginRight  - 1; }
    public int maxYUnits(int rows)  { return rows - marginBottom - 1; }

    public BoundsMode getBoundsMode() { return boundsMode; }
    public Board withBoundsMode(BoundsMode m) { this.boundsMode = m; return this; }
}
