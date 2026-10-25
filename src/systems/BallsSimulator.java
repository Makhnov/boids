package systems;

import core.Simulator;
import entities.Particle;
import world.Board.BoundsMode;
import world.Space;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Bouncing balls simulation on a discrete 2D space.
 * This simulator manages a set of particles that move with an integer velocity
 * and bounce on the borders (or wrap around, depending on the bounds mode).
 */
public class BallsSimulator extends Simulator<Particle, Space> {
    private final int nbBalls;

    /**
     * Creates a new {@code BallsSimulator}.
     *
     * @param width  width of the drawing area in pixels.
     * @param height height of the drawing area in pixels.
     * @param n      number of balls to simulate.
     * @param size   approximate size (diameter) of a ball in pixels, used to derive
     *               a reasonable logical grid resolution.
     */

    public BallsSimulator(int width, int height, int n, int size) {
        super(width, height,
                Math.max(1, (height / size) * 2),
                Math.max(1, (width / size) * 2),
                Color.white,
                new Space());
        this.nbBalls = n;
        this.entities = new ArrayList<>(n);
        init();
    }

    /**
     * (Re)initializes the simulation by placing {@code nbBalls} particles
     * at random positions with random velocities and colors.
     */

    @Override
    protected void init() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        entities.clear();

        int minX = board.minXUnits(), maxX = board.maxXUnits(cols);
        int minY = board.minYUnits(), maxY = board.maxYUnits(rows);

        for (int i = 0; i < nbBalls; i++) {
            int x = rnd.nextInt(minX, maxX + 1);
            int y = rnd.nextInt(minY, maxY + 1);
            int sx = rnd.nextInt(1, 4);
            int sy = rnd.nextInt(1, 4);
            int vx = rnd.nextBoolean() ? sx : -sx;
            int vy = rnd.nextBoolean() ? sy : -sy;
            vx = ((vx == 0) && (vy == 0)) ? 1 : vx;

            Color c = new Color(rnd.nextInt(40, 256), rnd.nextInt(40, 256), rnd.nextInt(40, 256));
            entities.add(new Particle(x, y, vx, vy, c));
        }
    }

    /**
     * Clears the simulation by resetting each {@link Particle} to its initial
     * state.
     */

    @Override
    protected void clear() {
        for (Particle b : entities)
            b.init_entity();
    }

    /**
     * Performs one simulation step:
     * updates the position of each ball, applying either bounce or wrap
     * behavior depending on the board bounds mode.
     */
    @Override
    protected void update() {

        boolean wrap = (board.getBoundsMode() == BoundsMode.WRAP);
        int minX = board.minXUnits(), maxX = board.maxXUnits(cols);
        int minY = board.minYUnits(), maxY = board.maxYUnits(rows);

        for (Particle b : entities) {
            int nx = b.getX() + b.getVx();
            int ny = b.getY() + b.getVy();

            if (wrap) {
                if (nx < minX)
                    nx = maxX;
                else if (nx > maxX)
                    nx = minX;
                if (ny < minY)
                    ny = maxY;
                else if (ny > maxY)
                    ny = minY;
            } else {
                if (nx < minX || nx > maxX) {
                    nx = Math.max(minX, Math.min(nx, maxX));
                    b.setVx(-b.getVx());
                }
                if (ny < minY || ny > maxY) {
                    ny = Math.max(minY, Math.min(ny, maxY));
                    b.setVy(-b.getVy());
                }
            }
            b.setPosition(nx, ny);
        }
    }
}