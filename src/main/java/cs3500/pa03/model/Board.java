package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * Represents the board for a player and the player's view of the opponent
 */
public class Board {
  private Cell[][] playerBoard;
  private Cell[][] opponentBoard;
  private final Random rand;
  private final List<ShipType> ships;
  private final ArrayList<Ship> allShips;
  private final EnumMap<ShipType, Integer> inputShips;
  private final int rows;
  private final int columns;

  /**
   * Default constructor.
   *
   * @param row        How many rows this board has.
   * @param col        How many columns this board has.
   * @param inputShips The ships to be placed onto this board.
   * @param rand       A random object for a random board.
   */
  public Board(int row, int col, EnumMap<ShipType, Integer> inputShips, Random rand) {
    this.rand = rand;
    this.rows = row;
    this.columns = col;
    this.ships = new Stack<>();
    this.allShips = new ArrayList<>();
    this.inputShips = inputShips;
  }

  /**
   * Gets the Ships placed down
   *
   * @return an ArrayList of the ships
   */
  public ArrayList<Ship> getAllShips() {
    return new ArrayList<>(allShips);
  }

  /**
   * Generates board and places ship
   */
  public void generateBoard() {
    this.playerBoard = new Cell[rows][columns];
    this.opponentBoard = new Cell[rows][columns];

    // Filling the board with Water cells
    for (Cell[] cells : this.playerBoard) {
      Arrays.fill(cells, Cell.WATER);
    }

    for (Cell[] cells : this.opponentBoard) {
      Arrays.fill(cells, Cell.WATER);
    }

    // Loops through the EnumMap and then adds them to a stack
    // Adds each element entry.getValue() # of times
    for (Map.Entry<ShipType, Integer> entry : this.inputShips.entrySet()) {
      this.ships.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
    }

    Collections.sort(this.ships);

    // Looping through the stack of ships, and then placing them on the board
    for (ShipType shipType : this.ships) {
      this.placeShip(shipType);
    }
  }

  /**
   * Returns the string representation of the opponent board.
   *
   * @return A String of the board
   */
  public String getPlayerBoard() {
    StringBuilder sb = new StringBuilder();
    for (Cell[] cells : this.playerBoard) {
      convertBoardToString(sb, cells);
    }

    return sb.toString();
  }

  /**
   * Returns the string representation of the opponent board.
   *
   * @return A String of the board
   */
  public String getOpponentBoard() {
    StringBuilder sb = new StringBuilder();
    for (Cell[] cells : this.opponentBoard) {
      convertBoardToString(sb, cells);
    }
    return sb.toString();
  }

  /**
   * Updates the location on the playerBoard with the new state
   *
   * @param coord The location of which to update.
   * @param state The new state of the location (Either WATER, SHIP, or HIT)
   */
  public void updatePlayerBoard(Coord coord, Cell state) {
    this.playerBoard[coord.x()][coord.y()] = state;
  }

  /**
   * Updates the location on the opponentBoard with the new state
   *
   * @param coord The location of which to update.
   * @param state The new state of the location (Either WATER, SHIP, or HIT)
   */
  public void updateOpponentBoard(Coord coord, Cell state) {
    this.opponentBoard[coord.x()][coord.y()] = state;
  }

  /**
   * Takes an incoming shot from the opponent and if a ship is located at the provided coordinate,
   * marks that ship as hit. Otherwise, marks the coordinate as miss.
   *
   * @param coord The coordinate which to shootOpponent.
   * @return True if the shot from the opponent hit the player's ship
   */
  public boolean shootPlayer(Coord coord) {
    // Loops through each ship on the player
    for (Ship ship : allShips) {
      // if the ship is on the coordinate provided, mark it as hit
      if (ship.shootShip(coord)) {
        this.updatePlayerBoard(coord, Cell.HIT);
        return true;
      }
    }

    return false;
  }

  /**
   * Returns the number of player ships are still remaining.
   *
   * @return The # of ships remaining.
   */
  public int numberShipsLeft() {
    int numberShips = 0;

    // Loops through each ship that the player has placed and checks if they are sunk.
    // If not, adds them to the count of how many player ships are left.
    for (Ship ship : allShips) {
      if (!ship.isSunk()) {
        numberShips += 1;
      }
    }

    return numberShips;
  }

  /**
   * Converts a board into a string representation.
   *
   * @param sb    A StringBuilder object to build the string.
   * @param cells The board to convert
   */
  private void convertBoardToString(StringBuilder sb, Cell[] cells) {
    for (Cell entry : cells) {
      switch (entry) {
        case HIT -> sb.append(" H ");
        case SHIP -> sb.append(" S ");
        case WATER -> sb.append(" - ");
        case MISS -> sb.append(" M ");
        default -> throw new EnumConstantNotPresentException(Cell.class, "Cell");
      }
    }
    sb.append(System.lineSeparator());
  }


  /**
   * Places a ship onto the board. Retries until a valid placement is found
   *
   * @param shipType An enumeration representing which ship to place.
   */
  private void placeShip(ShipType shipType) {
    int shipLength = shipType.getLength();
    Ship ship;

    // Converts a ShipType enumeration to a Ship object
    switch (shipType) {
      case CARRIER -> ship = new Carrier();
      case BATTLESHIP -> ship = new Battleship();
      case DESTROYER -> ship = new Destroyer();
      case SUBMARINE -> ship = new Submarine();
      default -> throw new EnumConstantNotPresentException(ShipType.class, "ShipType");
    }

    // Generates a random set of coordinates (within bounds of the board), and then attempts to
    // place a ship at those coordinates and rotation. Retries until valid location found.
    while (true) {
      // Choosing the placement and orientation of this ship
      int placementRow = this.rand.nextInt(rows);
      int placementCol = this.rand.nextInt(columns);
      boolean rotation = this.rand.nextBoolean();

      // checking if the cell position is empty
      if (playerBoard[placementRow][placementCol].equals(Cell.WATER)) {
        if (rotation) {
          if (checkSpacesOpen(shipLength, placementRow, placementCol, 0, 1)) {
            // Add the ship's coordinates to the Ship object
            for (int i = 0; i < shipLength; i += 1) {
              playerBoard[placementRow][placementCol + i] = Cell.SHIP;
              ship.addCoordinate(new Coord(placementRow, placementCol + i));
            }
            break;
          }
        } else {
          if (checkSpacesOpen(shipLength, placementRow, placementCol, 1, 0)) {
            // Add the ship's coordinates to the Ship object
            for (int i = 0; i < shipLength; i += 1) {
              playerBoard[placementRow + i][placementCol] = Cell.SHIP;
              ship.addCoordinate(new Coord(placementRow + i, placementCol));
            }
            break;
          }
        }
      }
    }
    allShips.add(ship);
  }

  /**
   * Verifies that a ship is placeable at these coordinates.
   *
   * @param length The length of the ship being placed.
   * @param row    The rows where the ship is being placed.
   * @param col    The column where the ship is being placed.
   * @param dx     The x direction rotation.
   * @param dy     The y direction rotation.
   * @return True when the ship has been successfully placed onto the board
   */
  private boolean checkSpacesOpen(int length, int row, int col, int dx, int dy) {
    if ((row + dx * length - 1 < playerBoard.length)
        && (col + dy * length - 1 < playerBoard[0].length)) {

      // verifying that the entire branch is open to spots
      for (int i = 0; i < length; i += 1) {
        if (!playerBoard[row + (i * dx)]
            [col + (i * dy)]
            .equals(Cell.WATER)) {
          return false;
        }
      }

    } else {
      return false;
    }
    return true;
  }
}
