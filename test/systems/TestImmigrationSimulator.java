package systems;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import entities.Cell;
import world.Grid;
import java.awt.Color;
import java.util.List;

/**
 * Unit tests for the ImmigrationSimulator class.
 * Tests Immigration game logic without GUI dependencies.
 */
@DisplayName("ImmigrationSimulator Tests")
public class TestImmigrationSimulator {

    /**
     * Test-friendly wrapper that exposes protected fields for testing.
     */
    private static class TestableImmigrationSimulator extends ImmigrationSimulator {
        public TestableImmigrationSimulator(int width, int height, int rows, int cols, int coverage, Color bgColor, int nbState) {
            super(width, height, rows, cols, coverage, bgColor, nbState);
        }

        public List<Cell> getEntities() {
            return entities;
        }

        public Grid getBoard() {
            return board;
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }

        public int getNbStatePublic() {
            return nbState;
        }

        public Color getDeadColor() {
            return deadColor;
        }

        public void clearPublic() {
            clear();
        }
    }

    private static TestableImmigrationSimulator simu;
    private static int width = 800;
    private static int height = 600;
    private static int rows = 50;
    private static int cols = 50;
    private static int coverage = 30;
    private static int nbState = 4;

    @BeforeAll
    public static void setUp() {
        simu = new TestableImmigrationSimulator(width, height, rows, cols, coverage, Color.BLACK, nbState);
    }

    @AfterEach
    public void resetSimulator() {
        // Reset the simulator to initial state after each test
        simu.restart();
    }

    // ---- INITIALIZATION ----

    @Test
    @DisplayName("Simulator should be initialized")
    public void testSimulatorNotNull() {
        assertNotNull(simu, "Simulator should not be null");
    }

    @Test
    @DisplayName("Simulator should create correct number of cells")
    public void testNumberOfCells() {
        assertEquals(rows * cols, simu.getEntities().size(), "Should have rows*cols cells");
    }

    @Test
    @DisplayName("All cells should be Cell instances")
    public void testCellsAreCells() {
        for (Cell cell : simu.getEntities()) {
            assertNotNull(cell, "Cell should not be null");
            assertTrue(cell instanceof Cell, "Cell should be a Cell instance");
        }
    }

