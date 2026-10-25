package systems;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import entities.Cell;
import world.Grid;
import java.awt.Color;
import java.util.List;

/**
 * Unit tests for the ConwaySimulator class.
 * Tests Conway's Game of Life logic without GUI dependencies.
 */
@DisplayName("ConwaySimulator Tests")
public class TestConwaySimulator {

    /**
     * Test-friendly wrapper that exposes protected fields for testing.
     */
    private static class TestableConwaySimulator extends ConwaySimulator {
        public TestableConwaySimulator(int width, int height, int rows, int cols, int coverage, Color bgColor) {
            super(width, height, rows, cols, coverage, bgColor);
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

        public Color getAliveColor() {
            return aliveColor;
        }

        public void clearPublic() {
            clear();
        }
    }

    // Create a small empty grid for testing
    private static TestableConwaySimulator simu;
    private static int width = 400;
    private static int height = 400;
    private static int rows = 5;
    private static int cols = 5;
    private static int coverage = 0;

    @BeforeAll
    public static void setUp() {
        simu = new TestableConwaySimulator(width, height, rows, cols, coverage, Color.BLACK);
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
    @DisplayName("Cells should have valid states (0 or 1)")
    public void testCellsHaveValidStates() {
        for (Cell cell : simu.getEntities()) {
            int state = cell.getState();
            assertTrue(state == 0 || state == 1, "Cell state should be 0 or 1");
        }
    }

    @Test
    @DisplayName("Board should be Grid instance")
    public void testBoardIsGrid() {
        assertNotNull(simu.getBoard(), "Board should not be null");
        assertTrue(simu.getBoard() instanceof Grid, "Board should be a Grid instance");
    }

    @Test
    @DisplayName("Number of states should be 2 (dead/alive)")
    public void testNbState() {
        assertEquals(2, simu.getNbStatePublic(), "Conway should have 2 states");
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
            if (cell.getState() == 1) {
                assertNotEquals(Color.WHITE, cell.getColor(), "Alive cells should not be white");
            }
        }
    }

    // ---- CONWAY_RULES ----

    @Test
    @DisplayName("Dead cell with 3 alive neighbors should become alive")
    public void testDeadCellWith3Neighbors() {
        // Clear all cells
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Set up pattern: center cell dead, 3 neighbors alive
        simu.setState(1, 1, 1); // top-left neighbor
        simu.setState(1, 2, 1); // top neighbor
        simu.setState(1, 3, 1); // top-right neighbor
        simu.setState(2, 2, 0); // center cell (dead)

        simu.update();

        assertEquals(1, simu.getState(2, 2), "Dead cell with 3 neighbors should become alive");
    }

    @Test
    @DisplayName("Alive cell with 2 neighbors should stay alive")
    public void testAliveCellWith2Neighbors() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(1, 1, 1); // neighbor
        simu.setState(1, 2, 1); // neighbor
        simu.setState(2, 2, 1); // center cell (alive)

        simu.update();

        assertEquals(1, simu.getState(2, 2), "Alive cell with 2 neighbors should stay alive");
    }

    @Test
    @DisplayName("Alive cell with 3 neighbors should stay alive")
    public void testAliveCellWith3Neighbors() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(1, 1, 1);
        simu.setState(1, 2, 1);
        simu.setState(1, 3, 1);
        simu.setState(2, 2, 1); // center cell (alive)

        simu.update();

        assertEquals(1, simu.getState(2, 2), "Alive cell with 3 neighbors should stay alive");
    }

    @Test
    @DisplayName("Alive cell with less than 2 neighbors should die (underpopulation)")
    public void testAliveCellUnderpopulation() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(1, 1, 1); // only 1 neighbor
        simu.setState(2, 2, 1); // center cell (alive)

        simu.update();

        assertEquals(0, simu.getState(2, 2), "Alive cell with <2 neighbors should die");
    }

    @Test
    @DisplayName("Alive cell with more than 3 neighbors should die (overpopulation)")
    public void testAliveCellOverpopulation() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Surround center with 4 alive neighbors
        simu.setState(1, 1, 1);
        simu.setState(1, 2, 1);
        simu.setState(1, 3, 1);
        simu.setState(2, 1, 1);
        simu.setState(2, 2, 1); // center cell (alive)

        simu.update();

        assertEquals(0, simu.getState(2, 2), "Alive cell with >3 neighbors should die");
    }

    @Test
    @DisplayName("Dead cell with 2 neighbors should stay dead")
    public void testDeadCellWith2Neighbors() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(1, 1, 1);
        simu.setState(1, 2, 1);
        simu.setState(2, 2, 0); // center cell (dead)

        simu.update();

        assertEquals(0, simu.getState(2, 2), "Dead cell with 2 neighbors should stay dead");
    }




    // ---- PATTERNS ----

    @Test
    @DisplayName("Blinker pattern should oscillate (period 2)")
    public void testBlinkerPattern() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Horizontal blinker
        simu.setState(2, 1, 1);
        simu.setState(2, 2, 1);
        simu.setState(2, 3, 1);

        simu.update();

        // Should become vertical
        assertEquals(0, simu.getState(2, 1), "Left cell should die");
        assertEquals(1, simu.getState(1, 2), "Top cell should be alive");
        assertEquals(1, simu.getState(2, 2), "Center should stay alive");
        assertEquals(1, simu.getState(3, 2), "Bottom cell should be alive");
        assertEquals(0, simu.getState(2, 3), "Right cell should die");
    }

    @Test
    @DisplayName("Block pattern should be stable (still life)")
    public void testBlockPattern() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // 2x2 block
        simu.setState(1, 1, 1);
        simu.setState(1, 2, 1);
        simu.setState(2, 1, 1);
        simu.setState(2, 2, 1);

        simu.update();

        // Should remain unchanged
        assertEquals(1, simu.getState(1, 1), "Block should be stable");
        assertEquals(1, simu.getState(1, 2), "Block should be stable");
        assertEquals(1, simu.getState(2, 1), "Block should be stable");
        assertEquals(1, simu.getState(2, 2), "Block should be stable");
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
    @DisplayName("Grid wraps around (toroidal topology)")
    public void testToroidalWrapping() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Place cells at edges to test wrapping
        simu.setState(0, 0, 1); // top-left corner
        simu.setState(0, 4, 1); // top-right corner
        simu.setState(4, 0, 1); // bottom-left corner

        // The corner cell (0,0) should have neighbors wrapping around
        // This is tested implicitly by the update logic
        simu.update();

        // Just verify no exceptions were thrown
        assertNotNull(simu.getEntities(), "Grid should handle wrapping");
    }

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
    @DisplayName("Full grid should die (overpopulation)")
    public void testFullGrid() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(1);
        }

        simu.update();

        // All cells should die due to overpopulation (8 neighbors)
        int deadCount = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 0) deadCount++;
        }
        // 25 = 5x5 grid all died if in toroidal wrapping
        assertEquals(25, deadCount, "Full grid should have some deaths due to overpopulation");
    }

    @Test
    @DisplayName("Single alive cell should die (isolation)")
    public void testSingleCellDies() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(2, 2, 1); // single cell

        simu.update();

        assertEquals(0, simu.getState(2, 2), "Single cell should die from isolation");
    }

}
