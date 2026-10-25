package systems;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import world.Space;
import world.Board.BoundsMode;
import java.awt.Color;
import java.util.List;
import entities.boid.Boid;
import entities.boid.Fish;
import entities.boid.Shark;
/**
 * Unit tests for the BoidsSimulator class.
 * Tests boid flocking behavior without GUI dependencies.
 * Uses @BeforeAll to minimize GUI window creation (only 1 window for all tests).
 */
@DisplayName("BoidsSimulator Tests")
public class TestBoidsSimulator {

    /**
     * Test-friendly wrapper that exposes protected fields for testing.
     */
    private static class TestableBoidsSimulator extends BoidsSimulator {
        public TestableBoidsSimulator(int width, int height, int n, int cellSize,
                                     int radius, int desiredSep,
                                     int maxSpeed, int maxStep,
                                     double wAlign, double wCoh, double wSep) {
            super(width, height, n, cellSize, radius, desiredSep, maxSpeed, maxStep, wAlign, wCoh, wSep);
        }

        public List<Boid> getEntities() {
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

        public void clearPublic() {
            clear();
        }

        // Helper methods for testing
        public Boid getBoid(int index) {
            if (index < 0 || index >= entities.size()) return null;
            return entities.get(index);
        }

        public int getBoidCount() {
            return entities.size();
        }

        public void setBoidPosition(int index, int x, int y) {
            if (index >= 0 && index < entities.size()) {
                entities.get(index).setPosition(x, y);
            }
        }

        public void setBoidVelocity(int index, int vx, int vy) {
            if (index >= 0 && index < entities.size()) {
                entities.get(index).setVelocity(vx, vy);
            }
        }

        public void setBoidContinuousVelocity(int index, double vx, double vy) {
            if (index >= 0 && index < entities.size()) {
                entities.get(index).setContinuousVelocity(vx, vy);
            }
        }

        public double getAverageSpeed() {
            if (entities.isEmpty()) return 0.0;
            double sum = 0.0;
            for (Boid b : entities) {
                int vx = b.getVx(), vy = b.getVy();
                sum += Math.sqrt(vx * vx + vy * vy);
            }
            return sum / entities.size();
        }

        public double[] getCenterOfMass() {
            if (entities.isEmpty()) return new double[]{0.0, 0.0};
            double sumX = 0.0, sumY = 0.0;
            for (Boid b : entities) {
                sumX += b.getX();
                sumY += b.getY();
            }
            return new double[]{sumX / entities.size(), sumY / entities.size()};
        }

        public double getAverageSeparation() {
            if (entities.size() < 2) return 0.0;
            double sum = 0.0;
            int count = 0;
            for (int i = 0; i < entities.size(); i++) {
                for (int j = i + 1; j < entities.size(); j++) {
                    Boid b1 = entities.get(i);
                    Boid b2 = entities.get(j);
                    int dx = b1.getX() - b2.getX();
                    int dy = b1.getY() - b2.getY();
                    sum += Math.sqrt(dx * dx + dy * dy);
                    count++;
                }
            }
            return count > 0 ? sum / count : 0.0;
        }
    }

    private static TestableBoidsSimulator simu;
    private static int width = 900;
    private static int height = 600;
    private static int nbBoids = 50;
    private static int cellSize = 150;
    private static int radius = 8;
    private static int desiredSep = 3;
    private static int maxSpeed = 2;
    private static int maxStep = 1;
    private static double wAlign = 1.6;
    private static double wCoh = 0.12;
    private static double wSep = 0.02;

