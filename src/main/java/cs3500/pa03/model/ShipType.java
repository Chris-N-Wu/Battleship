package cs3500.pa03.model;

/**
 * Represents the possible Ships a player can have
 */
public enum ShipType {
  /**
   * Represents the Carrier option for a ship
   */
  CARRIER(6),
  /**
   * Represents the Battleship option for a ship
   */
  BATTLESHIP(5),
  /**
   * Represents the Destroyer option for a ship
   */
  DESTROYER(4),
  /**
   * Represents the Submarine option for a ship
   */
  SUBMARINE(3);

  private final int length;

  /**
   * Contract for each ShipType
   *
   * @param length The length of a ship
   */
  ShipType(int length) {
    this.length = length;
  }

  /**
   * Getter for the Ship's length
   *
   * @return The ship's length
   */
  public int getLength() {
    return length;
  }
}
