package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * An Abstract Class for the Player interface, implementing common functionality.
 */
public abstract class AbstractPlayer implements Player {
  /**
   * A Board that contains both the Player's board and the Opponent's board
   */
  protected Board board;
  /**
   * A set of coordinates that the player has already shot
   */
  protected final HashSet<Coord> previouslyShot;
  /**
   * The number of shots that a player can make
   */
  protected int numShotsAvailable;
  /**
   * The number of rows on a board
   */
  protected int rows;
  /**
   * The number of columns on a board
   */
  protected int cols;
  /**
   * A random generator
   */
  protected Random rand;

  /**
   * Abstract constructor
   */
  AbstractPlayer() {
    this.rand = new Random();
    this.previouslyShot = new HashSet<>();
  }

  /**
   * Abstract constructor.
   *
   * @param rand Provides for randomness
   */
  AbstractPlayer(Random rand) {
    this.rand = rand;
    this.previouslyShot = new HashSet<>();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.rows = height;
    this.cols = width;
    this.board = new Board(height, width, new EnumMap<>(specifications), rand);
    this.board.generateBoard();
    this.numShotsAvailable = this.board.numberShipsLeft();

    return this.board.getAllShips();
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    // Loops through the coordinates successfully shot and updates the player's view of the
    // opponents board
    for (Coord coordinatesShot : shotsThatHitOpponentShips) {
      this.board.updateOpponentBoard(coordinatesShot, Cell.HIT);
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *        ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> damage = new ArrayList<>();

    // Loops through the coordinates shot and if hits a ship, adds that coordinate to the damage
    // ArrayList.
    for (Coord coordinateShot : opponentShotsOnBoard) {
      // shoots the other player's board. If hits a ship, adds to damage list.
      if (this.board.shootPlayer(coordinateShot)) {
        damage.add(coordinateShot);
      }
    }

    return damage;
  }

  /**
   * Sets the random object
   *
   * @param rand A random object for randomness
   */
  public void setRand(Random rand) {
    this.rand = rand;
  }

  /**
   * Determines if the end condition for a game has been met. Does not assign win, loss, or tie.
   *
   * @return True if the player has no more shots left
   */
  public boolean hasLost() {
    return this.board.numberShipsLeft() <= 0;
  }
}
