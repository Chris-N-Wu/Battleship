package cs3500.pa03.model;

import java.util.Objects;

/**
 * Represents a coordinate
 */
public record Coord(int x, int y) {
  /**
   * @param x The x location, row
   * @param y The y location, column
   */
  public Coord {
  }

  /**
   * Getter for the x position.
   *
   * @return x
   */
  @Override
  public int x() {
    return x;
  }

  /**
   * Getter for the y position.
   *
   * @return y
   */
  @Override
  public int y() {
    return y;
  }

  /**
   * Overriding equals to check if two coordinates are equal
   *
   * @param o The object being compared
   * @return True if both objects have the same coordinates
   */
  @Override
  public boolean equals(Object o) {
    // Checking if Object o is this object
    if (o == this) {
      return true;
    }

    // Checking if object is an instance of this object,
    // if not, they are not equal.
    if (!(o instanceof Coord c)) {
      return false;
    }

    // Comparing x and y values
    return (this.x == c.x() && this.y == c.y());
  }

  /**
   * Overriding hashCode because we overrode equals
   *
   * @return an int representing this object's hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  /**
   * Overrides the toString method to print this coordinate
   *
   * @return A String representation of this coordinate/
   */
  @Override
  public String toString() {
    return "Coord["
        + "x=" + x + ", "
        + "y=" + y + ']';
  }


}
