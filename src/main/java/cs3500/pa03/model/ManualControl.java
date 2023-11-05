package cs3500.pa03.model;

import cs3500.pa03.controller.Prompts;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the manual control implementation for a Player
 */
public class ManualControl extends AbstractPlayer {
  private final Prompts prompts;


  /**
   * Default constructor for ManualControl
   *
   * @param prompts A prompt object to prompt the user for input
   */
  public ManualControl(Prompts prompts) {
    super();
    this.prompts = prompts;
  }

  /**
   * Constructor used for testing
   *
   * @param prompts A prompt object to prompt the user for input
   * @param rand A random object for testing
   */
  public ManualControl(Prompts prompts, Random rand) {
    super(rand);
    this.prompts = prompts;
  }

  /*
  Interface Methods
   */

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

    return prompts.promptShots(this.previouslyShot, this.numShotsAvailable, new Coord(rows, cols));
  }

  /*
  Helper Methods
   */

  /**
   * Takes misses and updates what the player sees of the opponent's board.
   *
   * @param misses An ArrayList containing the coordinates of missed shots
   */
  public void updateViewOfOpponent(ArrayList<Coord> misses) {
    for (Coord coord : misses) {
      this.board.updateOpponentBoard(coord, Cell.MISS);
    }
  }

  /**
   * Returns a String representation of this player's board
   *
   * @return The player's board as a String
   */
  public String getPlayerBoardAsString() {
    return this.board.getPlayerBoard();
  }

  /**
   * Returns a String representation of this player's view of the opponent's board.
   *
   * @return The player's view of the opponent's board as a String
   */
  public String getOpponentBoardAsString() {
    return this.board.getOpponentBoard();
  }
}
