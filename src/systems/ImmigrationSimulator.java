package systems;

import entities.Cell;
import java.awt.*;

/**
 * Implementation of the Immigration cellular automaton on a 2D toroidal grid.
 * <p>
 * Each cell can be in one of {@code nbState} states. At each step, a cell may
 * change its state to the "next" one if it has sufficiently many neighbors
 * already in that next state.
 */

public class ImmigrationSimulator extends GridSimulator {

    /**
     * Creates a new {@code ImmigrationSimulator}.
     *
     * @param width    width of the drawing area in pixels.
     * @param height   height of the drawing area in pixels.
     * @param rows     number of rows of the logical grid.
     * @param cols     number of columns of the logical grid.
     * @param coverage percentage of cells initially active (non-zero state).
     * @param bgColor  background color of the grid.
     * @param nbState  number of possible states for each cell.
     */

    public ImmigrationSimulator(int width, int height, int rows, int cols, int coverage, Color bgColor, int nbState) {
        super(width, height, rows, cols, nbState, bgColor, coverage);
        init();
    }

    /**
     * Updates the grid according to the Immigration rule:
     * <ul>
     *   <li>For each cell, compute {@code nextState = (state + 1) mod nbState}.</li>
     *   <li>If the number of neighbors in {@code nextState} is at least 3,
     *       the cell switches to that next state.</li>
     *   <li>Otherwise, the cell keeps its current state.</li>
     * </ul>
     * A double buffer is used so that all updates are applied simultaneously.
     */
    @Override
    public void update() {
        int[] next = new int[rows*cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell e = entities.get(toIndex(r,c));
                int nextState = (e.getState()+1)%nbState;
                int n = similarNeighbors(e, nextState);
                next[toIndex(r,c)] = (n >= 3) ? nextState : e.getState();
            }
        }
        // apply the buffer
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).setState(next[i]);
            entities.get(i).setColor(mapGradientColor(next[i]));
        }
    }

    /**
     * Draws the current state of the grid.
     * <p>
     * This method simply delegates to {@link GridSimulator#draw()} but can be
     * overridden for custom rendering if needed.
     */

    @Override
    public void draw() {
        super.draw();
    }

}