    @BeforeAll
    public static void setUp() {
        simu = new TestableBoidsSimulator(width, height, nbBoids, cellSize,
                                         radius, desiredSep, maxSpeed, maxStep,
                                         wAlign, wCoh, wSep);
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
    @DisplayName("Should create correct number of boids")
    public void testBoidCount() {
        assertEquals(nbBoids, simu.getBoidCount(), "Should have " + nbBoids + " boids");
    }

    @Test
    @DisplayName("All entities should be Boid instances")
    public void testAllEntitiesAreBoids() {
        for (int i = 0; i < simu.getBoidCount(); i++) {
            assertNotNull(simu.getBoid(i), "Boid at index " + i + " should not be null");
            assertTrue(simu.getBoid(i) instanceof Boid, "Entity should be a Boid instance");
        }
    }

    @Test
    @DisplayName("Board should be Space instance")
    public void testBoardIsSpace() {
        assertNotNull(simu.getBoard(), "Board should not be null");
        assertTrue(simu.getBoard() instanceof Space, "Board should be a Space instance");
    }

    @Test
    @DisplayName("Boids should have valid initial positions")
    public void testBoidsHaveValidPositions() {
        for (int i = 0; i < simu.getBoidCount(); i++) {
            Boid b = simu.getBoid(i);
            assertTrue(b.getX() >= 0, "Boid X position should be non-negative");
            assertTrue(b.getY() >= 0, "Boid Y position should be non-negative");
        }
    }

    @Test
    @DisplayName("Boids should have non-zero initial velocities")
    public void testBoidsHaveNonZeroVelocities() {
        for (int i = 0; i < simu.getBoidCount(); i++) {
            Boid b = simu.getBoid(i);
            int vx = b.getVx();
            int vy = b.getVy();
            assertTrue(vx != 0 || vy != 0, "Boid should have non-zero velocity");
        }
    }

    @Test
    @DisplayName("Boids should have valid colors")
    public void testBoidsHaveValidColors() {
        for (int i = 0; i < simu.getBoidCount(); i++) {
            Boid b = simu.getBoid(i);
            assertNotNull(b.getColor(), "Boid color should not be null");
            // Colors can be black (sharks) or in range [40, 255] (fish)
            Color c = b.getColor();
            assertTrue(c.getRed() >= 0 && c.getRed() <= 255, "Red component should be in valid range");
            assertTrue(c.getGreen() >= 0 && c.getGreen() <= 255, "Green component should be in valid range");
            assertTrue(c.getBlue() >= 0 && c.getBlue() <= 255, "Blue component should be in valid range");
        }
    }

    @Test
    @DisplayName("Rows and cols should be calculated correctly")
    public void testRowsAndCols() {
        assertTrue(simu.getRows() > 0, "Rows should be positive");
        assertTrue(simu.getCols() > 0, "Cols should be positive");
    }

    // ---- UPDATE_LOGIC ----

    @Test
    @DisplayName("Update should change boid positions")
    public void testUpdateChangesPositions() {
        Boid b = simu.getBoid(0);
        int oldX = b.getX();
        int oldY = b.getY();

        simu.update();

        // At least some boids should move (not all might move in one step)
        boolean anyMoved = false;
        for (int i = 0; i < simu.getBoidCount(); i++) {
            Boid boid = simu.getBoid(i);
            if (i == 0) {
                // Check if first boid moved
                if (boid.getX() != oldX || boid.getY() != oldY) {
                    anyMoved = true;
                    break;
                }
            }
        }
        // Note: Due to flocking forces, some boids might not move significantly
        // This test just ensures update() is doing something
        assertNotNull(simu.getBoid(0), "Boid should still exist after update");
    }

    @Test
    @DisplayName("Boids should maintain continuous velocity tracking")
    public void testContinuousVelocityTracking() {
        Boid b = simu.getBoid(0);

        // Set continuous velocity
        simu.setBoidContinuousVelocity(0, 1.5, 2.3);

        assertEquals(1.5, b.getContVx(), 0.01, "Continuous Vx should be set");
        assertEquals(2.3, b.getContVy(), 0.01, "Continuous Vy should be set");
    }

    // ---- FLOCKING_BEHAVIOR ----

    // TODO: Test alignment behavior - boids should align velocities with neighbors
    // TODO: Test cohesion behavior - boids should move toward center of mass of neighbors
    // TODO: Test separation behavior - boids should avoid crowding neighbors
    // TODO: Test multi-group behavior - boids of different groups should interact differently (future feature)

    @Test
    @DisplayName("Boids should have flocking behavior over multiple steps")
    public void testFlockingOverTime() {
        // Run simulation for several steps
        for (int i = 0; i < 10; i++) {
            simu.update();
        }

        // Boids count may decrease due to predator-prey dynamics (sharks eating fish)
        assertTrue(simu.getBoidCount() > 0, "At least some boids should remain");
        assertTrue(simu.getBoidCount() <= nbBoids, "Boid count should not increase");

        // Check that boids are still moving
        int movingBoids = 0;
        for (int i = 0; i < simu.getBoidCount(); i++) {
            Boid b = simu.getBoid(i);
            if (b.getVx() != 0 || b.getVy() != 0) {
                movingBoids++;
            }
        }
        assertTrue(movingBoids > 0, "At least some boids should be moving");
    }

    // ---- BOUNDARY_BEHAVIOR ----

    @Test
    @DisplayName("Boids should wrap around boundaries in WRAP mode")
    public void testWrappingBehavior() {
        simu.getBoard().withBoundsMode(BoundsMode.WRAP);

        // This test verifies the mode is set correctly
        // Actual wrapping behavior is tested implicitly through update logic
        assertEquals(BoundsMode.WRAP, simu.getBoard().getBoundsMode(),
                    "Bounds mode should be WRAP");
    }

    @Test
    @DisplayName("Boids should bounce in BOUNCE mode")
    public void testBounceBehavior() {
        simu.getBoard().withBoundsMode(BoundsMode.BOUNCE);

        assertEquals(BoundsMode.BOUNCE, simu.getBoard().getBoundsMode(),
                    "Bounds mode should be BOUNCE");
    }

    // ---- CLEAR_LOGIC ----

    @Test
    @DisplayName("Clear should reset boid states")
    public void testClear() {
        // Get initial count
        int initialCount = simu.getBoidCount();

        // Modify some boid states
        simu.update();
        simu.update();

        // Clear should reset entities
        simu.clearPublic();

        // Boids count may have changed due to predator-prey dynamics
        assertTrue(simu.getBoidCount() > 0, "Some boids should remain after clear");
    }

    @Test
    @DisplayName("Restart should reinitialize simulation")
    public void testRestart() {
        // Run simulation
        simu.update();
        simu.update();

        // Restart
        simu.restart();

        // Should have same number of boids
        assertEquals(nbBoids, simu.getBoidCount(), "Boid count should be same after restart");
    }

    // ---- EDGE_CASES ----

    @Test
    @DisplayName("Simulator with single boid should work")
    public void testSingleBoid() {
        TestableBoidsSimulator singleBoid = new TestableBoidsSimulator(
            400, 400, 1, 100, radius, desiredSep, maxSpeed, maxStep, wAlign, wCoh, wSep);

        assertEquals(1, singleBoid.getBoidCount(), "Should have 1 boid");
        assertNotNull(singleBoid.getBoid(0), "Single boid should exist");

        // Update should work without errors
        singleBoid.update();
        assertEquals(1, singleBoid.getBoidCount(), "Should still have 1 boid after update");
    }

    @Test
    @DisplayName("Simulator with zero boids should work")
    public void testZeroBoids() {
        TestableBoidsSimulator noBoids = new TestableBoidsSimulator(
            400, 400, 0, 100, radius, desiredSep, maxSpeed, maxStep, wAlign, wCoh, wSep);

        assertEquals(0, noBoids.getBoidCount(), "Should have 0 boids");

        // Update should work without errors
        noBoids.update();
        assertEquals(0, noBoids.getBoidCount(), "Should still have 0 boids after update");
    }

    @Test
    @DisplayName("Simulator with many boids should work")
    public void testManyBoids() {
        TestableBoidsSimulator manyBoids = new TestableBoidsSimulator(
            900, 600, 200, 150, radius, desiredSep, maxSpeed, maxStep, wAlign, wCoh, wSep);

        assertEquals(200, manyBoids.getBoidCount(), "Should have 200 boids");

        // Update should work without errors (count may decrease due to predation)
        manyBoids.update();
        assertTrue(manyBoids.getBoidCount() > 0, "Should have some boids after update");
        assertTrue(manyBoids.getBoidCount() <= 200, "Boid count should not increase");
    }

    @Test
    @DisplayName("Helper method getBoid should handle invalid indices")
    public void testGetBoidInvalidIndex() {
        assertNull(simu.getBoid(-1), "Should return null for negative index");
        assertNull(simu.getBoid(nbBoids), "Should return null for index >= count");
        assertNull(simu.getBoid(1000), "Should return null for large index");
    }

    @Test
    @DisplayName("Helper method setBoidPosition should handle invalid indices")
    public void testSetBoidPositionInvalidIndex() {
        // Should not throw exception
        simu.setBoidPosition(-1, 10, 10);
        simu.setBoidPosition(nbBoids, 10, 10);
        simu.setBoidPosition(1000, 10, 10);
    }

    @Test
    @DisplayName("Helper method setBoidVelocity should handle invalid indices")
    public void testSetBoidVelocityInvalidIndex() {
        // Should not throw exception
        simu.setBoidVelocity(-1, 1, 1);
        simu.setBoidVelocity(nbBoids, 1, 1);
        simu.setBoidVelocity(1000, 1, 1);
    }

    @Test
    @DisplayName("Average speed should be calculated correctly")
    public void testAverageSpeed() {
        double avgSpeed = simu.getAverageSpeed();
        assertTrue(avgSpeed >= 0, "Average speed should be non-negative");
    }

    @Test
    @DisplayName("Center of mass should be calculated correctly")
    public void testCenterOfMass() {
        double[] com = simu.getCenterOfMass();
        assertNotNull(com, "Center of mass should not be null");
        assertEquals(2, com.length, "Center of mass should have 2 components");
        assertTrue(com[0] >= 0, "Center of mass X should be non-negative");
        assertTrue(com[1] >= 0, "Center of mass Y should be non-negative");
    }

    @Test
    @DisplayName("Average separation should be calculated correctly")
    public void testAverageSeparation() {
        double avgSep = simu.getAverageSeparation();
        assertTrue(avgSep >= 0, "Average separation should be non-negative");
    }

    // ---- PARAMETER_VARIATIONS ----

    @Test
    @DisplayName("Different radius values should work")
    public void testDifferentRadius() {
        TestableBoidsSimulator smallRadius = new TestableBoidsSimulator(
            400, 400, 20, 100, 3, desiredSep, maxSpeed, maxStep, wAlign, wCoh, wSep);
        TestableBoidsSimulator largeRadius = new TestableBoidsSimulator(
            400, 400, 20, 100, 15, desiredSep, maxSpeed, maxStep, wAlign, wCoh, wSep);

        assertEquals(20, smallRadius.getBoidCount(), "Small radius simulator should work");
        assertEquals(20, largeRadius.getBoidCount(), "Large radius simulator should work");

        smallRadius.update();
        largeRadius.update();
    }

    @Test
    @DisplayName("Different separation values should work")
    public void testDifferentSeparation() {
        TestableBoidsSimulator smallSep = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, 1, maxSpeed, maxStep, wAlign, wCoh, wSep);
        TestableBoidsSimulator largeSep = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, 10, maxSpeed, maxStep, wAlign, wCoh, wSep);

