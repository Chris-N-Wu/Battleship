package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  private Board board;

  @BeforeEach
  void setUp() {
    EnumMap<ShipType, Integer> enumMapShips = new EnumMap<>(ShipType.class);
    enumMapShips.put(ShipType.CARRIER, 1);
    enumMapShips.put(ShipType.BATTLESHIP, 1);
    enumMapShips.put(ShipType.DESTROYER, 1);
    enumMapShips.put(ShipType.SUBMARINE, 1);

    this.board = new Board(6, 6, enumMapShips, new Random(500));
    this.board.generateBoard();
  }

  /**
   * Tests that no exception is thrown and board is generated correctly for a board that has hit
   * max number of ships
   */
  @Test
  void generatedAnotherBoard() {
    EnumMap<ShipType, Integer> enumMapShips = new EnumMap<>(ShipType.class);
    enumMapShips.put(ShipType.CARRIER, 3);
    enumMapShips.put(ShipType.BATTLESHIP, 1);
    enumMapShips.put(ShipType.DESTROYER, 1);
    enumMapShips.put(ShipType.SUBMARINE, 1);

    Board board2 = new Board(6, 6, enumMapShips, new Random(231));
    assertDoesNotThrow(board2::generateBoard);

    // Testing that board is still generated correctly
    String expectedPlayerBoard = " -  S  S  S  S  S " + System.lineSeparator()
        + " S  S  S  S  S  S " + System.lineSeparator()
        + " S  S  S  S  S  S " + System.lineSeparator()
        + " -  -  -  S  S  S " + System.lineSeparator()
        + " S  S  S  S  S  S " + System.lineSeparator()
        + " -  S  S  S  S  - " + System.lineSeparator();
    assertEquals(expectedPlayerBoard, board2.getPlayerBoard());
  }

  /**
   * Testing the generateBoard method in Board.
   * This tests that the ships are placed correctly and the number of ships is correct
   */
  @Test
  void generateBoard() {
    // Testing with the view of the Player's board
    String expectedPlayerBoard = " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  - " + System.lineSeparator()
        + " -  -  -  S  -  - " + System.lineSeparator();

    String outputPlayerBoard = this.board.getPlayerBoard();

    assertEquals(expectedPlayerBoard, outputPlayerBoard);

    // Testing that the number of ships placed down is correct.
    // This would not work if ships were overlapped.
    // Should be 18 (as 6 + 5 + 4 + 3)
    int shipCount = 0;
    for (int i = 0; i < outputPlayerBoard.length(); i += 1) {
      if (outputPlayerBoard.charAt(i) == 'S') {
        shipCount += 1;
      }
    }

    assertEquals(18, shipCount);

    // Testing with the Player's view of the opponent's board
    String expectedOpponentBoard = " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();

    assertEquals(expectedOpponentBoard, this.board.getOpponentBoard());
  }

  /**
   * Tested extensively through side effects.
   * Other test methods use this method to view the result of their actions.
   */
  @Test
  void getPlayerBoard() {
  }

  /**
   * Tested extensively through side effects.
   * Other test methods use this method to view the result of their actions.
   */
  @Test
  void getOpponentBoard() {
  }

  /**
   * Testing the updatePlayerBoard method.
   * Only testing if the board updates correctly, this method does not check if the new state is
   * correct as a transition from its previous state (ex. if a ship was really hit)
   */
  @Test
  void updatePlayerBoard() {
    // Testing with a HIT cell
    this.board.updatePlayerBoard(new Coord(5, 3), Cell.HIT);

    // Testing with the view of the Player's board
    String expectedPlayerBoard = " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  - " + System.lineSeparator()
        + " -  -  -  H  -  - " + System.lineSeparator();

    assertEquals(expectedPlayerBoard, this.board.getPlayerBoard());

    // Testing with a MISS cell
    this.board.updatePlayerBoard(new Coord(3, 2), Cell.MISS);

    // Testing with the view of the Player's board
    String expectedPlayerBoard2 = " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  M  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  - " + System.lineSeparator()
        + " -  -  -  H  -  - " + System.lineSeparator();

    assertEquals(expectedPlayerBoard2, this.board.getPlayerBoard());

    // Testing with a WATER cell
    this.board.updatePlayerBoard(new Coord(3, 3), Cell.WATER);

    // Testing with the view of the Player's board
    String expectedPlayerBoard3 = " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  M  -  S  S " + System.lineSeparator()
        + " -  -  S  S  S  - " + System.lineSeparator()
        + " -  -  -  H  -  - " + System.lineSeparator();

    assertEquals(expectedPlayerBoard3, this.board.getPlayerBoard());

    // Testing with a SHIP cell
    this.board.updatePlayerBoard(new Coord(0, 0), Cell.SHIP);

    // Testing with the view of the Player's board
    String expectedPlayerBoard4 = " S  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  M  -  S  S " + System.lineSeparator()
        + " -  -  S  S  S  - " + System.lineSeparator()
        + " -  -  -  H  -  - " + System.lineSeparator();

    assertEquals(expectedPlayerBoard4, this.board.getPlayerBoard());

    // Testing that nothing should happen on the Player's board when we update the Player's view
    // of the opponent's board.
    this.board.updateOpponentBoard(new Coord(0, 0), Cell.HIT);
    assertEquals(expectedPlayerBoard4, this.board.getPlayerBoard());
  }

  /**
   * Testing the updateOpponentBoard method (player's view of Opponent)
   * Only testing if the board updates correctly, this method does not check if the new state is
   * correct as a transition from its previous state (ex. if a ship was really hit)
   */
  @Test
  void updateOpponentBoard() {
    // Testing with a SHIP cell
    this.board.updateOpponentBoard(new Coord(0, 0), Cell.SHIP);

    // Testing with the Player's view of the opponent's board
    String expectedOpponentBoard = " S  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();
    assertEquals(expectedOpponentBoard, this.board.getOpponentBoard());

    // Testing with a HIT cell
    this.board.updateOpponentBoard(new Coord(0, 0), Cell.HIT);

    // Testing with the Player's view of the opponent's board
    String expectedOpponentBoard2 = " H  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();
    assertEquals(expectedOpponentBoard2, this.board.getOpponentBoard());

    // Testing with a MISS cell
    this.board.updateOpponentBoard(new Coord(1, 1), Cell.MISS);

    // Testing with the Player's view of the opponent's board
    String expectedOpponentBoard3 = " H  -  -  -  -  - " + System.lineSeparator()
        + " -  M  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();
    assertEquals(expectedOpponentBoard3, this.board.getOpponentBoard());

    // Testing with a WATER cell
    this.board.updateOpponentBoard(new Coord(1, 1), Cell.WATER);

    // Testing with the Player's view of the opponent's board
    String expectedOpponentBoard4 = " H  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();
    assertEquals(expectedOpponentBoard4, this.board.getOpponentBoard());
  }

  /**
   * Tests the shootPlayer() method
   */
  @Test
  void shootPlayer() {
    // Testing where a ship is
    assertFalse(this.board.shootPlayer(new Coord(0, 0)));
    // Testing with a coordinate where a ship is
    assertTrue(this.board.shootPlayer(new Coord(0, 2)));
    // Testing with a coordinate where a ship was, but has already been hit
    assertFalse(this.board.shootPlayer(new Coord(0, 2)));
    // Testing with an out-of-bounds coordinate
    assertFalse(this.board.shootPlayer(new Coord(-5, -2)));
    // Testing with another spot of a ship (but different from the previous)
    assertTrue(this.board.shootPlayer(new Coord(4, 3)));

  }

  /**
   * Tests the numberShipsLeft method
   */
  @Test
  void numberShipsLeft() {
    // Testing when there are still four ships left (as no shots have been fired yet)
    assertEquals(4, this.board.numberShipsLeft());

    // Removing a ship
    this.board.shootPlayer(new Coord(0, 2));
    this.board.shootPlayer(new Coord(1, 2));
    this.board.shootPlayer(new Coord(2, 2));
    this.board.shootPlayer(new Coord(3, 2));
    // Testing that there are still 4 ships left (even if it's heavily damaged)
    assertEquals(4, this.board.numberShipsLeft());
    this.board.shootPlayer(new Coord(4, 2));

    // Testing that there are now 3 ships left
    assertEquals(3, this.board.numberShipsLeft());

    // Removing another ship
    this.board.shootPlayer(new Coord(0, 3));
    this.board.shootPlayer(new Coord(1, 3));
    this.board.shootPlayer(new Coord(2, 3));
    this.board.shootPlayer(new Coord(3, 3));
    this.board.shootPlayer(new Coord(4, 3));
    this.board.shootPlayer(new Coord(5, 3));

    // Testing that there are now 2 ships left
    assertEquals(2, this.board.numberShipsLeft());

    // Removing another ship
    this.board.shootPlayer(new Coord(2, 4));
    this.board.shootPlayer(new Coord(3, 4));
    // This should not yet remove a ship because even though we hit three ship cells,
    // it's not three of the same ship
    this.board.shootPlayer(new Coord(0, 5));

    // Testing that there are still 2 ships left
    assertEquals(2, this.board.numberShipsLeft());

    // Removing the last Ship cell for this Ship
    this.board.shootPlayer(new Coord(4, 4));

    // Testing that there are still 1 ship left
    assertEquals(1, this.board.numberShipsLeft());

    // Removing the final ship (this ship is 4 in length, but because we shot (0, 5) previously,
    // we only need 3 shots
    this.board.shootPlayer(new Coord(1, 5));
    this.board.shootPlayer(new Coord(2, 5));
    this.board.shootPlayer(new Coord(3, 5));

    // Testing that there are still 0 ships left
    assertEquals(0, this.board.numberShipsLeft());
  }
}