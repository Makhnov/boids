package world;

import gui.GUISimulator;
import gui.Rectangle;
import java.awt.Color;

/**
 * définit un environnement sous forme de grille où 
 * chaque carré est lié à une entité Cell, mais qui n'est pas
 * geré pour le Grid.
 */
public class Grid extends Board {
    public Grid() {
        this.boundsMode = BoundsMode.WRAP;      
    }

    @Override
    public void draw(GUISimulator gui, int rows, int cols) {
        // offset pixel explicite (aire jouable fixe à l’écran, rows/cols = “résolution”)
        if (getPlayOffX() != 0 || getPlayOffY() != 0) {
            int usedW = cols * size;
            int usedH = rows * size;
            int offX  = getPlayOffX();
            int offY  = getPlayOffY();

            int cx = offX + usedW / 2;
            int cy = offY + usedH / 2;

            // Fond aire jouable
            gui.addGraphicalElement(new gui.Rectangle(
                cx, cy, java.awt.Color.BLACK, new java.awt.Color(250,250,250),
                Math.max(1, usedW), Math.max(1, usedH)
            ));

            // Grille intérieure
            java.awt.Color gridLine = new java.awt.Color(220,220,220);

            for (int c = 1; c < cols; c++) {
                int x = offX + c * size;
                gui.addGraphicalElement(new gui.Rectangle(x, cy, gridLine, gridLine, 1, usedH));
            }
            for (int r = 1; r < rows; r++) {
                int y = offY + r * size;
                gui.addGraphicalElement(new gui.Rectangle(cx, y, gridLine, gridLine, usedW, 1));
            }
            // return; // On n'utilise pas les marges LOGIQUES dans ce mode
        }

        // Mode 2 code actuel avec marges LOGIQUES (en unités)
        // int W = cols * size;
        // int H = rows * size;

        // int mlU = getMarginLeft();
        // int mrU = getMarginRight();
        // int mtU = getMarginTop();
        // int mbU = getMarginBottom();
        // int ml  = mlU * size;
        // int mr  = mrU * size;
        // int mt  = mtU * size;
        // int mb  = mbU * size;

        // int playCols = Math.max(0, cols - (mlU + mrU));
        // int playRows = Math.max(0, rows - (mtU + mbU));
        // int innerW   = playCols * size;
        // int innerH   = playRows * size;

        // int cx = ml + innerW / 2;
        // int cy = mt + innerH / 2;

        // gui.addGraphicalElement(new gui.Rectangle(
        //     W / 2, H / 2,
        //     java.awt.Color.BLACK, new java.awt.Color(245,245,245),
        //     Math.max(1, W), Math.max(1, H)
        // ));

        // gui.addGraphicalElement(new gui.Rectangle(
        //     cx, cy,
        //     java.awt.Color.BLACK, new java.awt.Color(250,250,250),
        //     Math.max(1, innerW), Math.max(1, innerH)
        // ));

        // java.awt.Color gridLine = new java.awt.Color(220,220,220);

        // for (int c = 1; c < playCols; c++) {
        //     int x = ml + c * size;
        //     gui.addGraphicalElement(new gui.Rectangle(x, cy, gridLine, gridLine, 1, innerH));
        // }
        // for (int r = 1; r < playRows; r++) {
        //     int y = mt + r * size;
        //     gui.addGraphicalElement(new gui.Rectangle(cx, y, gridLine, gridLine, innerW, 1));
        // }
    }

}
