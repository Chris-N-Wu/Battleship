package cs3500.pa03.controller;

import cs3500.pa03.model.ComputerControl;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualControl;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Display;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

/**
 * Represents the primary controller for running a match of BattleSalvo.
 */
public class BattleSalvo {
  private final Display display;
  private final ManualControl player1;
  private final ComputerControl player2;
  private final Prompts prompts;

  /**
   * Constructor for BattleSalvo.
   *
   * @param player1 A Manually controlled player.
   * @param player2 A Computer controlled player.
   * @param prompts Prompts the user for input.
   * @param display Displaying messages the output stream.
   */
  public BattleSalvo(ManualControl player1, ComputerControl player2, Prompts prompts,
                     Display display) {
    this.player1 = player1;
    this.player2 = player2;
    this.prompts = prompts;
    this.display = display;
  }

  /**
   * Constructor for BattleSalvo. Used for testing
   *
   * @param player1 A Manually controlled player.
   * @param player2 A Computer controlled player.
   * @param prompts Prompts the user for input.
   * @param display Displaying messages the output stream.
   * @param rand    A random object for randomness
   */
  public BattleSalvo(ManualControl player1, ComputerControl player2, Prompts prompts,
                     Display display, Random rand) {
    this.player1 = player1;
    player1.setRand(rand);
    this.player2 = player2;
    player2.setRand(rand);
    this.prompts = prompts;
    this.display = display;
  }

  /**
   * Starts a game of BattleSalvo
   */
  public void startBattleSalvo() {
    this.display.printMessage("Welcome To BattleSalvo");

    // Gathers the user's board dimensions
    Coord boardSizes = prompts.promptBoardSize(6, 15);
    int rows = boardSizes.x();
    int cols = boardSizes.y();

    // Gathers the user's fleet
    EnumMap<ShipType, Integer> inputShips = prompts.promptFleetSelection(Math.min(rows, cols));

    // Setup each player
    player1.setup(rows, cols, inputShips);
    player2.setup(rows, cols, inputShips);

    this.battleSalvoLoop();
    this.prompts.clean();
  }

  /**
   * Conducts a loop between player one and player two
   */
  private void battleSalvoLoop() {

    do {
      // Displaying the boards (Player and Player's view of Opponent)
      display.printMessage(player1.getPlayerBoardAsString());
      display.printMessage(player1.getOpponentBoardAsString());

      // Gathering the input shots for Player1
      ArrayList<Coord> inputCoordinatesP1 = new ArrayList<>(player1.takeShots());
      ArrayList<Coord> damageReportP1 = new ArrayList<>(player2.reportDamage(inputCoordinatesP1));

      inputCoordinatesP1.removeAll(damageReportP1);
      player1.updateViewOfOpponent(inputCoordinatesP1);

      player1.successfulHits(damageReportP1);

      // Gathering the input shots for Player2
      ArrayList<Coord> inputCoordinatesP2 = new ArrayList<>(player2.takeShots());
      ArrayList<Coord> damageReportP2 = new ArrayList<>(player1.reportDamage(inputCoordinatesP2));

      player2.successfulHits(damageReportP2);

    } while (!this.endBattleSalvoLoop());
  }

  /**
   * Checks conditions for ending the BattleSalvo game.
   * This section will be updated once an implementation for endGame is made (Presumably when
   * required in PA04 or PA05), as Piazza post said endGame not required for PA03.
   *
   * @return True if the game has ended
   */
  private Boolean endBattleSalvoLoop() {
    boolean playerOneState = player1.hasLost();
    boolean playerTwoState = player2.hasLost();

    if (playerOneState && playerTwoState) {
      display.printMessage(GameResult.DRAW.toString());
    } else if (playerTwoState) {
      // endGame to be implemented in PA04/5
      player1.endGame(GameResult.WON, "Lost");
      display.printMessage("PLAYER ONE: " + GameResult.WON);
      display.printMessage("PLAYER TWO: " + GameResult.LOSS);
    } else if (playerOneState) {
      // endGame to be implemented in PA04/5
      player1.endGame(GameResult.LOSS, "Lost");
      display.printMessage("PLAYER ONE: " + GameResult.LOSS);
      display.printMessage("PLAYER TWO: " + GameResult.WON);
    } else {
      return false;
    }

    return true;
  }
}
