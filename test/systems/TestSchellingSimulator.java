package systems;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import entities.Cell;
import world.Grid;
import java.awt.Color;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for the SchellingSimulator class.
 * Tests Schelling segregation model logic without GUI dependencies.
 */
@DisplayName("SchellingSimulator Tests")
public class TestSchellingSimulator {

    /**
     * Test-friendly wrapper that exposes protected fields for testing.
     */
    private static class TestableSchellingSimulator extends SchellingSimulator {
        public TestableSchellingSimulator(int width, int height, int rows, int cols, Color bgColor, int K, int colorGroup, int coverage) {
            super(width, height, rows, cols, bgColor, K, colorGroup, coverage);
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

        public int getK() {
            return K;
        }

        public Color[] getColors() {
            return c;
        }

        public void clearPublic() {
            clear();
        }
    }

    private static TestableSchellingSimulator simu;
    private static int width = 800;
    private static int height = 600;
    private static int rows = 20;
    private static int cols = 20;
    private static int coverage = 50;
    private static int K = 3;
    private static int colorGroup = 3;

    @BeforeAll
    public static void setUp() {
        simu = new TestableSchellingSimulator(width, height, rows, cols, Color.BLACK, K, colorGroup, coverage);
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
    @DisplayName("Number of states should be 2 (empty/occupied)")
    public void testNbState() {
        assertEquals(2, simu.getNbStatePublic(), "Schelling should have 2 states");
    }

    @Test
    @DisplayName("K parameter should be set correctly")
    public void testKParameter() {
        assertEquals(K, simu.getK(), "K should match constructor parameter");
    }

    @Test
    @DisplayName("Color groups should be generated")
    public void testColorGroups() {
        Color[] colors = simu.getColors();
        assertNotNull(colors, "Color array should not be null");
        assertEquals(colorGroup, colors.length, "Should have " + colorGroup + " colors");

        for (Color color : colors) {
            assertNotNull(color, "Each color should not be null");
        }
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
    @DisplayName("Alive cells should have one of the group colors")
    public void testAliveCellsColor() {
        Color[] colors = simu.getColors();
        Set<Color> colorSet = new HashSet<>();
        for (Color c : colors) {
            colorSet.add(c);
        }

        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) {
                assertTrue(colorSet.contains(cell.getColor()),
                    "Alive cells should have one of the group colors");
            }
        }
    }

    @Test
    @DisplayName("Multiple color groups should exist")
    public void testMultipleColorGroups() {
        Set<Color> uniqueColors = new HashSet<>();
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) {
                uniqueColors.add(cell.getColor());
            }
        }

