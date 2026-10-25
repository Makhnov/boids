package systems;

import entities.Cell;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Implementation of Schelling's segregation model on a 2D grid.
 * <p>
 * Each living cell represents an "agent" belonging to a color group. Agents are
 * considered "unhappy" if they have more than {@code K} non-similar neighbors.
 * Unhappy agents are moved to random empty locations, leading to segregation
 * patterns emerging over time.
 */

public class SchellingSimulator extends GridSimulator {
    /** Threshold of non-similar neighbors above which an agent moves. */
    protected final int K;
    /** Family of colors used to represent different groups / populations. */
    protected final Color[] c;

    /**
     * Creates a new {@code SchellingSimulator}.
     *
     * @param width      width of the drawing area in pixels.
     * @param height     height of the drawing area in pixels.
     * @param rows       number of rows of the logical grid.
     * @param cols       number of columns of the logical grid.
     * @param bgColor    background color of empty cells.
     * @param K          tolerance threshold: maximum allowed number of non-similar
     *                   neighbors.
     * @param colorGroup number of distinct color groups to generate.
     * @param coverage   percentage of cells initially occupied by agents.
     */
    public SchellingSimulator(int width, int height, int rows, int cols, Color bgColor, int K, int colorGroup,
            int coverage) {
        super(width, height, rows, cols, 2, bgColor, coverage);
        this.K = K;
        this.c = generateColors(colorGroup);
        init();
    }

    /**
     * Initializes the color of a cell.
     * <p>
     * If the cell is alive (occupied by an agent), a random color is picked from
     * the pre-generated color family {@link #c}. Otherwise, the background
     * (dead) color is used.
     *
     * @param e the cell to initialize.
     */
    @Override
    protected void initColor(Cell e) {
        if (e.isAlive()) {
            Random rand = new Random();
            int idx = rand.nextInt(c.length);
            e.setColor(c[idx]);
        } else {
            e.setColor(deadColor);
        }
    }

    /**
     * Performs one step of Schelling's segregation process:
     * <ol>
     * <li>Classify cells as empty or "unhappy" agents, based on their
     * neighbors.</li>
     * <li>Shuffle the list of unhappy agents.</li>
     * <li>Move unhappy agents into random empty positions, until no empty cell
     * remains
     * or all unhappy agents have been processed.</li>
     * <li>Apply the resulting state and color buffers to the grid.</li>
     * </ol>
     */
    @Override
    protected void update() {
        Random rnd = new Random();
        final int n = entities.size();
        int[] nextState = new int[n];
        Color[] nextColor = new Color[n];

        ArrayList<Integer> emptyIdx = new ArrayList<>();
        ArrayList<Integer> unhappyAgents = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Cell e = entities.get(i);
            nextState[i] = e.getState();
            nextColor[i] = e.getColor();
            int aliveNeighbors = 8 - similarNeighbors(e, deadColor);
            int similar = similarNeighbors(e, e.getColor());
            int nonSimilar = aliveNeighbors - similar;

            if (!e.isAlive()) {
                emptyIdx.add(i);
            } else if (nonSimilar > K) {
                unhappyAgents.add(i);
            }
        }
        Collections.shuffle(unhappyAgents);
        for (int ui = 0; ui < unhappyAgents.size() && !emptyIdx.isEmpty(); ui++) {
            int srcIdx = unhappyAgents.get(ui);

            int pickPos = rnd.nextInt(emptyIdx.size());
            int destIdx = emptyIdx.remove(pickPos);

            Cell src = entities.get(srcIdx);
            nextState[destIdx] = 1;
            nextColor[destIdx] = src.getColor();

            nextState[srcIdx] = 0;
            nextColor[srcIdx] = deadColor;
        }

        for (int i = 0; i < n; i++) {
            entities.get(i).setState(nextState[i]);
            Color c = nextColor[i] != null ? nextColor[i] : deadColor;
            entities.get(i).setColor(c);
        }
    }

    /**
     * Generates an array of random colors.
     *
     * @param n number of colors to generate.
     * @return an array of {@link Color} objects.
     */
    public static Color[] generateColors(int n) {
        Random rnd = new Random();
        Color[] colors = new Color[n];
        for (int i = 0; i < n; i++) {
            colors[i] = new Color(rnd.nextInt(1, 255), rnd.nextInt(1, 255), rnd.nextInt(1, 255));
        }
        return colors;
    }
}
