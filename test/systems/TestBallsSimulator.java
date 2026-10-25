package systems;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import entities.Particle;
import world.Board.BoundsMode;
import world.Space;
import java.awt.Color;
import java.util.List;
/**
 * Unit tests for the BallsSimulator class.
 * Tests simulation logic without GUI dependencies.
 */
@DisplayName("BallsSimulator Tests")
public class TestBallsSimulator {

    // Extension de BallsSimulator pour accès aux attributs privés
    private static class TestableBallsSimulator extends BallsSimulator {
        public TestableBallsSimulator(int width, int height, int n, int size) {
            super(width, height, n, size);
        }

        public List<Particle> getEntities() {
            return entities;
        }

        public Space getBoard() {
            return board;
        }

        public int getRows() {
            return rows;
        }

        public int getCols() {
            return cols;
        }
    }

    private static TestableBallsSimulator simu;
    private static int width = 800;
    private static int height = 600;
    private static int nbBalls = 10;
    private static int size = 20;

    @BeforeAll
    public static void setUp() {
        simu = new TestableBallsSimulator(width, height, nbBalls, size);
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
    @DisplayName("Simulator should create correct number of balls")
    public void testNumberOfBalls() {
        assertEquals(nbBalls, simu.getEntities().size(), "Should have " + nbBalls + " balls");
    }

    @Test
    @DisplayName("All balls should be Particle instances")
    public void testBallsAreParticles() {
        for (Particle ball : simu.getEntities()) {
            assertNotNull(ball, "Ball should not be null");
            assertTrue(ball instanceof Particle, "Ball should be a Particle instance");
        }
    }

    @Test
    @DisplayName("All balls should have valid positions within bounds")
    public void testBallsHaveValidPositions() {
        int minX = simu.getBoard().minXUnits();
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        int minY = simu.getBoard().minYUnits();
        int maxY = simu.getBoard().maxYUnits(simu.getRows());

        for (Particle ball : simu.getEntities()) {
            int x = ball.getX();
            int y = ball.getY();
            assertTrue(x >= minX && x <= maxX,
                "Ball X position " + x + " should be within [" + minX + ", " + maxX + "]");
            assertTrue(y >= minY && y <= maxY,
                "Ball Y position " + y + " should be within [" + minY + ", " + maxY + "]");
        }
    }

    @Test
    @DisplayName("All balls should have non-zero velocity")
    public void testBallsHaveVelocity() {
        for (Particle ball : simu.getEntities()) {
            int vx = ball.getVx();
            int vy = ball.getVy();
            assertTrue(vx != 0 || vy != 0,
                "Ball should have non-zero velocity, got vx=" + vx + ", vy=" + vy);
        }
    }

    @Test
    @DisplayName("All balls should have valid velocity range")
    public void testBallsVelocityRange() {
        for (Particle ball : simu.getEntities()) {
            int vx = ball.getVx();
            int vy = ball.getVy();
            // Based on init(): sx/sy are in [1,3], then negated randomly
            assertTrue(Math.abs(vx) >= 1 && Math.abs(vx) <= 3,
                "Ball vx should be in range [-3, -1] or [1, 3], got " + vx);
            assertTrue(Math.abs(vy) >= 1 && Math.abs(vy) <= 3,
                "Ball vy should be in range [-3, -1] or [1, 3], got " + vy);
        }
    }

    @Test
    @DisplayName("All balls should have valid colors")
    public void testBallsHaveColors() {
        for (Particle ball : simu.getEntities()) {
            Color color = ball.getColor();
            assertNotNull(color, "Ball color should not be null");
            // Based on init(): RGB components are in [40, 255]
            assertTrue(color.getRed() >= 40 && color.getRed() <= 255,
                "Red component should be in [40, 255]");
            assertTrue(color.getGreen() >= 40 && color.getGreen() <= 255,
                "Green component should be in [40, 255]");
            assertTrue(color.getBlue() >= 40 && color.getBlue() <= 255,
                "Blue component should be in [40, 255]");
        }
    }

    @Test
    @DisplayName("Board should be Space instance")
    public void testBoardIsSpace() {
        assertNotNull(simu.getBoard(), "Board should not be null");
        assertTrue(simu.getBoard() instanceof world.Space, "Board should be a Space instance");
    }

    @Test
    @DisplayName("Board should default to BOUNCE mode")
    public void testBoardDefaultBounceMode() {
        assertEquals(BoundsMode.BOUNCE, simu.getBoard().getBoundsMode(),
            "Board should default to BOUNCE mode");
    }

    // ---- UPDATE_LOGIC ----

    @Test
    @DisplayName("Update should move balls")
    public void testUpdateMovesBalls() {
        Particle firstBall = simu.getEntities().get(0);
        int oldX = firstBall.getX();
        int oldY = firstBall.getY();
        int vx = firstBall.getVx();
        int vy = firstBall.getVy();

        simu.update();

        int newX = firstBall.getX();
        int newY = firstBall.getY();

        // Position should change by velocity (unless it bounced)
        boolean movedCorrectly = (newX == oldX + vx && newY == oldY + vy) ||
                                 (newX != oldX || newY != oldY);
        assertTrue(movedCorrectly, "Ball should move after update");
    }

    @Test
    @DisplayName("Multiple updates should continue moving balls")
    public void testMultipleUpdates() {
        Particle ball = simu.getEntities().get(0);

        // Store initial state
        int initialX = ball.getX();
        int initialY = ball.getY();

        // Run multiple updates
        for (int i = 0; i < 5; i++) {
            simu.update();
        }

        // Ball should have moved from initial position
        boolean moved = (ball.getX() != initialX) || (ball.getY() != initialY);
        assertTrue(moved, "Ball should move after multiple updates");
    }

    @Test
    @DisplayName("All balls should remain within bounds after update")
    public void testBallsStayInBounds() {
        int minX = simu.getBoard().minXUnits();
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        int minY = simu.getBoard().minYUnits();
        int maxY = simu.getBoard().maxYUnits(simu.getRows());

        // Run multiple updates
        for (int i = 0; i < 20; i++) {
            simu.update();
        }

        // Check all balls are still in bounds
        for (Particle ball : simu.getEntities()) {
            int x = ball.getX();
            int y = ball.getY();
            assertTrue(x >= minX && x <= maxX,
                "Ball X position " + x + " should be within [" + minX + ", " + maxX + "]");
            assertTrue(y >= minY && y <= maxY,
                "Ball Y position " + y + " should be within [" + minY + ", " + maxY + "]");
        }
    }

    // ---- BOUNCE_MODE ----

    @Test
    @DisplayName("Ball should bounce off right wall")
    public void testBallBouncesRightWall() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        Particle ball = simu.getEntities().get(0);

        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        ball.setPosition(maxX, 10);
        ball.setVelocity(3, 0); // Moving right toward wall

        int oldVx = ball.getVx();
        simu.update();

        // Velocity should reverse
        assertEquals(-oldVx, ball.getVx(), "Vx should reverse after hitting right wall");
        // Position should be clamped at wall
        assertTrue(ball.getX() <= maxX, "Ball should not exceed right wall");
    }

