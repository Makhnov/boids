package entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

/**
 * Unit tests for the Cell class.
 * Tests state management, position, color, and lifecycle methods.
 */
@DisplayName("Cell Tests")
class CellTest {

    private Cell cell;

    @BeforeEach
    void setUp() {
        // Create a cell at position (5, 10) before each test
        cell = new Cell(5, 10);
    }

    // CONSTRUCTOR

    @Test
    @DisplayName("Cell should be created with correct position")
    void testConstructorPosition() {
        Cell testCell = new Cell(3, 7);
        assertEquals(3, testCell.getX(), "X coordinate should be 3");
        assertEquals(7, testCell.getY(), "Y coordinate should be 7");
    }

    @Test
    @DisplayName("Cell should be created with initial state 0")
    void testConstructorInitialState() {
        Cell testCell = new Cell(0, 0);
        assertEquals(0, testCell.getState(), "Initial state should be 0");
    }

    @Test
    @DisplayName("Cell should be created with default color BLUE")
    void testConstructorDefaultColor() {
        Cell testCell = new Cell(0, 0);
        assertEquals(Color.BLUE, testCell.getColor(), "Default color should be BLUE");
    }

    @Test
    @DisplayName("Cell should be dead initially")
    void testConstructorInitiallyDead() {
        Cell testCell = new Cell(0, 0);
        assertFalse(testCell.isAlive(), "Cell should be dead initially (state = 0)");
    }

    // STATE

    @Test
    @DisplayName("getState should return current state")
    void testGetState() {
        assertEquals(0, cell.getState(), "Initial state should be 0");
    }

    @Test
    @DisplayName("setState should update state correctly")
    void testSetState() {
        cell.setState(1);
        assertEquals(1, cell.getState(), "State should be updated to 1");

        cell.setState(5);
        assertEquals(5, cell.getState(), "State should be updated to 5");
    }

    @Test
    @DisplayName("setState with 0 should make cell dead")
    void testSetStateZero() {
        cell.setState(3);
        cell.setState(0);
        assertEquals(0, cell.getState(), "State should be 0");
        assertFalse(cell.isAlive(), "Cell should be dead when state is 0");
    }

