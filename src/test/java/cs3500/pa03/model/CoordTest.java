package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Coord record
 */
class CoordTest {
  Coord coord1;
  Coord coord2;
  Coord coord3;
  Coord coord4;
  Coord coord5;

  @BeforeEach
  void setUp() {
    coord1 = new Coord(1, 2);
    coord2 = new Coord(50, 420);
    coord3 = new Coord(1, 2);
    coord4 = new Coord(1, 1);
    coord5 = new Coord(2, 1);
  }

  /**
   * Tests the getter for x
   */
  @Test
  void testX() {
    assertEquals(1, coord1.x());
  }

  /**
   * Tests the getter for y
   */
  @Test
  void testY() {
    assertEquals(2, coord1.y());
  }

  /**
   * Tests the override equals method
   */
  @Test
  void testEquals() {
    // Testing with a different set of coordinates
    assertNotEquals(coord1, coord2);
    // Testing with that even if one field matches, still not equal
    assertNotEquals(coord1, coord4);
    // Testing with same coordinates
    assertEquals(coord1, coord3);
  }

  /**
   * Tests the override hashCode method
   */
  @Test
  void testHashCode() {
    // Tests that hashCodes are still not equal when fields are flipped
    assertNotEquals(coord1.hashCode(), coord5.hashCode());
    // Tests when fields are the same
    assertEquals(coord1.hashCode(), coord3.hashCode());
  }

  /**
   * Tests the override toString method
   */
  @Test
  void testToString() {
    assertEquals("Coord[x=1, y=2]", coord1.toString());
    assertEquals("Coord[x=50, y=420]", coord2.toString());
    assertEquals("Coord[x=1, y=2]", coord3.toString());
  }
}