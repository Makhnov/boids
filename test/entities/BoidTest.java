package entities;
import entities.boid.Boid;
import entities.boid.Fish;
import entities.boid.Shark;
import entities.boid.Octopus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.lang.reflect.Method;

/**
 * Unit tests for the Boid class.
 * Tests Boid-specific functionality and inherited Particle behavior.
 */
@DisplayName("Boid Tests")
class BoidTest {

    private Boid fish;
    private Boid shark;

    @BeforeEach
    void setUp() {
        // Create a boid at position (10, 20) with velocity (3, 4) and red color
        fish = new Fish(10, 20, 3, 4, Color.RED);
        shark = new Shark(20, 20, 3, 4, Color.RED);
    }

    // CONSTRUCTOR

    @Test
    @DisplayName("Boid should be created with correct position")
    void testConstructorPosition() {
        assertEquals(10, fish.getX(), "X coordinate should be 10");
        assertEquals(20, fish.getY(), "Y coordinate should be 20");
        assertEquals(20, shark.getX(), "X coordinate should be 20");
        assertEquals(20, shark.getY(), "Y coordinate should be 20");
    }

    @Test
    @DisplayName("Boid should be created with correct velocity")
    void testConstructorVelocity() {
        Boid testBoid = new Fish(0, 0, 7, -3, Color.GREEN);
        assertEquals(7, testBoid.getVx(), "Vx should be 7");
        assertEquals(-3, testBoid.getVy(), "Vy should be -3");
    }

    @Test
    @DisplayName("Boid should be created with correct color")
    void testConstructorColor() {
        Color customColor = new Color(100, 150, 200);
        Boid testBoid = new Fish(0, 0, 0, 0, customColor);
        assertEquals(customColor, testBoid.getColor(), "Color should match");
    }

    @Test
    @DisplayName("Boid should accept zero velocity")
    void testConstructorZeroVelocity() {
        Boid testBoid = new Fish(5, 5, 0, 0, Color.YELLOW);
        assertEquals(0, testBoid.getVx(), "Vx should be 0");
        assertEquals(0, testBoid.getVy(), "Vy should be 0");
    }

    @Test
    @DisplayName("Boid should accept negative coordinates")
    void testConstructorNegativeCoordinates() {
        Boid testBoid = new Fish(-10, -20, 1, 1, Color.CYAN);
        assertEquals(-10, testBoid.getX(), "X should be -10");
        assertEquals(-20, testBoid.getY(), "Y should be -20");
    }

    // VELOCITY

    @Test
    @DisplayName("getVx should return X velocity")
    void testGetVx() {
        assertEquals(3, fish.getVx(), "Vx should be 3");
    }

    @Test
    @DisplayName("getVy should return Y velocity")
    void testGetVy() {
        assertEquals(4, fish.getVy(), "Vy should be 4");
    }

    @Test
    @DisplayName("setVx should update X velocity")
    void testSetVx() {
        fish.setVx(10);
        assertEquals(10, fish.getVx(), "Vx should be updated to 10");

        fish.setVx(-5);
        assertEquals(-5, fish.getVx(), "Vx should be updated to -5");
    }

    @Test
    @DisplayName("setVy should update Y velocity")
    void testSetVy() {
        fish.setVy(15);
        assertEquals(15, fish.getVy(), "Vy should be updated to 15");

        fish.setVy(-8);
        assertEquals(-8, fish.getVy(), "Vy should be updated to -8");
    }

    @Test
    @DisplayName("setVelocity should update both velocity components")
    void testSetVelocity() {
        fish.setVelocity(20, -10);
        assertEquals(20, fish.getVx(), "Vx should be 20");
        assertEquals(-10, fish.getVy(), "Vy should be -10");
    }

    @Test
    @DisplayName("setVelocity with zero should work")
    void testSetVelocityZero() {
        fish.setVelocity(0, 0);
        assertEquals(0, fish.getVx(), "Vx should be 0");
        assertEquals(0, fish.getVy(), "Vy should be 0");
    }

    // INIT

    @Test
    @DisplayName("init_entity should reset velocity to zero")
    void testInitEntityResetsVelocity() {
        fish.setVelocity(10, 20);
        fish.init_entity();
        assertEquals(0, fish.getVx(), "Vx should be reset to 0");
        assertEquals(0, fish.getVy(), "Vy should be reset to 0");
    }

    @Test
    @DisplayName("init_entity should not change position")
    void testInitEntityPreservesPosition() {
        int originalX = fish.getX();
        int originalY = fish.getY();

        fish.init_entity();

        assertEquals(originalX, fish.getX(), "X position should not change");
        assertEquals(originalY, fish.getY(), "Y position should not change");
    }

