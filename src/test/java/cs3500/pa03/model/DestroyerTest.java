package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Destroyer class
 */
class DestroyerTest {
  private Destroyer destroyer;
  private Coord coord1;
  private Coord coord2;
  private Coord coord3;
  private Coord coord4;
  private Coord coord5;

  /**
   * Sets up the prior conditions necessary for each test.
   */
  @BeforeEach
  void setUp() {
    destroyer = new Destroyer();
    coord1 = new Coord(401, 401);
    coord2 = new Coord(402, 402);
    coord3 = new Coord(403, 403);
    coord4 = new Coord(404, 404);
    coord5 = new Coord(405, 405);

  }

  /**
   * Tests that exceptions are thrown inside addCoordinate()
   * Functionality is more extensively tested through side effects in below tests.
   */
  @Test
  void addCoordinate() {
    // Testing with repeated coordinates
    destroyer.addCoordinate(coord1);
    IllegalStateException exception1 =
        assertThrows(IllegalStateException.class, () -> destroyer.addCoordinate(coord1));
    assertEquals("Coordinate Already Exists", exception1.getMessage());

    // Testing with a new coord object, but same values
    Coord coord1b = new Coord(401, 401);
    IllegalStateException exception1b =
        assertThrows(IllegalStateException.class, () -> destroyer.addCoordinate(coord1b));
    assertEquals("Coordinate Already Exists", exception1b.getMessage());

    // Adding in the coordinates to fill up this ship
    destroyer.addCoordinate(coord2);
    destroyer.addCoordinate(coord3);
    destroyer.addCoordinate(coord4);

    // Testing when a ship is filled up
    IllegalStateException exception2 =
        assertThrows(IllegalStateException.class, () -> destroyer.addCoordinate(coord5));
    assertEquals("A Carrier Can Only Fill 4 Slots", exception2.getMessage());
  }

  /**
   * Testing the shootShip method
   */
  @Test
  void shootShip() {
    // Adding coordinates to the ship
    destroyer.addCoordinate(coord1);
    destroyer.addCoordinate(coord2);
    destroyer.addCoordinate(coord3);
    destroyer.addCoordinate(coord4);

    // Testing when a ship contains and does not contain a coordinate
    assertTrue(destroyer.shootShip(coord1));
    assertFalse(destroyer.shootShip(coord5));
  }

  /**
   * Tests the boolean method isSunk
   */
  @Test
  void isSunk() {
    // Adding coordinates to the ship
    destroyer.addCoordinate(coord1);
    destroyer.addCoordinate(coord2);

    // Testing when a ship still contains one coordinate and when it contains 0
    destroyer.shootShip(coord1);
    assertFalse(destroyer.isSunk());
    destroyer.shootShip(coord2);
    assertTrue(destroyer.isSunk());
  }
}