package entities;
import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;

/**
 * Entité principal des les automates (Conway, Immigration, Schelling).
 */
public class Cell extends Entity {
    private int state;

    public Cell(int x, int y) {
        super(new Point(x,y));
        this.state = 0;
    }

    public int getState() {
        return state;
    }
    public void setState(int state) {
        if (state < 0) throw new IllegalArgumentException("Illegal state");
        this.state = state;
    }

    public boolean isAlive() {
        return state > 0;
    }

    @Override
    public void init_entity() {
        this.state = 0;
    }

    @Override
    public void draw(GUISimulator gui) {
        int xpx = offX() + getX() * SIZE + SIZE / 2;
        int ypx = offY() + getY() * SIZE + SIZE / 2;
        gui.addGraphicalElement(
            new Rectangle(xpx, ypx, Color.LIGHT_GRAY, getColor(), SIZE)
        );
    }

    @Override
    public String toString() {
        return "Cell state : " + this.state + 
                " at coordinate " + this.dot +
                " | size=" + SIZE + 
                " | color(RGB)=" + color.getRed() +
                "," + color.getGreen() +
                "," + color.getBlue();
    }
}