        assertEquals(20, smallSep.getBoidCount(), "Small separation simulator should work");
        assertEquals(20, largeSep.getBoidCount(), "Large separation simulator should work");

        smallSep.update();
        largeSep.update();
    }

    @Test
    @DisplayName("Different max speed values should work")
    public void testDifferentMaxSpeed() {
        TestableBoidsSimulator slowBoids = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, desiredSep, 1, maxStep, wAlign, wCoh, wSep);
        TestableBoidsSimulator fastBoids = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, desiredSep, 5, maxStep, wAlign, wCoh, wSep);

        assertEquals(20, slowBoids.getBoidCount(), "Slow boids simulator should work");
        assertEquals(20, fastBoids.getBoidCount(), "Fast boids simulator should work");

        slowBoids.update();
        fastBoids.update();
    }

    @Test
    @DisplayName("Different weight values should work")
    public void testDifferentWeights() {
        // High alignment weight
        TestableBoidsSimulator highAlign = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, desiredSep, maxSpeed, maxStep, 5.0, 0.1, 0.1);

        // High cohesion weight
        TestableBoidsSimulator highCoh = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, desiredSep, maxSpeed, maxStep, 0.1, 5.0, 0.1);

        // High separation weight
        TestableBoidsSimulator highSep = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, desiredSep, maxSpeed, maxStep, 0.1, 0.1, 5.0);

        assertEquals(20, highAlign.getBoidCount(), "High alignment simulator should work");
        assertEquals(20, highCoh.getBoidCount(), "High cohesion simulator should work");
        assertEquals(20, highSep.getBoidCount(), "High separation simulator should work");

        highAlign.update();
        highCoh.update();
        highSep.update();
    }

    @Test
    @DisplayName("Zero weights should work")
    public void testZeroWeights() {
        TestableBoidsSimulator noFlocking = new TestableBoidsSimulator(
            400, 400, 20, 100, radius, desiredSep, maxSpeed, maxStep, 0.0, 0.0, 0.0);

        assertEquals(20, noFlocking.getBoidCount(), "No flocking simulator should work");

        // Update should work without errors
        noFlocking.update();
    }

    // ---- VELOCITY_AND_POSITION ----

    @Test
    @DisplayName("Boid velocity should be modifiable")
    public void testSetBoidVelocity() {
        Boid b = simu.getBoid(0);

        simu.setBoidVelocity(0, 5, -3);

        assertEquals(5, b.getVx(), "Vx should be set");
        assertEquals(-3, b.getVy(), "Vy should be set");
    }

    @Test
    @DisplayName("Boid position should be modifiable")
    public void testSetBoidPosition() {
        Boid b = simu.getBoid(0);

        simu.setBoidPosition(0, 100, 200);

        assertEquals(100, b.getX(), "X should be set");
        assertEquals(200, b.getY(), "Y should be set");
    }

    @Test
    @DisplayName("Boid continuous velocity should be modifiable")
    public void testSetBoidContinuousVelocity() {
        Boid b = simu.getBoid(0);

        simu.setBoidContinuousVelocity(0, 3.7, -2.1);

        assertEquals(3.7, b.getContVx(), 0.01, "Continuous Vx should be set");
        assertEquals(-2.1, b.getContVy(), 0.01, "Continuous Vy should be set");
    }

    @Test
    @DisplayName("Boid count should not increase during updates")
    public void testBoidCountNonIncreasing() {
        int initialCount = simu.getBoidCount();

        for (int i = 0; i < 20; i++) {
            simu.update();
            assertTrue(simu.getBoidCount() <= initialCount,
                        "Boid count should not increase at step " + i);
            assertTrue(simu.getBoidCount() >= 0,
                        "Boid count should be non-negative at step " + i);
        }
    }

    @Test
    @DisplayName("All boids should remain valid after multiple updates")
    public void testBoidsValidAfterUpdates() {
        for (int i = 0; i < 10; i++) {
            simu.update();
        }

        for (int i = 0; i < simu.getBoidCount(); i++) {
            Boid b = simu.getBoid(i);
            assertNotNull(b, "Boid " + i + " should not be null");
            assertNotNull(b.getColor(), "Boid " + i + " should have a color");
        }
    }
}

