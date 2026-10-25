package systems;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.IntSupplier;

public final class GridSeeding {
    private final GridSimulator simulator;
    private final int coverage;
    private final Random rand = new Random();

    public GridSeeding(GridSimulator  simulator, int coverage) {
        this.simulator = simulator;
        this.coverage = coverage;
    }
    // SEEDING
    // Place blocks on grid, return number of blocks placed
    // Stop when coverage reached
    // Maybe can be improved marginally ??

    /**
     * Places a "glider" pattern on the grid.
     *
     * @param row The starting row position of the glider.
     * @param col The starting column position of the glider.
     */
    private int seedGlider(int row, int col) {
        int randomState = rand.nextInt(1, simulator.getNbState());
        int count = 0;
        if (simulator.getState(row+1, col) != randomState) {
            simulator.setState(row+1, col, randomState);
            count++;
        }
        if (simulator.getState(row+2, col+1) != randomState) {
            simulator.setState(row+2, col+1, randomState);
            count++;
        }
        if (simulator.getState(row+1, col+2) != randomState) {
            simulator.setState(row+1, col+2, randomState);
            count++;
        }
        if (simulator.getState(row, col+2) != randomState) {
            simulator.setState(row, col+2, randomState);
            count++;
        }
        if (simulator.getState(row+2, col+2) != randomState) {
            simulator.setState(row+2, col+2, randomState);
            count++;
        }
        return count;
    }

    /**
     * Places a 2x2 block on the grid.
     *
     * @param row The starting row position of the block.
     * @param col The starting column position of the block.
     */
    private int seedBlock(int row, int col) {
        int randomState = rand.nextInt(1, simulator.getNbState());
        int count = 0;
        if (simulator.getState(row, col) != randomState) {
            simulator.setState(row, col, randomState);
            count++;
        }
        if (simulator.getState(row+1, col) != randomState) {
            simulator.setState(row+1, col, randomState);
            count++;
        }
        if (simulator.getState(row, col+1) != randomState) {
            simulator.setState(row, col+1, randomState);
            count++;
        }
        if (simulator.getState(row+1, col+1) != randomState) {
            simulator.setState(row+1, col+1, randomState);
            count++;
        }
        return count;
    }

    /**
     * Places a blinking block on the grid.
     *
     * @param row The starting row position of the blinker.
     * @param col The starting column position of the blinker.
     */
    private int seedBlinker(int row, int col) {
        int randomState = rand.nextInt(1, simulator.getNbState());
        int count = 0;
        if (simulator.getState(row, col) != randomState) {
            simulator.setState(row, col, randomState);
            count++;
        }
        if (simulator.getState(row+1, col) != randomState) {
            simulator.setState(row+1, col, randomState);
            count++;
        }
        if (simulator.getState(row+2, col) != randomState) {
            simulator.setState(row+2, col, randomState);
            count++;
        }
        return count;
    }

    /**
     * Places a random pattern on the grid.
     *
     * @param row The starting row position of the pattern.
     * @param col The starting column position of the pattern.
     */
    private int seedRandomPattern(int row, int col) {
        Random rand = new Random();
        List<IntSupplier> seeds = Arrays.asList(
                () -> seedBlock(row, col),
                () -> seedBlinker(row, col),
                () -> seedGlider(row, col)
        );
        return seeds.get(rand.nextInt(seeds.size())).getAsInt();
    }

    /**
     * Generates random blocks with different functionality based on cell coverage percentage.
     *
     */
    public void seedRandomBlocks() {
        // Get number of blocks from coverage
        int target = Math.round((float) (simulator.getRow() * simulator.getCol() * coverage) / 100);
        int placed = 0;
        while (placed < target) {
            int col = rand.nextInt(0, simulator.getCol());
            int row = rand.nextInt(0, simulator.getRow());
            placed += seedRandomPattern(row, col); // get as random as possible
        }
    }


    public void seed() {
        seedRandomBlocks();
        System.out.println("Seeding complete");
    }
}