    @Test
    @DisplayName("init_entity should not change color")
    void testInitEntityPreservesColor() {
        Color originalColor = fish.getColor();

        fish.init_entity();

        assertEquals(originalColor, fish.getColor(), "Color should not change");
    }


    // COORDINATES

    @Test
    @DisplayName("getX should return X coordinate")
    void testGetX() {
        assertEquals(10, fish.getX(), "X should be 10");
    }

    @Test
    @DisplayName("getY should return Y coordinate")
    void testGetY() {
        assertEquals(20, fish.getY(), "Y should be 20");
    }

    @Test
    @DisplayName("setPosition should update both coordinates")
    void testSetPosition() {
        fish.setPosition(50, 60);
        assertEquals(50, fish.getX(), "X should be updated to 50");
        assertEquals(60, fish.getY(), "Y should be updated to 60");
    }

    @Test
    @DisplayName("setPosition with negative values should work")
    void testSetPositionNegative() {
        fish.setPosition(-15, -25);
        assertEquals(-15, fish.getX(), "X should be -15");
        assertEquals(-25, fish.getY(), "Y should be -25");
    }

    // COLOR

    @Test
    @DisplayName("getColor should return current color")
    void testGetColor() {
        assertEquals(Color.RED, fish.getColor(), "Color should be RED");
    }

    @Test
    @DisplayName("setColor should update color")
    void testSetColor() {
        fish.setColor(Color.BLUE);
        assertEquals(Color.BLUE, fish.getColor(), "Color should be updated to BLUE");

        Color customColor = new Color(50, 100, 150);
        fish.setColor(customColor);
        assertEquals(customColor, fish.getColor(), "Color should be updated to custom color");
    }

    // SPRITE ORIENTATION TO BE TESTED

    // BEHAVIOR

    @Test
    @DisplayName("Boid should maintain velocity after position change")
    void testVelocityIndependentOfPosition() {
        int originalVx = fish.getVx();
        int originalVy = fish.getVy();

        fish.setPosition(100, 200);

        assertEquals(originalVx, fish.getVx(), "Vx should not change when position changes");
        assertEquals(originalVy, fish.getVy(), "Vy should not change when position changes");
    }

    @Test
    @DisplayName("Boid should maintain position after velocity change")
    void testPositionIndependentOfVelocity() {
        int originalX = fish.getX();
        int originalY = fish.getY();

        fish.setVelocity(50, 60);

        assertEquals(originalX, fish.getX(), "X should not change when velocity changes");
        assertEquals(originalY, fish.getY(), "Y should not change when velocity changes");
    }

    @Test
    @DisplayName("Multiple velocity updates should work correctly")
    void testMultipleVelocityUpdates() {
        fish.setVelocity(1, 2);
        assertEquals(1, fish.getVx());
        assertEquals(2, fish.getVy());

        fish.setVelocity(3, 4);
        assertEquals(3, fish.getVx());
        assertEquals(4, fish.getVy());

        fish.setVx(5);
        assertEquals(5, fish.getVx());
        assertEquals(4, fish.getVy(), "Vy should remain unchanged");

        fish.setVy(6);
        assertEquals(5, fish.getVx(), "Vx should remain unchanged");
        assertEquals(6, fish.getVy());
    }

    // EDGE_CASES

    @Test
    @DisplayName("Boid at origin with zero velocity should work")
    void testBoidAtOriginWithZeroVelocity() {
        Boid originBoid = new Fish(0, 0, 0, 0, Color.WHITE);
        assertEquals(0, originBoid.getX());
        assertEquals(0, originBoid.getY());
        assertEquals(0, originBoid.getVx());
        assertEquals(0, originBoid.getVy());
    }

    @Test
    @DisplayName("Boid with large velocity values should work")
    void testBoidWithLargeVelocity() {
        Boid fastBoid = new Fish(0, 0, 1000, -1000, Color.MAGENTA);
        assertEquals(1000, fastBoid.getVx());
        assertEquals(-1000, fastBoid.getVy());
    }

    @Test
    @DisplayName("Boid with large position values should work")
    void testBoidWithLargePosition() {
        Boid farBoid = new Fish(10000, -10000, 1, 1, Color.ORANGE);
        assertEquals(10000, farBoid.getX());
        assertEquals(-10000, farBoid.getY());
    }

    @Test
    @DisplayName("Multiple boids should be independent")
    void testMultipleBoidsIndependent() {
        Boid boid1 = new Fish(1, 2, 3, 4, Color.RED);
        Boid boid2 = new Fish(5, 6, 7, 8, Color.BLUE);

        assertEquals(1, boid1.getX());
        assertEquals(5, boid2.getX());

        boid1.setPosition(10, 20);
        assertEquals(10, boid1.getX());
        assertEquals(5, boid2.getX(), "Boid2 position should not change");

        boid1.setVelocity(100, 200);
        assertEquals(100, boid1.getVx());
        assertEquals(7, boid2.getVx(), "Boid2 velocity should not change");
    }

