package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Submarine class
 */
class SubmarineTest {
  private Submarine submarine;
  private Coord coord1;
  private Coord coord2;
  private Coord coord3;
  private Coord coord4;

  /**
   * Sets up the prior conditions necessary for each test.
   */
  @BeforeEach
  void setUp() {
    submarine = new Submarine();
    coord1 = new Coord(301, 301);
    coord2 = new Coord(302, 302);
    coord3 = new Coord(303, 303);
    coord4 = new Coord(304, 304);
  }

  /**
   * Tests that exceptions are thrown inside addCoordinate()
   * Functionality is more extensively tested through side effects in below tests.
   */
  @Test
  void addCoordinate() {
    // Testing with repeated coordinates
    submarine.addCoordinate(coord1);
    IllegalStateException exception1 =
        assertThrows(IllegalStateException.class, () -> submarine.addCoordinate(coord1));
    assertEquals("Coordinate Already Exists", exception1.getMessage());

    // Testing with a new coord object, but same values
    Coord coord1b = new Coord(301, 301);
    IllegalStateException exception1b =
        assertThrows(IllegalStateException.class, () -> submarine.addCoordinate(coord1b));
    assertEquals("Coordinate Already Exists", exception1b.getMessage());

    // Adding in the coordinates to fill up this ship
    submarine.addCoordinate(coord2);
    submarine.addCoordinate(coord3);

    // Testing when a ship is filled up
    IllegalStateException exception2 =
        assertThrows(IllegalStateException.class, () -> submarine.addCoordinate(coord4));
    assertEquals("A Carrier Can Only Fill 3 Slots", exception2.getMessage());
  }

  /**
   * Testing the shootShip method
   */
  @Test
  void shootShip() {
    // Adding coordinates to the ship
    submarine.addCoordinate(coord1);
    submarine.addCoordinate(coord2);
    submarine.addCoordinate(coord3);

    // Testing when a ship contains and does not contain a coordinate
    assertTrue(submarine.shootShip(coord1));
    assertFalse(submarine.shootShip(coord4));
  }

  /**
   * Tests the boolean method isSunk
   */
  @Test
  void isSunk() {
    // Adding coordinates to the ship
    submarine.addCoordinate(coord1);
    submarine.addCoordinate(coord2);

    // Testing when a ship still contains one coordinate and when it contains 0
    submarine.shootShip(coord1);
    assertFalse(submarine.isSunk());
    submarine.shootShip(coord2);
    assertTrue(submarine.isSunk());
  }
}