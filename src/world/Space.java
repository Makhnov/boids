package world;

import gui.GUISimulator;
import gui.Rectangle;
import java.awt.Color;

/**
 * Environnement d'espace continu discret. Seuls les attributs des boids sont en float, les cooordonnées des points sont en int.
 */
public class Space extends Board {
    Color background;   
    Color border;

    public Space(BoundsMode mode, Color border, Color background) {
        this.boundsMode  = mode;
        this.background = background;
        this.border = border;
    }

    public Space() {
        this(BoundsMode.BOUNCE, Color.black, Color.white);
    }

    @Override
    public void draw(GUISimulator gui, int rows, int cols) {
        int usedW = cols * size;                 // aire jouable réelle en px
        int usedH = rows * size;
        int offX  = getPlayOffX();               // coin haut-gauche en px
        int offY  = getPlayOffY();

        int delta = size / 4;
        int innerW = Math.max(1, usedW + 2 * delta);
        int innerH = Math.max(1, usedH + 2 * delta);

        int cx = offX + usedW / 2;
        int cy = offY + usedH / 2;

        gui.addGraphicalElement(new gui.Rectangle(
            cx, cy,
            border, // Bordures
            background, // Fond de l'aire jouable
            innerW, innerH
        ));
    }
}