    @Test
    @DisplayName("Boid should handle velocity reversal")
    void testVelocityReversal() {
        fish.setVelocity(10, 20);
        assertEquals(10, fish.getVx());
        assertEquals(20, fish.getVy());

        fish.setVelocity(-10, -20);
        assertEquals(-10, fish.getVx());
        assertEquals(-20, fish.getVy());
    }

    @Test
    @DisplayName("Boid color should be independent of position and velocity")
    void testColorIndependence() {
        Color originalColor = fish.getColor();

        fish.setPosition(999, 888);
        assertEquals(originalColor, fish.getColor(), "Color should not change with position");

        fish.setVelocity(777, 666);
        assertEquals(originalColor, fish.getColor(), "Color should not change with velocity");
    }

    // INHERITANCE

    @Test
    @DisplayName("Boid should be instance of Particle")
    void testBoidIsParticle() {
        assertTrue(fish instanceof Particle, "Boid should be instance of Particle");
    }

    @Test
    @DisplayName("Boid should be instance of Entity")
    void testBoidIsEntity() {
        assertTrue(fish instanceof Entity, "Boid should be instance of Entity");
    }

    @Test
    @DisplayName("Boid should inherit Particle behavior")
    void testBoidInheritsParticleBehavior() {
        // Test that Boid has all Particle methods
        assertDoesNotThrow(() -> fish.getVx(), "Should have getVx from Particle");
        assertDoesNotThrow(() -> fish.getVy(), "Should have getVy from Particle");
        assertDoesNotThrow(() -> fish.setVx(1), "Should have setVx from Particle");
        assertDoesNotThrow(() -> fish.setVy(1), "Should have setVy from Particle");
        assertDoesNotThrow(() -> fish.setVelocity(1, 1), "Should have setVelocity from Particle");
        assertDoesNotThrow(() -> fish.setAcceleration(1, 1), "Should have setAcceleration from Particle");
        assertDoesNotThrow(() -> fish.init_entity(), "Should have init_entity from Particle");
    }

    @Test
    @DisplayName("Boid should inherit Entity behavior")
    void testBoidInheritsEntityBehavior() {
        // Test that Boid has all Entity methods
        assertDoesNotThrow(() -> fish.getX(), "Should have getX from Entity");
        assertDoesNotThrow(() -> fish.getY(), "Should have getY from Entity");
        assertDoesNotThrow(() -> fish.setPosition(1, 1), "Should have setPosition from Entity");
        assertDoesNotThrow(() -> fish.getColor(), "Should have getColor from Entity");
        assertDoesNotThrow(() -> fish.setColor(Color.BLACK), "Should have setColor from Entity");
    }


    // SHARK-SPECIFIC TESTS

    @Test
    @DisplayName("Shark should be created with correct position")
    void testSharkConstructorPosition() {
        Shark testShark = new Shark(50, 60, 5, 6, Color.BLACK);
        assertEquals(50, testShark.getX(), "Shark X coordinate should be 50");
        assertEquals(60, testShark.getY(), "Shark Y coordinate should be 60");
    }

    @Test
    @DisplayName("Shark should be created with correct velocity")
    void testSharkConstructorVelocity() {
        Shark testShark = new Shark(0, 0, 8, -4, Color.BLACK);
        assertEquals(8, testShark.getVx(), "Shark Vx should be 8");
        assertEquals(-4, testShark.getVy(), "Shark Vy should be -4");
    }

    @Test
    @DisplayName("Shark should be created with correct color")
    void testSharkConstructorColor() {
        Color customColor = new Color(50, 50, 50);
        Shark testShark = new Shark(0, 0, 0, 0, customColor);
        assertEquals(customColor, testShark.getColor(), "Shark color should match");
    }

    @Test
    @DisplayName("Shark should accept zero velocity")
    void testSharkZeroVelocity() {
        Shark testShark = new Shark(5, 10, 0, 0, Color.BLACK);
        assertEquals(0, testShark.getVx(), "Shark Vx should be 0");
        assertEquals(0, testShark.getVy(), "Shark Vy should be 0");
    }

    @Test
    @DisplayName("Shark should accept negative coordinates")
    void testSharkNegativeCoordinates() {
        Shark testShark = new Shark(-10, -20, 1, 1, Color.BLACK);
        assertEquals(-10, testShark.getX(), "Shark X should be -10");
        assertEquals(-20, testShark.getY(), "Shark Y should be -20");
    }