    @Test
    @DisplayName("setState with negative value should throw IllegalArgumentException")
    void testSetStateNegativeThrowsException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cell.setState(-1),
            "Setting negative state should throw IllegalArgumentException"
        );
        assertEquals("Illegal state", exception.getMessage());
    }

    @Test
    @DisplayName("setState with large positive value should work")
    void testSetStateLargeValue() {
        cell.setState(100);
        assertEquals(100, cell.getState(), "State should accept large positive values");
    }

    // ISALIVE

    @Test
    @DisplayName("isAlive should return false when state is 0")
    void testIsAliveWhenDead() {
        cell.setState(0);
        assertFalse(cell.isAlive(), "Cell should not be alive when state is 0");
    }

    @Test
    @DisplayName("isAlive should return true when state is 1")
    void testIsAliveWhenStateOne() {
        cell.setState(1);
        assertTrue(cell.isAlive(), "Cell should be alive when state is 1");
    }

    @Test
    @DisplayName("isAlive should return true for any positive state")
    void testIsAliveWithPositiveStates() {
        for (int state = 1; state <= 10; state++) {
            cell.setState(state);
            assertTrue(cell.isAlive(), "Cell should be alive when state is " + state);
        }
    }

    // INIT

    @Test
    @DisplayName("init_entity should reset state to 0")
    void testInitEntityResetsState() {
        cell.setState(5);
        cell.init_entity();
        assertEquals(0, cell.getState(), "init_entity should reset state to 0");
    }

    @Test
    @DisplayName("init_entity should make cell dead")
    void testInitEntityMakesDead() {
        cell.setState(3);
        assertTrue(cell.isAlive(), "Cell should be alive before init");

        cell.init_entity();
        assertFalse(cell.isAlive(), "Cell should be dead after init_entity");
    }

    @Test
    @DisplayName("init_entity should not change position")
    void testInitEntityPreservesPosition() {
        int originalX = cell.getX();
        int originalY = cell.getY();

        cell.setState(5);
        cell.init_entity();

        assertEquals(originalX, cell.getX(), "X position should not change");
        assertEquals(originalY, cell.getY(), "Y position should not change");
    }


    @Test
    @DisplayName("init_entity should not change color")
    void testInitEntityPreservesColor() {
        Color testColor = Color.RED;
        cell.setColor(testColor);

        cell.setState(5);
        cell.init_entity();

        assertEquals(testColor, cell.getColor(), "Color should not change after init_entity");
    }

    // COORDINATES

    @Test
    @DisplayName("getX should return X coordinate")
    void testGetX() {
        assertEquals(5, cell.getX(), "getX should return 5");
    }

    @Test
    @DisplayName("getY should return Y coordinate")
    void testGetY() {
        assertEquals(10, cell.getY(), "getY should return 10");
    }

    @Test
    @DisplayName("setPosition should update both coordinates")
    void testSetPosition() {
        cell.setPosition(15, 20);
        assertEquals(15, cell.getX(), "X should be updated to 15");
        assertEquals(20, cell.getY(), "Y should be updated to 20");
    }

    @Test
    @DisplayName("setPosition with negative coordinates should work")
    void testSetPositionNegative() {
        cell.setPosition(-5, -10);
        assertEquals(-5, cell.getX(), "X should accept negative values");
        assertEquals(-10, cell.getY(), "Y should accept negative values");
    }

    // COLOR

    @Test
    @DisplayName("getColor should return current color")
    void testGetColor() {
        assertEquals(Color.BLUE, cell.getColor(), "Default color should be BLUE");
    }

    @Test
    @DisplayName("setColor should update color")
    void testSetColor() {
        cell.setColor(Color.RED);
        assertEquals(Color.RED, cell.getColor(), "Color should be updated to RED");

        cell.setColor(Color.GREEN);
        assertEquals(Color.GREEN, cell.getColor(), "Color should be updated to GREEN");
    }

    @Test
    @DisplayName("setColor with custom color should work")
    void testSetColorCustom() {
        Color customColor = new Color(123, 45, 67);
        cell.setColor(customColor);
        assertEquals(customColor, cell.getColor(), "Custom color should be set");
    }

    // TO_STRING

    @Test
    @DisplayName("toString should contain state information")
    void testToStringContainsState() {
        cell.setState(3);
        String result = cell.toString();
        assertTrue(result.contains("state : 3"), "toString should contain state value");
    }

    @Test
    @DisplayName("toString should contain coordinate information")
    void testToStringContainsCoordinates() {
        String result = cell.toString();
        assertTrue(result.contains("coordinate"), "toString should mention coordinates");
    }

    @Test
    @DisplayName("toString should contain color information")
    void testToStringContainsColor() {
        cell.setColor(Color.RED);
        String result = cell.toString();
        assertTrue(result.contains("color(RGB)"), "toString should contain color info");
        assertTrue(result.contains("255"), "toString should contain red component (255)");
    }

    // STATE TRANSITIONS

    @Test
    @DisplayName("Cell should transition from dead to alive")
    void testDeadToAliveTransition() {
        assertFalse(cell.isAlive(), "Cell should start dead");

        cell.setState(1);
        assertTrue(cell.isAlive(), "Cell should be alive after setting state to 1");
    }

    @Test
    @DisplayName("Cell should transition from alive to dead")
    void testAliveToDeadTransition() {
        cell.setState(2);
        assertTrue(cell.isAlive(), "Cell should be alive");

        cell.setState(0);
        assertFalse(cell.isAlive(), "Cell should be dead after setting state to 0");
    }

    @Test
    @DisplayName("Cell should handle multiple state changes")
    void testMultipleStateChanges() {
        cell.setState(1);
        assertEquals(1, cell.getState());

        cell.setState(2);
        assertEquals(2, cell.getState());

        cell.setState(0);
        assertEquals(0, cell.getState());

        cell.setState(5);
        assertEquals(5, cell.getState());
    }

    // EDGE_CASES

    @Test
    @DisplayName("Cell at origin (0,0) should work correctly")
    void testCellAtOrigin() {
        Cell originCell = new Cell(0, 0);
        assertEquals(0, originCell.getX());
        assertEquals(0, originCell.getY());
        assertEquals(0, originCell.getState());
    }

    @Test
    @DisplayName("Multiple cells should be independent")
    void testMultipleCellsIndependent() {
        Cell cell1 = new Cell(1, 1);
        Cell cell2 = new Cell(2, 2);

        cell1.setState(1);
        cell2.setState(2);

        assertEquals(1, cell1.getState(), "Cell1 state should be 1");
        assertEquals(2, cell2.getState(), "Cell2 state should be 2");

        cell1.setColor(Color.RED);
        cell2.setColor(Color.BLUE);

        assertEquals(Color.RED, cell1.getColor(), "Cell1 should be RED");
        assertEquals(Color.BLUE, cell2.getColor(), "Cell2 should be BLUE");
    }
}
