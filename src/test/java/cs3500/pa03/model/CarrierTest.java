package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarrierTest {
  private Carrier carrier;
  private Coord coord1;
  private Coord coord2;
  private Coord coord3;
  private Coord coord4;
  private Coord coord5;
  private Coord coord6;
  private Coord coord7;

  /**
   * Sets up the prior conditions necessary for each test.
   */
  @BeforeEach
  void setUp() {
    carrier = new Carrier();
    coord1 = new Coord(601, 601);
    coord2 = new Coord(602, 602);
    coord3 = new Coord(603, 603);
    coord4 = new Coord(604, 604);
    coord5 = new Coord(605, 605);
    coord6 = new Coord(606, 606);
    coord7 = new Coord(607, 607);
  }

  /**
   * Tests that exceptions are thrown inside addCoordinate()
   * Functionality is more extensively tested through side effects in below tests.
   */
  @Test
  void addCoordinate() {
    // Testing with repeated coordinates
    carrier.addCoordinate(coord1);
    IllegalStateException exception1 =
        assertThrows(IllegalStateException.class, () -> carrier.addCoordinate(coord1));
    assertEquals("Coordinate Already Exists", exception1.getMessage());

    // Testing with a new coord object, but same values
    Coord coord1b = new Coord(601, 601);
    IllegalStateException exception1b =
        assertThrows(IllegalStateException.class, () -> carrier.addCoordinate(coord1b));
    assertEquals("Coordinate Already Exists", exception1b.getMessage());

    // Adding in the coordinates to fill up this ship
    carrier.addCoordinate(coord2);
    carrier.addCoordinate(coord3);
    carrier.addCoordinate(coord4);
    carrier.addCoordinate(coord5);
    carrier.addCoordinate(coord6);

    IllegalStateException exception2 =
        assertThrows(IllegalStateException.class, () -> carrier.addCoordinate(coord7));
    assertEquals("A Carrier Can Only Fill 6 Slots", exception2.getMessage());
  }

  /**
   * Testing the shootShip method
   */
  @Test
  void shootShip() {
    // Adding coordinates to the ship
    carrier.addCoordinate(coord1);
    carrier.addCoordinate(coord2);
    carrier.addCoordinate(coord3);
    carrier.addCoordinate(coord4);
    carrier.addCoordinate(coord5);
    carrier.addCoordinate(coord6);

    // Testing when a ship contains and does not contain a coordinate
    assertTrue(carrier.shootShip(coord1));
    assertFalse(carrier.shootShip(coord7));
  }

  /**
   * Tests the boolean method isSunk
   */
  @Test
  void isSunk() {
    // Adding coordinates to the ship
    carrier.addCoordinate(coord1);
    carrier.addCoordinate(coord2);

    // Testing when a ship still contains one coordinate and when it contains 0
    carrier.shootShip(coord1);
    assertFalse(carrier.isSunk());
    carrier.shootShip(coord2);
    assertTrue(carrier.isSunk());
  }
}