    @Test
    @DisplayName("Ball should bounce off left wall")
    public void testBallBouncesLeftWall() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        Particle ball = simu.getEntities().get(0);

        int minX = simu.getBoard().minXUnits();
        ball.setPosition(minX, 10);
        ball.setVelocity(-3, 0); // Moving left toward wall

        int oldVx = ball.getVx();
        simu.update();

        // Velocity should reverse
        assertEquals(-oldVx, ball.getVx(), "Vx should reverse after hitting left wall");
        // Position should be clamped at wall
        assertTrue(ball.getX() >= minX, "Ball should not go below left wall");
    }

    @Test
    @DisplayName("Ball should bounce off bottom wall")
    public void testBallBouncesBottomWall() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        Particle ball = simu.getEntities().get(0);

        int maxY = simu.getBoard().maxYUnits(simu.getRows());
        ball.setPosition(10, maxY);
        ball.setVelocity(0, 3); // Moving down toward wall

        int oldVy = ball.getVy();
        simu.update();

        // Velocity should reverse
        assertEquals(-oldVy, ball.getVy(), "Vy should reverse after hitting bottom wall");
        // Position should be clamped at wall
        assertTrue(ball.getY() <= maxY, "Ball should not exceed bottom wall");
    }

    @Test
    @DisplayName("Ball should bounce off top wall")
    public void testBallBouncesTopWall() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        Particle ball = simu.getEntities().get(0);

        int minY = simu.getBoard().minYUnits();
        ball.setPosition(10, minY);
        ball.setVelocity(0, -3); // Moving up toward wall

        int oldVy = ball.getVy();
        simu.update();

        // Velocity should reverse
        assertEquals(-oldVy, ball.getVy(), "Vy should reverse after hitting top wall");
        // Position should be clamped at wall
        assertTrue(ball.getY() >= minY, "Ball should not go below top wall");
    }

    @Test
    @DisplayName("Ball should bounce off corner")
    public void testBallBouncesCorner() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        Particle ball = simu.getEntities().get(0);

        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        int maxY = simu.getBoard().maxYUnits(simu.getRows());
        ball.setPosition(maxX, maxY);
        ball.setVelocity(3, 3); // Moving toward bottom-right corner

        int oldVx = ball.getVx();
        int oldVy = ball.getVy();
        simu.update();

        // Both velocities should reverse
        assertEquals(-oldVx, ball.getVx(), "Vx should reverse after hitting corner");
        assertEquals(-oldVy, ball.getVy(), "Vy should reverse after hitting corner");
    }

    // ---- WRAP_MODE ----

    @Test
    @DisplayName("Ball should wrap from right to left in WRAP mode")
    public void testBallWrapsRightToLeft() {
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);
        Particle ball = simu.getEntities().get(0);

        int minX = simu.getBoard().minXUnits();
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        ball.setPosition(maxX, 10);
        ball.setVelocity(3, 0); // Moving right

        int oldVx = ball.getVx();
        simu.update();

        // Velocity should NOT change in wrap mode
        assertEquals(oldVx, ball.getVx(), "Vx should not change in WRAP mode");
        // Position should wrap to left side
        assertEquals(minX, ball.getX(), "Ball should wrap to left side");
    }

    @Test
    @DisplayName("Ball should wrap from left to right in WRAP mode")
    public void testBallWrapsLeftToRight() {
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);
        Particle ball = simu.getEntities().get(0);

        int minX = simu.getBoard().minXUnits();
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        ball.setPosition(minX, 10);
        ball.setVelocity(-3, 0); // Moving left

        int oldVx = ball.getVx();
        simu.update();

        // Velocity should NOT change in wrap mode
        assertEquals(oldVx, ball.getVx(), "Vx should not change in WRAP mode");
        // Position should wrap to right side
        assertEquals(maxX, ball.getX(), "Ball should wrap to right side");
    }

    @Test
    @DisplayName("Ball should wrap from bottom to top in WRAP mode")
    public void testBallWrapsBottomToTop() {
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);
        Particle ball = simu.getEntities().get(0);

        int minY = simu.getBoard().minYUnits();
        int maxY = simu.getBoard().maxYUnits(simu.getRows());
        ball.setPosition(10, maxY);
        ball.setVelocity(0, 3); // Moving down

        int oldVy = ball.getVy();
        simu.update();

        // Velocity should NOT change in wrap mode
        assertEquals(oldVy, ball.getVy(), "Vy should not change in WRAP mode");
        // Position should wrap to top side
        assertEquals(minY, ball.getY(), "Ball should wrap to top side");
    }

    @Test
    @DisplayName("Ball should wrap from top to bottom in WRAP mode")
    public void testBallWrapsTopToBottom() {
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);
        Particle ball = simu.getEntities().get(0);

        int minY = simu.getBoard().minYUnits();
        int maxY = simu.getBoard().maxYUnits(simu.getRows());
        ball.setPosition(10, minY);
        ball.setVelocity(0, -3); // Moving up

        int oldVy = ball.getVy();
        simu.update();

        // Velocity should NOT change in wrap mode
        assertEquals(oldVy, ball.getVy(), "Vy should not change in WRAP mode");
        // Position should wrap to bottom side
        assertEquals(maxY, ball.getY(), "Ball should wrap to bottom side");
    }

    @Test
    @DisplayName("Ball should wrap in both directions in WRAP mode")
    public void testBallWrapsBothDirections() {
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);
        Particle ball = simu.getEntities().get(0);

        int minX = simu.getBoard().minXUnits();
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        int minY = simu.getBoard().minYUnits();
        int maxY = simu.getBoard().maxYUnits(simu.getRows());

        ball.setPosition(maxX, maxY);
        ball.setVelocity(3, 3); // Moving right and down

        int oldVx = ball.getVx();
        int oldVy = ball.getVy();
        simu.update();

        // Velocities should NOT change in wrap mode
        assertEquals(oldVx, ball.getVx(), "Vx should not change in WRAP mode");
        assertEquals(oldVy, ball.getVy(), "Vy should not change in WRAP mode");
        // Position should wrap to opposite corner
        assertEquals(minX, ball.getX(), "Ball X should wrap to left side");
        assertEquals(minY, ball.getY(), "Ball Y should wrap to top side");
    }

    // ---- CLEAR_LOGIC ----

    @Test
    @DisplayName("Clear should reset all ball velocities to zero")
    public void testClearResetsVelocities() {
        // Verify balls have non-zero velocities initially
        for (Particle ball : simu.getEntities()) {
            assertTrue(ball.getVx() != 0 || ball.getVy() != 0,
                "Ball should have non-zero velocity before clear");
        }

        simu.clear();

        // All balls should have zero velocity after clear
        for (Particle ball : simu.getEntities()) {
            assertEquals(0, ball.getVx(), "Vx should be 0 after clear");
            assertEquals(0, ball.getVy(), "Vy should be 0 after clear");
        }
    }

    @Test
    @DisplayName("Clear should not change ball positions")
    public void testClearDoesNotChangePositions() {
        // Store positions before clear
        int[] xPositions = new int[simu.getEntities().size()];
        int[] yPositions = new int[simu.getEntities().size()];
        for (int i = 0; i < simu.getEntities().size(); i++) {
            xPositions[i] = simu.getEntities().get(i).getX();
            yPositions[i] = simu.getEntities().get(i).getY();
        }

        simu.clear();

        // Positions should remain the same
        for (int i = 0; i < simu.getEntities().size(); i++) {
            assertEquals(xPositions[i], simu.getEntities().get(i).getX(),
                "X position should not change after clear");
            assertEquals(yPositions[i], simu.getEntities().get(i).getY(),
                "Y position should not change after clear");
        }
    }

    @Test
    @DisplayName("Clear should not change ball colors")
    public void testClearDoesNotChangeColors() {
        // Store colors before clear
        Color[] colors = new Color[simu.getEntities().size()];
        for (int i = 0; i < simu.getEntities().size(); i++) {
            colors[i] = simu.getEntities().get(i).getColor();
        }

        simu.clear();

        // Colors should remain the same
        for (int i = 0; i < simu.getEntities().size(); i++) {
            assertEquals(colors[i], simu.getEntities().get(i).getColor(),
                "Color should not change after clear");
        }
    }

    @Test
    @DisplayName("Clear should not change number of balls")
    public void testClearDoesNotChangeCount() {
        int countBefore = simu.getEntities().size();
        simu.clear();
        int countAfter = simu.getEntities().size();

        assertEquals(countBefore, countAfter, "Number of balls should not change after clear");
    }

    // ---- EDGE_CASES ----

    @Test
    @DisplayName("Simulator with 1 ball should work")
    public void testSingleBall() {
        TestableBallsSimulator singleBall = new TestableBallsSimulator(800, 600, 1, 20);
        assertEquals(1, singleBall.getEntities().size(), "Should have 1 ball");
        assertNotNull(singleBall.getEntities().get(0), "Ball should not be null");
    }

    @Test
    @DisplayName("Simulator with many balls should work")
    public void testManyBalls() {
        TestableBallsSimulator manyBalls = new TestableBallsSimulator(800, 600, 100, 20);
        assertEquals(100, manyBalls.getEntities().size(), "Should have 100 balls");

        // All balls should be valid
        for (Particle ball : manyBalls.getEntities()) {
            assertNotNull(ball, "Ball should not be null");
        }
    }

    @Test
    @DisplayName("Balls should be independent of each other")
    public void testBallsAreIndependent() {
        assertTrue(simu.getEntities().size() >= 2, "Need at least 2 balls for this test");

        Particle ball1 = simu.getEntities().get(0);
        Particle ball2 = simu.getEntities().get(1);

        int ball1X = ball1.getX();
        int ball2X = ball2.getX();

        // Modify ball1
        ball1.setPosition(999, 999);
        ball1.setVelocity(10, 10);

        // ball2 should not be affected
        assertEquals(ball2X, ball2.getX(), "Ball2 position should not change when ball1 is modified");
    }

    @Test
    @DisplayName("Update after clear should not move balls")
    public void testUpdateAfterClear() {
        simu.clear();

        // Store positions after clear
        int[] xPositions = new int[simu.getEntities().size()];
        int[] yPositions = new int[simu.getEntities().size()];
        for (int i = 0; i < simu.getEntities().size(); i++) {
            xPositions[i] = simu.getEntities().get(i).getX();
            yPositions[i] = simu.getEntities().get(i).getY();
        }

        simu.update();

        // Positions should remain the same (velocity is 0)
        for (int i = 0; i < simu.getEntities().size(); i++) {
            assertEquals(xPositions[i], simu.getEntities().get(i).getX(),
                "X position should not change after update with zero velocity");
            assertEquals(yPositions[i], simu.getEntities().get(i).getY(),
                "Y position should not change after update with zero velocity");
        }
    }

    @Test
    @DisplayName("Ball moving parallel to wall should not bounce")
    public void testBallParallelToWall() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        Particle ball = simu.getEntities().get(0);

        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        ball.setPosition(maxX, 10);
        ball.setVelocity(0, 2); // Moving parallel to right wall

        int oldVx = ball.getVx();
        simu.update();

        // Vx should not change (not hitting wall in X direction)
        assertEquals(oldVx, ball.getVx(), "Vx should not change when moving parallel to wall");
    }

    @Test
    @DisplayName("Switching from BOUNCE to WRAP mode should work")
    public void testSwitchBounceToWrap() {
        // Start in BOUNCE mode (default)
        assertEquals(BoundsMode.BOUNCE, simu.getBoard().getBoundsMode());

        // Switch to WRAP mode
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);
        assertEquals(BoundsMode.WRAP, simu.getBoard().getBoundsMode());

        // Test wrap behavior
        Particle ball = simu.getEntities().get(0);
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        int minX = simu.getBoard().minXUnits();
        ball.setPosition(maxX, 10);
        ball.setVelocity(3, 0);

        simu.update();

        // Should wrap, not bounce
        assertEquals(minX, ball.getX(), "Ball should wrap in WRAP mode");
    }

    @Test
    @DisplayName("Switching from WRAP to BOUNCE mode should work")
    public void testSwitchWrapToBounce() {
        // Switch to WRAP mode first
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);

        // Switch back to BOUNCE mode
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);
        assertEquals(BoundsMode.BOUNCE, simu.getBoard().getBoundsMode());

        // Test bounce behavior
        Particle ball = simu.getEntities().get(0);
        int maxX = simu.getBoard().maxXUnits(simu.getCols());
        ball.setPosition(maxX, 10);
        ball.setVelocity(3, 0);

        int oldVx = ball.getVx();
        simu.update();

        // Should bounce, not wrap
        assertEquals(-oldVx, ball.getVx(), "Ball should bounce in BOUNCE mode");
    }

    @Test
    @DisplayName("Ball with zero velocity should not move")
    public void testBallWithZeroVelocity() {
        Particle ball = simu.getEntities().get(0);
        ball.setVelocity(0, 0);

        int oldX = ball.getX();
        int oldY = ball.getY();

        simu.update();

        assertEquals(oldX, ball.getX(), "Ball with zero velocity should not move in X");
        assertEquals(oldY, ball.getY(), "Ball with zero velocity should not move in Y");
    }
}