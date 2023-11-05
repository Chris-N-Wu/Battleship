package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The AI implementation for a Player
 */
public class ComputerControl extends AbstractPlayer {

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    // Calculating the number of shots this player can take
    this.numShotsAvailable = this.board.numberShipsLeft();

    // Simple loop through the board to shoot.
    for (int row = 0; row < rows; row += 1) {
      for (int col = 0; col < cols; col += 1) {
        // Checking that the computer still has shots available, if so, also checks that the
        // computer has not yet shot this space.
        if (this.numShotsAvailable > 0 && previouslyShot.add(new Coord(row, col))) {
          this.numShotsAvailable -= 1;
        }
      }
    }

    return new ArrayList<>(this.previouslyShot);
  }
}
