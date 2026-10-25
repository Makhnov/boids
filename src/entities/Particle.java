package entities;

import gui.GUISimulator;
import java.awt.*;
import gui.Oval;

/**
 * entité avec un point spécifique dans un espace continu 
 * fini. Utilisé dans Balls et Boids.
 */
public class Particle extends Entity {
    protected int vx, vy; // Velocity components in the x and y directions
    protected int ax, ay; // Acceleration components in the x and y directions

    public Particle(int x, int y, int vx, int vy, Color color) {
        super(new Point(x, y), color);
        this.vx = vx;
        this.vy = vy;
        this.ax = 0;
        this.ay = 0;        
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setVelocity(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public void setAcceleration(int ax, int ay) {
        this.ax = ax;
        this.ay = ay;
    }

    @Override
    public void init_entity() {
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
    }

    @Override
    public void draw(GUISimulator gui) {
        // les éléments du GUI sont centrés : on ajoute + SIZE/2 comme pour Cell
        int px = offX() + getX() * SIZE + SIZE / 2;
        int py = offY() + getY() * SIZE + SIZE / 2;

        gui.addGraphicalElement(new Oval(
            px, py,
            color, color, SIZE
        ));
    }

    @Override
    public String toString() {
        return "Particle at " + this.dot +
               " | vx=" + this.vx + " vy=" + this.vy +
               " | ax=" + this.ax + " ay=" + this.ay +
               " | size=" + SIZE +
               " | color(RGB)=" + color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
}