    @Test
    @DisplayName("Shark velocity should be modifiable")
    void testSharkVelocityModification() {
        shark.setVelocity(15, 25);
        assertEquals(15, shark.getVx(), "Shark Vx should be updated to 15");
        assertEquals(25, shark.getVy(), "Shark Vy should be updated to 25");
    }

    @Test
    @DisplayName("Shark position should be modifiable")
    void testSharkPositionModification() {
        shark.setPosition(100, 200);
        assertEquals(100, shark.getX(), "Shark X should be updated to 100");
        assertEquals(200, shark.getY(), "Shark Y should be updated to 200");
    }

    @Test
    @DisplayName("Shark color should be modifiable")
    void testSharkColorModification() {
        Color newColor = new Color(255, 0, 0);
        shark.setColor(newColor);
        assertEquals(newColor, shark.getColor(), "Shark color should be updated");
    }

    @Test
    @DisplayName("Shark init_entity should reset velocity to zero")
    void testSharkInitEntity() {
        shark.setVelocity(10, 20);
        shark.init_entity();
        assertEquals(0, shark.getVx(), "Shark Vx should be reset to 0");
        assertEquals(0, shark.getVy(), "Shark Vy should be reset to 0");
    }

    @Test
    @DisplayName("Multiple sharks should be independent")
    void testMultipleSharksIndependent() {
        Shark shark1 = new Shark(1, 2, 3, 4, Color.BLACK);
        Shark shark2 = new Shark(5, 6, 7, 8, Color.DARK_GRAY);

        assertEquals(1, shark1.getX());
        assertEquals(5, shark2.getX());

        shark1.setPosition(10, 20);
        assertEquals(10, shark1.getX());
        assertEquals(5, shark2.getX(), "Shark2 position should not change");

        shark1.setVelocity(100, 200);
        assertEquals(100, shark1.getVx());
        assertEquals(7, shark2.getVx(), "Shark2 velocity should not change");
    }

    @Test
    @DisplayName("Fish and Shark should be independent")
    void testFishAndSharkIndependent() {
        fish.setPosition(50, 60);
        shark.setPosition(70, 80);

        assertEquals(50, fish.getX());
        assertEquals(70, shark.getX());

        fish.setVelocity(10, 20);
        shark.setVelocity(30, 40);

        assertEquals(10, fish.getVx());
        assertEquals(30, shark.getVx());
    }

    // INDIVIDUAL PARAMETERS TESTS

    @Test
    @DisplayName("Fish should have default parameters")
    void testFishDefaultParameters() {
        Fish defaultFish = new Fish(0, 0, 0, 0, Color.BLUE);
        assertEquals(1.0, defaultFish.getSpeedScale(), 0.001, "Fish default speedScale should be 1.0");
        assertEquals(1.0, defaultFish.getForceScale(), 0.001, "Fish default forceScale should be 1.0");
        assertEquals(1.0, defaultFish.getFearWeight(), 0.001, "Fish default fearWeight should be 1.0");
    }

    @Test
    @DisplayName("Shark should have default parameters")
    void testSharkDefaultParameters() {
        Shark defaultShark = new Shark(0, 0, 0, 0, Color.BLACK);
        assertEquals(0.60, defaultShark.getSpeedScale(), 0.001, "Shark default speedScale should be 0.60");
        assertEquals(0.75, defaultShark.getForceScale(), 0.001, "Shark default forceScale should be 0.75");
        assertEquals(20.0, defaultShark.getFearWeight(), 0.001, "Shark default fearWeight should be 20.0");
    }

    @Test
    @DisplayName("Fish with custom parameters should work")
    void testFishCustomParameters() {
        Fish customFish = new Fish(0, 0, 0, 0, Color.GREEN, 1.5, 2.0, 3.5);
        assertEquals(1.5, customFish.getSpeedScale(), 0.001, "Custom speedScale should be 1.5");
        assertEquals(2.0, customFish.getForceScale(), 0.001, "Custom forceScale should be 2.0");
        assertEquals(3.5, customFish.getFearWeight(), 0.001, "Custom fearWeight should be 3.5");
    }

    @Test
    @DisplayName("Shark with custom parameters should work")
    void testSharkCustomParameters() {
        Shark customShark = new Shark(0, 0, 0, 0, Color.GRAY, 0.8, 1.2, 25.0);
        assertEquals(0.8, customShark.getSpeedScale(), 0.001, "Custom speedScale should be 0.8");
        assertEquals(1.2, customShark.getForceScale(), 0.001, "Custom forceScale should be 1.2");
        assertEquals(25.0, customShark.getFearWeight(), 0.001, "Custom fearWeight should be 25.0");
    }

    // PREDATOR SCORE TESTS

