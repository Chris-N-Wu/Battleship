package cs3500.pa03.model;

import java.util.HashSet;

/**
 * Implements Ship for a Submarine
 */
public class Submarine implements Ship {
  private final HashSet<Coord> placementCoordinates;

  /**
   * Constructor
   */
  public Submarine() {
    this.placementCoordinates = new HashSet<>();
  }

  /**
   * Adds a coordinate to the set of coordinates for this object.
   * Throws an error if method is invoked size of placementCoordinates exceeds the size of a
   * submarine.
   *
   * @param coord A Coordinate where this ship is placed
   */
  @Override
  public void addCoordinate(Coord coord) {
    if (!this.placementCoordinates.add(coord)) {
      throw new IllegalStateException("Coordinate Already Exists");
    }

    if (this.placementCoordinates.size() > ShipType.SUBMARINE.getLength()) {
      throw new IllegalStateException(
          "A Carrier Can Only Fill " + ShipType.SUBMARINE.getLength() + " Slots");
    }
  }

  /**
   * Takes a coordinate and sees if this ship is placed on those coordinates, if so, adds the
   * coordinate to this ship's coordinatesHit set.
   *
   * @param coord Coordinate where a user shot
   * @return True if an element is in the set, now removed. False if the element is not in the set.
   */
  @Override
  public boolean shootShip(Coord coord) {
    // checking if the coordinate corresponds to a Coordinate where this ship is
    return placementCoordinates.remove(coord);
  }

  /**
   * Tells a user if this ship has been sunk
   *
   * @return True if this ship no longer has any coordinates on the map not yet hit.
   */
  @Override
  public boolean isSunk() {
    return placementCoordinates.isEmpty();
  }
}
