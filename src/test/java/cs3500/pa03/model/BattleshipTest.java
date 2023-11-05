package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Battleship class
 */
class BattleshipTest {
  private Battleship bs;
  private Coord coord1;
  private Coord coord2;
  private Coord coord3;
  private Coord coord4;
  private Coord coord5;
  private Coord coord6;

  /**
   * Sets up the prior conditions necessary for each test.
   */
  @BeforeEach
  void setUp() {
    bs = new Battleship();
    coord1 = new Coord(501, 501);
    coord2 = new Coord(502, 502);
    coord3 = new Coord(503, 503);
    coord4 = new Coord(504, 504);
    coord5 = new Coord(505, 505);
    coord6 = new Coord(506, 506);
  }

  /**
   * Tests that exceptions are thrown inside addCoordinate()
   * Functionality is more extensively tested through side effects in below tests.
   */
  @Test
  void addCoordinate() {
    // Testing with repeated coordinates
    bs.addCoordinate(coord1);
    IllegalStateException exception1 =
        assertThrows(IllegalStateException.class, () -> bs.addCoordinate(coord1));
    assertEquals("Coordinate Already Exists", exception1.getMessage());

    // Adding in the coordinates to fill up this ship
    bs.addCoordinate(coord2);
    bs.addCoordinate(coord3);
    bs.addCoordinate(coord4);
    bs.addCoordinate(coord5);

    IllegalStateException exception2 =
        assertThrows(IllegalStateException.class, () -> bs.addCoordinate(coord6));
    assertEquals("A Carrier Can Only Fill 5 Slots", exception2.getMessage());
  }

  /**
   * Testing the shootShip method
   */
  @Test
  void shootShip() {
    // Adding coordinates to the ship
    bs.addCoordinate(coord1);
    bs.addCoordinate(coord2);
    bs.addCoordinate(coord3);
    bs.addCoordinate(coord4);
    bs.addCoordinate(coord5);

    // Testing when a ship contains and does not contain a coordinate
    assertTrue(bs.shootShip(coord1));
    assertFalse(bs.shootShip(coord6));
  }

  /**
   * Tests the boolean method isSunk
   */
  @Test
  void isSunk() {
    // Adding coordinates to the ship
    bs.addCoordinate(coord1);
    bs.addCoordinate(coord2);

    // Testing when a ship still contains one coordinate and when it contains 0
    bs.shootShip(coord1);
    assertFalse(bs.isSunk());
    bs.shootShip(coord2);
    assertTrue(bs.isSunk());
  }
}