    @Test
    @DisplayName("Fish predator score should be lower than Shark")
    void testPredatorScoreOrdering() {
        double fishScore = fish.predatorScore();
        double sharkScore = shark.predatorScore();

        assertTrue(fishScore < sharkScore, "Shark predator score should exceed Fish");
        assertTrue(fishScore >= 0.0 && fishScore <= 1.0, "Fish score must be in [0, 1]");
        assertTrue(sharkScore >= 0.0 && sharkScore <= 1.0, "Shark score must be in [0, 1]");
    }

    @Test
    @DisplayName("Predator score should be within valid range")
    void testPredatorScoreRange() {
        double score = fish.predatorScore();
        assertTrue(score >= 0.0 && score <= 1.0, "Predator score must be in [0, 1]");
    }

    // SHARK HUNGER TESTS

    @Test
    @DisplayName("Shark hunger should start at zero")
    void testSharkInitialHunger() {
        Shark newShark = new Shark(0, 0, 0, 0, Color.BLACK);
        assertEquals(0.0, newShark.hungerLevel(), 0.001, "Initial hunger should be 0");
    }

    @Test
    @DisplayName("Shark hunger should increase over time")
    void testSharkHungerIncrease() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        double initialHunger = testShark.hungerLevel();

        for (int i = 0; i < 50; i++) {
            testShark.onTick();
        }

