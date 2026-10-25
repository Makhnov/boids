package entities;

import gui.GUISimulator;
import java.awt.*;

/**
 * classe de base pour les entités Cell et Particle avec 
 * des fonctions et des attributs communs .
 */
public abstract class Entity {
    protected Point dot;
    protected Color color;

    // Échelle et décalages (px) poussés par le Simulator
    protected static int SIZE   = 1;
    protected static int OFFX_PX = 0;
    protected static int OFFY_PX = 0;

    public Entity(Point p) {
        this.dot = p;
        this.color = Color.BLUE;
    }

    public Entity(Point p, Color color) {
        this.dot = p;
        this.color = color;
    }

    public abstract void init_entity();
    public abstract void draw(GUISimulator gui);

    public int getX() { return dot.x; }
    public int getY() { return dot.y; }

    public void setPosition(int x, int y) { dot.setLocation(x, y); }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public static int getSize() { return SIZE; }
    public static void setSize(int size) { SIZE = Math.max(1, size); }

    /** Positionne l’origine (px) de l’aire jouable. Appelé par Simulator. */
    public static void setOffset(int x, int y) {
        OFFX_PX = x;
        OFFY_PX = y;
    }

    /** Décalages pixels pour le dessin (protégé pour les sous-classes). */
    protected static int offX() { return OFFX_PX; }
    protected static int offY() { return OFFY_PX; }

    @Override
    public String toString() {
        return "Entity at " + dot
             + " | size=" + SIZE
             + " | color(RGB)=" + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}