    @Test
    @DisplayName("All cells should have valid positions")
    public void testCellsHaveValidPositions() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = simu.getEntities().get(r * cols + c);
                assertEquals(r, cell.getX(), "Cell row should match");
                assertEquals(c, cell.getY(), "Cell col should match");
            }
        }
    }

    @Test
    @DisplayName("Cells should have valid states (0 to nbState-1)")
    public void testCellsHaveValidStates() {
        for (Cell cell : simu.getEntities()) {
            int state = cell.getState();
            assertTrue(state >= 0 && state < nbState, "Cell state should be in [0, " + (nbState-1) + "]");
        }
    }

    @Test
    @DisplayName("Board should be Grid instance")
    public void testBoardIsGrid() {
        assertNotNull(simu.getBoard(), "Board should not be null");
        assertTrue(simu.getBoard() instanceof Grid, "Board should be a Grid instance");
    }

    @Test
    @DisplayName("Number of states should match constructor parameter")
    public void testNbState() {
        assertEquals(nbState, simu.getNbStatePublic(), "Immigration should have " + nbState + " states");
    }

    @Test
    @DisplayName("Dead cells should have white color")
    public void testDeadCellsColor() {
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 0) {
                assertEquals(Color.WHITE, cell.getColor(), "Dead cells should be white");
            }
        }
    }

    @Test
    @DisplayName("Alive cells should not have white color")
    public void testAliveCellsColor() {
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() > 0) {
                assertNotEquals(Color.WHITE, cell.getColor(), "Alive cells should not be white");
            }
        }
    }

    // ---- IMMIGRATION_RULES ----

    @Test
    @DisplayName("Cell with 3+ neighbors of next state should transition")
    public void testCellTransitionWith3Neighbors() {
        // Clear all cells
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Center cell is state 1, surround with 3 cells of state 2 (next state)
        simu.setState(2, 2, 1); // center
        simu.setState(1, 1, 2); // neighbor
        simu.setState(1, 2, 2); // neighbor
        simu.setState(1, 3, 2); // neighbor

        simu.update();

        // Center should transition to state 2
        assertEquals(2, simu.getState(2, 2), "Cell with 3+ neighbors of next state should transition");
    }

    @Test
    @DisplayName("Cell with less than 3 neighbors of next state should stay same")
    public void testCellStaysSameWithFewNeighbors() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Center cell is state 1, only 2 neighbors of state 2
        simu.setState(2, 2, 1); // center
        simu.setState(1, 1, 2); // neighbor
        simu.setState(1, 2, 2); // neighbor

        simu.update();

        // Center should stay at state 1
        assertEquals(1, simu.getState(2, 2), "Cell with <3 neighbors of next state should stay same");
    }

    @Test
    @DisplayName("State should cycle (nbState-1 -> 0)")
    public void testStateCycling() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Center cell is state 3 (max), surround with state 0 (next after 3)
        simu.setState(2, 2, 3); // center
        simu.setState(1, 1, 0);
        simu.setState(1, 2, 0);
        simu.setState(1, 3, 0);

        simu.update();

        // Center should cycle to state 0
        assertEquals(0, simu.getState(2, 2), "State should cycle from max to 0");
    }

    @Test
    @DisplayName("Multiple states should coexist")
    public void testMultipleStates() {
        boolean[] statesFound = new boolean[5];
        for (Cell cell : simu.getEntities()) {
            int state = cell.getState();
            if (state >= 0 && state < 5) {
                statesFound[state] = true;
            }
        }

        // With 50% coverage and 5 states, we should see multiple states
        int stateCount = 0;
        for (boolean found : statesFound) {
            if (found) stateCount++;
        }

        assertTrue(stateCount >= 2, "Should have at least 2 different states");
    }

    // ---- CLEAR_LOGIC ----

    @Test
    @DisplayName("Clear should set all cells to dead")
    public void testClear() {
        simu.clearPublic();

        for (Cell cell : simu.getEntities()) {
            assertEquals(0, cell.getState(), "All cells should be dead after clear");
        }
    }

    @Test
    @DisplayName("Clear should preserve cell positions")
    public void testClearPreservesPositions() {
        simu.clearPublic();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = simu.getEntities().get(r * cols + c);
                assertEquals(r, cell.getX(), "Cell row should be preserved");
                assertEquals(c, cell.getY(), "Cell col should be preserved");
            }
        }
    }

    @Test
    @DisplayName("Clear should preserve cell count")
    public void testClearPreservesCellCount() {
        int countBefore = simu.getEntities().size();
        simu.clearPublic();
        int countAfter = simu.getEntities().size();

        assertEquals(countBefore, countAfter, "Cell count should be preserved after clear");
    }

    // ---- EDGE_CASES ----

    @Test
    @DisplayName("Empty grid should stay empty")
    public void testEmptyGrid() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.update();

        for (Cell cell : simu.getEntities()) {
            assertEquals(0, cell.getState(), "Empty grid should stay empty");
        }
    }

    @Test
    @DisplayName("Grid with 2 states should work")
    public void testTwoStates() {
        TestableImmigrationSimulator twoState = new TestableImmigrationSimulator(400, 400, 5, 5, 50, Color.BLACK, 2);

        assertNotNull(twoState, "Simulator with 2 states should work");
        assertEquals(2, twoState.getNbStatePublic(), "Should have 2 states");

        for (Cell cell : twoState.getEntities()) {
            int state = cell.getState();
            assertTrue(state >= 0 && state < 2, "State should be 0 or 1");
        }
    }

    @Test
    @DisplayName("Grid with many states should work")
    public void testManyStates() {
        TestableImmigrationSimulator manyStates = new TestableImmigrationSimulator(400, 400, 5, 5, 50, Color.BLACK, 10);

        assertNotNull(manyStates, "Simulator with 10 states should work");
        assertEquals(10, manyStates.getNbStatePublic(), "Should have 10 states");

        for (Cell cell : manyStates.getEntities()) {
            int state = cell.getState();
            assertTrue(state >= 0 && state < 10, "State should be in [0, 9]");
        }
    }

    @Test
    @DisplayName("Isolated cell should not change state")
    public void testIsolatedCell() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(2, 2, 2); // single cell with state 2

        simu.update();

        // Should stay at state 2 (no neighbors of state 3)
        assertEquals(2, simu.getState(2, 2), "Isolated cell should not change");
    }

    @Test
    @DisplayName("Grid wraps around (toroidal topology)")
    public void testToroidalWrapping() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Place cells at edges to test wrapping
        simu.setState(0, 0, 1);
        simu.setState(0, 4, 2);
        simu.setState(4, 0, 3);

        simu.update();

        // Just verify no exceptions were thrown
        assertNotNull(simu.getEntities(), "Grid should handle wrapping");
    }

    @Test
    @DisplayName("All cells at same state should not change")
    public void testUniformGrid() {
        // Set all cells to state 2
        for (Cell cell : simu.getEntities()) {
            cell.setState(2);
        }

        simu.update();

        // All cells should stay at state 2 (no neighbors of state 3)
        for (Cell cell : simu.getEntities()) {
            assertEquals(2, cell.getState(), "Uniform grid should not change");
        }
    }
}