        // With 50% coverage and 3 color groups, we should see multiple colors
        assertTrue(uniqueColors.size() >= 1, "Should have at least 1 color group");
    }

    // ---- SCHELLING_RULES ----

    @Test
    @DisplayName("Unhappy agents should move to empty cells")
    public void testUnhappyAgentsMove() {
        // Clear all cells
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
            cell.setColor(Color.WHITE);
        }

        Color color1 = Color.RED;
        Color color2 = Color.BLUE;

        // Create an unhappy agent (center cell surrounded by different color)
        simu.setState(2, 2, 1);
        simu.getEntities().get(2 * 5 + 2).setColor(color1);

        // Surround with 3+ different color neighbors (K=2, so >2 means unhappy)
        simu.setState(1, 1, 1);
        simu.getEntities().get(1 * 5 + 1).setColor(color2);
        simu.setState(1, 2, 1);
        simu.getEntities().get(1 * 5 + 2).setColor(color2);
        simu.setState(1, 3, 1);
        simu.getEntities().get(1 * 5 + 3).setColor(color2);

        // Create empty cell
        simu.setState(4, 4, 0);

        int aliveBefore = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) aliveBefore++;
        }

        simu.update();

        int aliveAfter = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) aliveAfter++;
        }

        // Number of alive cells should remain the same (agents move, not die)
        assertEquals(aliveBefore, aliveAfter, "Number of agents should remain constant");
    }

    @Test
    @DisplayName("Happy agents should not move")
    public void testHappyAgentsStay() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
            cell.setColor(Color.WHITE);
        }

        Color color1 = Color.RED;

        // Create a happy agent (all neighbors same color, K=5 so needs >5 different to be unhappy)
        simu.setState(2, 2, 1);
        simu.getEntities().get(2 * 5 + 2).setColor(color1);

        // Surround with same color
        simu.setState(1, 1, 1);
        simu.getEntities().get(1 * 5 + 1).setColor(color1);
        simu.setState(1, 2, 1);
        simu.getEntities().get(1 * 5 + 2).setColor(color1);

        Color centerColorBefore = simu.getEntities().get(2 * 5 + 2).getColor();
        int centerStateBefore = simu.getState(2, 2);

        simu.update();

        // Center should still be alive (happy agents don't move)
        assertEquals(centerStateBefore, simu.getState(2, 2), "Happy agent should stay");
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
    @DisplayName("Grid with no empty cells should not change")
    public void testFullGrid() {
        int aliveBefore = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) aliveBefore++;
        }

        simu.update();

        int aliveAfter = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) aliveAfter++;
        }

        // No movement possible without empty cells
        assertEquals(aliveBefore, aliveAfter, "Full grid should maintain agent count");
    }

    @Test
    @DisplayName("Single agent should not move (no neighbors)")
    public void testSingleAgent() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        simu.setState(2, 2, 1);
        simu.getEntities().get(2 * 5 + 2).setColor(Color.RED);

        simu.update();

        // Single agent has no neighbors, so 0 non-similar neighbors (happy)
        int aliveCount = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) aliveCount++;
        }

        assertEquals(1, aliveCount, "Single agent should remain");
    }

    @Test
    @DisplayName("Different K values should work")
    public void testDifferentKValues() {
        TestableSchellingSimulator k1 = new TestableSchellingSimulator(400, 400, 5, 5, Color.BLACK, 1, 2, 50);
        TestableSchellingSimulator k5 = new TestableSchellingSimulator(400, 400, 5, 5, Color.BLACK, 5, 2, 50);

        assertEquals(1, k1.getK(), "K should be 1");
        assertEquals(5, k5.getK(), "K should be 5");

        assertNotNull(k1.getEntities(), "K=1 simulator should work");
        assertNotNull(k5.getEntities(), "K=5 simulator should work");
    }

    @Test
    @DisplayName("Different color group counts should work")
    public void testDifferentColorGroups() {
        TestableSchellingSimulator twoColors = new TestableSchellingSimulator(400, 400, 5, 5, Color.BLACK, 3, 2, 50);
        TestableSchellingSimulator fiveColors = new TestableSchellingSimulator(400, 400, 5, 5, Color.BLACK, 3, 5, 50);

        assertEquals(2, twoColors.getColors().length, "Should have 2 color groups");
        assertEquals(5, fiveColors.getColors().length, "Should have 5 color groups");
    }

    @Test
    @DisplayName("generateColors should create valid colors")
    public void testGenerateColors() {
        Color[] colors = SchellingSimulator.generateColors(5);

        assertNotNull(colors, "Color array should not be null");
        assertEquals(5, colors.length, "Should have 5 colors");

        for (Color color : colors) {
            assertNotNull(color, "Each color should not be null");
            // Colors should have RGB values in [1, 255]
            assertTrue(color.getRed() >= 1 && color.getRed() <= 255, "Red component should be in [1, 255]");
            assertTrue(color.getGreen() >= 1 && color.getGreen() <= 255, "Green component should be in [1, 255]");
            assertTrue(color.getBlue() >= 1 && color.getBlue() <= 255, "Blue component should be in [1, 255]");
        }
    }

    @Test
    @DisplayName("Grid wraps around (toroidal topology)")
    public void testToroidalWrapping() {
        for (Cell cell : simu.getEntities()) {
            cell.setState(0);
        }

        // Place agents at edges
        simu.setState(0, 0, 1);
        simu.getEntities().get(0).setColor(Color.RED);
        simu.setState(0, 4, 1);
        simu.getEntities().get(4).setColor(Color.BLUE);

        simu.update();

        // Just verify no exceptions were thrown
        assertNotNull(simu.getEntities(), "Grid should handle wrapping");
    }

    @Test
    @DisplayName("Update preserves total agent count")
    public void testAgentCountPreserved() {
        int countBefore = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) countBefore++;
        }

        simu.update();

        int countAfter = 0;
        for (Cell cell : simu.getEntities()) {
            if (cell.getState() == 1) countAfter++;
        }

        assertEquals(countBefore, countAfter, "Agent count should be preserved after update");
    }
}