        double laterHunger = testShark.hungerLevel();
        assertTrue(laterHunger > initialHunger, "Hunger should increase over time");
    }

    @Test
    @DisplayName("Shark hunger should be capped at maximum")
    void testSharkHungerCap() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);

        // Tick many times to exceed max hunger
        for (int i = 0; i < 200; i++) {
            testShark.onTick();
        }

        double hunger = testShark.hungerLevel();
        assertTrue(hunger <= 3.0, "Hunger should be capped at maximum (3.0)");
    }

    @Test
    @DisplayName("Shark predator score should increase with hunger")
    void testSharkPredatorScoreIncreasesWithHunger() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        double initialScore = testShark.predatorScore();

        for (int i = 0; i < 100; i++) {
            testShark.onTick();
        }

        double hungryScore = testShark.predatorScore();
        assertTrue(hungryScore > initialScore, "Predator score should increase as hunger grows");
    }

    @Test
    @DisplayName("Fish hunger should always be zero")
    void testFishHungerAlwaysZero() {
        assertEquals(0.0, fish.hungerLevel(), 0.001, "Fish hunger should be 0");

        for (int i = 0; i < 100; i++) {
            fish.onTick();
        }

        assertEquals(0.0, fish.hungerLevel(), 0.001, "Fish hunger should remain 0 after ticks");
    }

    // CONTINUOUS VELOCITY TESTS

    @Test
    @DisplayName("Fish continuous velocity should be modifiable")
    void testFishContinuousVelocity() {
        fish.setContinuousVelocity(1.5, 2.5);
        assertEquals(1.5, fish.getContVx(), 0.001, "Fish continuous Vx should be 1.5");
        assertEquals(2.5, fish.getContVy(), 0.001, "Fish continuous Vy should be 2.5");
    }

    @Test
    @DisplayName("Shark continuous velocity should be modifiable")
    void testSharkContinuousVelocity() {
        shark.setContinuousVelocity(3.7, 4.2);
        assertEquals(3.7, shark.getContVx(), 0.001, "Shark continuous Vx should be 3.7");
        assertEquals(4.2, shark.getContVy(), 0.001, "Shark continuous Vy should be 4.2");
    }

    @Test
    @DisplayName("Continuous velocity should default to zero")
    void testContinuousVelocityDefault() {
        Boid newFish = new Fish(0, 0, 5, 5, Color.BLUE);
        assertEquals(0.0, newFish.getContVx(), 0.001, "Default continuous Vx should be 0");
        assertEquals(0.0, newFish.getContVy(), 0.001, "Default continuous Vy should be 0");
    }

    @Test
    @DisplayName("Continuous velocity should handle negative values")
    void testContinuousVelocityNegative() {
        fish.setContinuousVelocity(-2.5, -3.5);
        assertEquals(-2.5, fish.getContVx(), 0.001, "Continuous Vx should be -2.5");
        assertEquals(-3.5, fish.getContVy(), 0.001, "Continuous Vy should be -3.5");
    }

    @Test
    @DisplayName("Continuous velocity should handle zero")
    void testContinuousVelocityZero() {
        fish.setContinuousVelocity(5.0, 5.0);
        fish.setContinuousVelocity(0.0, 0.0);
        assertEquals(0.0, fish.getContVx(), 0.001, "Continuous Vx should be 0");
        assertEquals(0.0, fish.getContVy(), 0.001, "Continuous Vy should be 0");
    }

    // SHARK EATING MECHANICS TESTS

    @Test
    @DisplayName("Shark should not be frozen initially")
    void testSharkNotFrozenInitially() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        assertFalse(testShark.isFrozen(), "Shark should not be frozen initially");
    }

    @Test
    @DisplayName("Shark should be frozen when eating")
    void testSharkFrozenWhenEating() {
        Shark testShark = new Shark(0, 0, 5, 5, Color.BLACK);
        testShark.startEat();
        assertTrue(testShark.isFrozen(), "Shark should be frozen when eating");
    }

    @Test
    @DisplayName("Shark startEat should reset hunger")
    void testSharkStartEatResetsHunger() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);

        // Build up hunger
        for (int i = 0; i < 50; i++) {
            testShark.onTick();
        }

        assertTrue(testShark.hungerLevel() > 0.0, "Hunger should be > 0 before eating");

        testShark.startEat();
        assertEquals(0.0, testShark.hungerLevel(), 0.001, "Hunger should be reset to 0 after eating");
    }

    @Test
    @DisplayName("Shark startEat should reset velocity")
    void testSharkStartEatResetsVelocity() {
        Shark testShark = new Shark(0, 0, 5, 10, Color.BLACK);
        testShark.setContinuousVelocity(3.0, 4.0);

        testShark.startEat();

        assertEquals(0, testShark.getVx(), "Vx should be 0 after startEat");
        assertEquals(0, testShark.getVy(), "Vy should be 0 after startEat");
        assertEquals(0.0, testShark.getContVx(), 0.001, "Continuous Vx should be 0 after startEat");
        assertEquals(0.0, testShark.getContVy(), 0.001, "Continuous Vy should be 0 after startEat");
    }

    @Test
    @DisplayName("Shark startEat should change color to red")
    void testSharkStartEatChangesColor() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        testShark.startEat();

        Color eatColor = testShark.getColor();
        // EAT_COLOR is new Color(220, 30, 30) - reddish
        assertEquals(220, eatColor.getRed(), "Red component should be 220 when eating");
        assertEquals(30, eatColor.getGreen(), "Green component should be 30 when eating");
        assertEquals(30, eatColor.getBlue(), "Blue component should be 30 when eating");
    }

    @Test
    @DisplayName("Shark endEat should unfreeze shark")
    void testSharkEndEatUnfreezes() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        testShark.startEat();
        assertTrue(testShark.isFrozen(), "Should be frozen after startEat");

        testShark.endEat();
        assertFalse(testShark.isFrozen(), "Should not be frozen after endEat");
    }

    @Test
    @DisplayName("Shark endEat should restore base color")
    void testSharkEndEatRestoresColor() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        testShark.startEat();
        testShark.endEat();

        Color finalColor = testShark.getColor();
        assertEquals(0, finalColor.getRed(), "Should return to black (0,0,0)");
        assertEquals(0, finalColor.getGreen(), "Should return to black (0,0,0)");
        assertEquals(0, finalColor.getBlue(), "Should return to black (0,0,0)");
    }

    @Test
    @DisplayName("Shark hunger should not increase while eating")
    void testSharkHungerDoesNotIncreaseWhileEating() {
        Shark testShark = new Shark(0, 0, 0, 0, Color.BLACK);
        testShark.startEat();

        double hungerWhileEating = testShark.hungerLevel();

        for (int i = 0; i < 50; i++) {
            testShark.onTick();
        }

        assertEquals(hungerWhileEating, testShark.hungerLevel(), 0.001,
                    "Hunger should not increase while eating");
    }

    @Test
    @DisplayName("Fish should never be frozen")
    void testFishNeverFrozen() {
        assertFalse(fish.isFrozen(), "Fish should never be frozen");
        fish.startEat();
        assertFalse(fish.isFrozen(), "Fish should still not be frozen after startEat");
    }

    // BITE DESIRE TESTS

    @Test
    @DisplayName("Shark bite desire should be positive for weaker fish")
    void testSharkBiteDesireForWeakFish() {
        Fish weakFish = new Fish(0, 0, 0, 0, Color.ORANGE, 1.0, 1.0, 0.5);
        double desire = shark.biteDesire(weakFish);
        assertTrue(desire > 0.0, "Shark should want to bite weaker fish");
    }

    @Test
    @DisplayName("Shark bite desire should be zero for scarier shark")
    void testSharkBiteDesireForScarierShark() {
        Shark biggerShark = new Shark(0, 0, 0, 0, Color.DARK_GRAY, 0.5, 0.8, 50.0);
        double desire = shark.biteDesire(biggerShark);
        assertEquals(0.0, desire, 0.001, "Shark should not bite a scarier shark");
    }

    @Test
    @DisplayName("Shark bite desire should increase with hunger")
    void testSharkBiteDesireIncreasesWithHunger() {
        Fish targetFish = new Fish(0, 0, 0, 0, Color.CYAN, 1.0, 1.0, 1.0);

        double initialDesire = shark.biteDesire(targetFish);

        // Increase hunger
        for (int i = 0; i < 100; i++) {
            shark.onTick();
        }

        double hungryDesire = shark.biteDesire(targetFish);
        assertTrue(hungryDesire > initialDesire, "Bite desire should increase with hunger");
    }

    @Test
    @DisplayName("Fish bite desire should always be zero")
    void testFishBiteDesireAlwaysZero() {
        Shark targetShark = new Shark(0, 0, 0, 0, Color.BLACK);
        Fish otherFish = new Fish(0, 0, 0, 0, Color.BLUE);

        assertEquals(0.0, fish.biteDesire(targetShark), 0.001, "Fish should not bite sharks");
        assertEquals(0.0, fish.biteDesire(otherFish), 0.001, "Fish should not bite other fish");
    }

    // STEP QUANTIZATION TESTS

    @Test
    @DisplayName("stepX should accumulate fractional movement")
    void testStepXAccumulation() {
        Fish testFish = new Fish(0, 0, 0, 0, Color.BLUE);

        // Add 0.4 five times = 2.0 total
        int total = 0;
        for (int i = 0; i < 5; i++) {
            total += testFish.stepX(0.4);
        }

        assertEquals(2, total, "Five steps of 0.4 should total 2");
    }

    @Test
    @DisplayName("stepY should accumulate fractional movement")
    void testStepYAccumulation() {
        Fish testFish = new Fish(0, 0, 0, 0, Color.BLUE);

        // Add 0.3 ten times = 3.0 total
        int total = 0;
        for (int i = 0; i < 10; i++) {
            total += testFish.stepY(0.3);
        }

        assertEquals(3, total, "Ten steps of 0.3 should total 3");
    }

