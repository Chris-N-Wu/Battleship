package cs3500.pa03.model;

/**
 * Represents a Ship
 */
public interface Ship {
  /**
   * Takes a coordinate and sees if this ship is placed on those coordinates, if so, adds the
   * coordinate to this ship's coordinatesHit set.
   *
   * @param coord Coordinate where a user shot
   * @return True if an element is in the set, now removed. False if the element is not in the set.
   */
  boolean shootShip(Coord coord);

  /**
   * Tells a user if this ship has been sunk
   *
   * @return True if this ship no longer has any coordinates on the map not yet hit.
   */
  boolean isSunk();

  /**
   * Adds a coordinate to the set of coordinates for this object
   * Throws an error if method is invoked size of placementCoordinates exceeds the size of this
   * ship.
   *
   * @param coord A Coordinate where this ship is placed
   */
  void addCoordinate(Coord coord);

}
