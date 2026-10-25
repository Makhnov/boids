package systems;

import core.Simulator;
import entities.Cell;
import world.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GridSimulator est une classe abstraite qui étend Simulator<Cell, Grid>. Elle fournit 
 * les fonctionnalités de base pour simuler une grille 2D de cellules, chacune avec 
 * plusieurs états et couleurs associées. La classe prend en charge l'initialisation de 
 * la grille, la gestion de l'état des cellules, les calculs de voisinage et le mappage 
 * des couleurs pour la visualisation.
 */
abstract public class GridSimulator extends Simulator<Cell, Grid> {
    final protected Color aliveColor = Color.CYAN;
    final protected Color deadColor = Color.WHITE;
    final protected int nbState;
    final protected int coverage;
    protected final GridSeeding seeding;
    protected GridSimulator(int width, int height, int rows, int cols, int nbState, Color bgColor, int coverage) {
        super(width, height, 
            rows, cols,
            bgColor, 
            new Grid()
        );
        if (nbState <= 0) throw new IllegalArgumentException("GridSimulator invalid arguments : nbState");

        entities = new ArrayList<>(rows*cols);
        // populate the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                entities.add(
                    new Cell(i,j)
                );
            }
        }
        this.nbState = nbState;
        this.coverage = coverage;
        seeding = new GridSeeding(this, coverage);
    }

    // A dead cell has deadColor (white by default)
    // A living cell has gradient colors of the live color (depends on its state)
    protected void initColor(Cell e) {
        e.setColor(mapGradientColor(e.getState()));
    }

    protected void init() {
        seeding.seed();
        for (Cell e:entities) {
            initColor(e);
        }
        // next();
    }

    // COLOR
    public Color mapGradientColor(int state) {
        if (state < 0 || state > nbState-1) throw new IllegalArgumentException("Invalid state");
        if (state == 0) return deadColor;
        float t = (float) state/(nbState-1);
        float [] hsb = Color.RGBtoHSB(
                aliveColor.getRed(),
                aliveColor.getGreen(),
                aliveColor.getBlue(),
                null
        );

        float hue = (hsb[0] + t*0.2f)% 1.0f;
        float sat = hsb[1];
        float brightness = hsb[2];
        return Color.getHSBColor(hue, sat, brightness);
    }
    
    // CLEAR
    protected void clear() {
        for (Cell ne : entities) {
            ne.setState(0);
        }
    }

    // GET / SET
    public void setState(int r, int c, int state) {
        if (state < 0 || state > nbState-1) throw new IllegalArgumentException("Illegal state: " + state);
        int rr = wrapRow(r);
        int cc = wrapCol(c);
        entities.get(toIndex(rr,cc)).setState(state);
    }

    public int getState(int r, int c) {
        return entities.get(toIndex(wrapRow(r), wrapCol(c))).getState();
    }

    public int getNbState() {
        return nbState;
    }

    public int getRow() {
        return rows;
    }
    public int getCol() {
        return cols;
    }

    // return a list of neighbors
    protected List<Cell> getNeighbors(Cell e) {
        List<Cell> ret = new ArrayList<Cell>(8);
        for (int dr = -1; dr <=1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr==0 && dc==0) continue;
                int x = wrapRow(e.getX()+dr);
                int y = wrapCol(e.getY()+dc);
                ret.add(entities.get(toIndex(x, y)));
            }
        }
        return ret;
    }

    // voisins de même état
    protected int similarNeighbors(Cell e, int state) {
        int count = 0;
        for (Cell ne : getNeighbors(e)) {
            if (ne.getState() == state) count++;
        }
        return count;
    }

    // voisins de même couleur
    protected int similarNeighbors(Cell e, Color color) {
        int count = 0;
        for (Cell ne : getNeighbors(e)) {
            if (ne.getColor().equals(color)) count++;
        }
        return count;
    }

}