//    @Test
//    @DisplayName("stepX should handle negative values")
//    void testStepXNegative() {
//        Fish testFish = new Fish(0, 0, 0, 0, Color.BLUE);
//
//        int total = 0;
//        for (int i = 0; i < 5; i++) {
//            total += testFish.stepX(-0.4);
//        }
//
//        assertEquals(-3, total, "With floor rounding, five steps of -0.4 total -3");
//    }

//    @Test
//    @DisplayName("stepY should handle negative values")
//    void testStepYNegative() {
//        Fish testFish = new Fish(0, 0, 0, 0, Color.BLUE);
//
//        int total = 0;
//        for (int i = 0; i < 10; i++) {
//            total += testFish.stepY(-0.3);
//        }
//
//        assertEquals(-4, total, "With floor rounding, ten steps of -0.3 total -4");
//    }

    @Test
    @DisplayName("stepX should round to nearest integer")
    void testStepXRounding() {
        Fish testFish = new Fish(0, 0, 0, 0, Color.BLUE);

        // 0.6 should round to 1 on first call
        int step1 = testFish.stepX(0.6);
        assertEquals(1, step1, "0.6 should round to 1");

        // Accumulator now has -0.4, so 0.6 gives 0.2, rounds to 0
        int step2 = testFish.stepX(0.6);
        assertEquals(0, step2, "Second 0.6 should round to 0 (accumulator effect)");
    }

    // SOCIAL BEHAVIOR TESTS

    @Test
    @DisplayName("Fish alignment with other fish should be positive")
    void testFishAlignmentWithFish() {
        Fish otherFish = new Fish(10, 10, 1, 1, Color.CYAN);
        double alignment = fish.alignment(otherFish);
        assertTrue(alignment > 0.0, "Fish should align with other fish");
    }

    @Test
    @DisplayName("Fish alignment with shark should be reduced")
    void testFishAlignmentWithShark() {
        double alignmentWithFish = fish.alignment(new Fish(0, 0, 0, 0, Color.CYAN));
        double alignmentWithShark = fish.alignment(shark);

        assertTrue(alignmentWithShark < alignmentWithFish,
                  "Fish alignment with shark should be less than with other fish");
    }

    @Test
    @DisplayName("Shark cohesion with fish should be positive")
    void testSharkCohesionWithFish() {
        Fish prey = new Fish(10, 10, 1, 1, Color.CYAN);
        double cohesion = shark.cohesion(prey);
        assertTrue(cohesion > 0.0, "Shark should be attracted to fish");
    }

    @Test
    @DisplayName("Shark cohesion with other shark should be negative")
    void testSharkCohesionWithShark() {
        Shark otherShark = new Shark(10, 10, 1, 1, Color.DARK_GRAY);
        double cohesion = shark.cohesion(otherShark);
        assertTrue(cohesion < 0.0, "Shark should repel other sharks (anti-cohesion)");
    }

    @Test
    @DisplayName("Fish separation radius should increase for predators")
    void testFishSeparationRadiusForPredators() {
        Fish otherFish = new Fish(0, 0, 0, 0, Color.CYAN);
        double radiusForFish = fish.separationRadius(otherFish);
        double radiusForShark = fish.separationRadius(shark);

        assertTrue(radiusForShark > radiusForFish,
                  "Fish should have larger separation radius from sharks");
    }

    // PANIC BEHAVIOR TESTS

    @Test
    @DisplayName("Fish should have panic radius factor")
    void testFishPanicRadiusFactor() {
        double factor = fish.panicRadiusFactor();
        assertTrue(factor > 0.0, "Fish should have positive panic radius factor");
    }

    @Test
    @DisplayName("Fish should have panic flee gain")
    void testFishPanicFleeGain() {
        double gain = fish.panicFleeGain();
        assertTrue(gain > 0.0, "Fish should have positive panic flee gain");
    }

    @Test
    @DisplayName("Fish should have panic speed boost")
    void testFishPanicSpeedBoost() {
        double boost = fish.panicSpeedBoost();
        assertTrue(boost > 0.0, "Fish should have positive panic speed boost");
    }

    @Test
    @DisplayName("Fish panic sensitivity to shark should be positive")
    void testFishPanicSensitivityToShark() {
        double sensitivity = fish.panicSensitivity(shark);
        assertTrue(sensitivity > 0.0, "Fish should panic when sensing shark");
    }

    @Test
    @DisplayName("Fish panic sensitivity to other fish depends on threat level")
    void testFishPanicSensitivityToFish() {
        // Calm fish with no predator pressure (fearWeight = 0) should not spread panic.
        Fish calmFish = new Fish(0, 0, 0, 0, Color.CYAN, 1.0, 1.0, 0.0);
        double calmSensitivity = fish.panicSensitivity(calmFish);
        assertEquals(0.0, calmSensitivity, 0.001, "Calm fish should not panic neighbors");

        // Fish that senses predators relays the panic to neighbors (fearWeight > 0).
        Fish panickedFish = new Fish(0, 0, 0, 0, Color.CYAN, 1.0, 1.0, 1.0);
        double relayedPanic = fish.panicSensitivity(panickedFish);
        assertTrue(relayedPanic > 0.0, "Fish sensing predators should scare nearby fish");
    }

    @Test
    @DisplayName("Shark should have lower panic radius than fish")
    void testSharkPanicRadiusLowerThanFish() {
        double sharkPanic = shark.panicRadiusFactor();
        double fishPanic = fish.panicRadiusFactor();
        assertTrue(sharkPanic < fishPanic, "Shark panic radius should be less than fish");
    }

    // HUNT BEHAVIOR TESTS

    @Test
    @DisplayName("Shark should have positive hunt radius factor")
    void testSharkHuntRadiusFactor() {
        double factor = shark.huntRadiusFactor();
        assertTrue(factor > 0.0, "Shark should have positive hunt radius factor");
    }

    @Test
    @DisplayName("Shark hunt attraction to fish should be positive")
    void testSharkHuntAttractionToFish() {
        Fish prey = new Fish(0, 0, 0, 0, Color.CYAN);
        double attraction = shark.huntAttraction(prey);
        assertTrue(attraction > 0.0, "Shark should be attracted to fish for hunting");
    }

    @Test
    @DisplayName("Shark should have positive hunt global gain")
    void testSharkHuntGlobalGain() {
        double gain = shark.huntGlobalGain();
        assertTrue(gain > 0.0, "Shark should have positive hunt global gain");
    }

    @Test
    @DisplayName("Shark should have positive orbit gain")
    void testSharkOrbitGain() {
        double gain = shark.orbitGain();
        assertTrue(gain > 0.0, "Shark should have positive orbit gain");
    }

    @Test
    @DisplayName("Shark should have positive focus gain")
    void testSharkFocusGain() {
        double gain = shark.focusGain();
        assertTrue(gain > 0.0, "Shark should have positive focus gain");
    }

    @Test
    @DisplayName("Fish hunt radius factor should be zero")
    void testFishHuntRadiusFactorZero() {
        double factor = fish.huntRadiusFactor();
        assertEquals(0.0, factor, 0.001, "Fish should not hunt (radius factor = 0)");
    }

    @Test
    @DisplayName("Fish hunt global gain should be zero")
    void testFishHuntGlobalGainZero() {
        double gain = fish.huntGlobalGain();
        assertEquals(0.0, gain, 0.001, "Fish should not hunt (global gain = 0)");
    }
}
