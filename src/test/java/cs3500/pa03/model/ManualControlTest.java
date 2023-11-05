package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.controller.Prompts;
import cs3500.pa03.view.Display;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ManualControl implementation of a Player
 */
class ManualControlTest {
  private ManualControl manualControl;
  private Display display;
  private FileInputStream fileInputStream;
  private Prompts prompts;
  private EnumMap<ShipType, Integer> specifications;

  /**
   * Setting up commonly used fields.
   */
  @BeforeEach
  public void setUp() {
    Appendable collector = new StringWriter();
    this.display = new Display(collector);
    this.fileInputStream = null;

    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    this.prompts = new Prompts(System.in, this.display);

    // Creating our manualControl instance
    this.manualControl = new ManualControl(this.prompts, new Random(500));

    // Setting up ships
    this.specifications = new EnumMap<>(ShipType.class);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    specifications.put(ShipType.CARRIER, 1);
  }

  /**
   * Tests name method. Currently not implemented, will be implemented in either PA04 or PA05
   */
  @Test
  void name() {
    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    this.prompts = new Prompts(System.in, this.display);

    this.manualControl = new ManualControl(this.prompts);

    // Tests name, should equal a blank string as it has not been fully implemented yet
    assertEquals("", this.manualControl.name());
  }

  /**
   * Tests the setup method.
   */
  @Test
  void setup() {
    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    this.prompts = new Prompts(System.in, this.display);

    // Creating our manualControl instance
    this.manualControl = new ManualControl(this.prompts, new Random(500));


    ArrayList<Ship> ships = new ArrayList<>(this.manualControl.setup(6, 10, this.specifications));

    // Since we cannot test that the Ships are all exactly the same (without adding dozens of
    // lines of code adding each of their coordinates, only testing the size of the returned array)
    // The placement of ships correctly is tested through the side effects of other methods.
    assertEquals(4, ships.size());

  }

  /**
   * Tests the reportDamage method
   */
  @Test
  void reportDamage() {
    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    this.prompts = new Prompts(System.in, this.display);

    // Creating our manualControl instance
    this.manualControl = new ManualControl(this.prompts, new Random(500));
    this.manualControl.setup(6, 6, specifications);

    // Setting up the opponent's shots
    ArrayList<Coord> opponentShotsOnBoard = new ArrayList<>();
    opponentShotsOnBoard.add(new Coord(0, 0));
    opponentShotsOnBoard.add(new Coord(0, 2));
    opponentShotsOnBoard.add(new Coord(1, 2));
    opponentShotsOnBoard.add(new Coord(2, 2));

    // Reporting damage and returned array should only contain shots that hit a ship
    final ArrayList<Coord> returnedReport =
        new ArrayList<>(this.manualControl.reportDamage(opponentShotsOnBoard));
    ArrayList<Coord> expectedHits = new ArrayList<>();
    expectedHits.add(new Coord(0, 2));
    expectedHits.add(new Coord(1, 2));
    expectedHits.add(new Coord(2, 2));

    assertArrayEquals(expectedHits.toArray(), returnedReport.toArray());
  }

  /**
   * Testing the hasLost method
   */
  @Test
  void hasLost() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 2));
    shots.add(new Coord(1, 2));
    shots.add(new Coord(2, 2));
    shots.add(new Coord(3, 2));
    shots.add(new Coord(0, 3));
    shots.add(new Coord(1, 3));
    shots.add(new Coord(2, 3));
    shots.add(new Coord(3, 3));
    shots.add(new Coord(4, 3));
    shots.add(new Coord(5, 3));
    shots.add(new Coord(2, 4));
    shots.add(new Coord(3, 4));
    shots.add(new Coord(0, 5));
    shots.add(new Coord(4, 4));
    shots.add(new Coord(1, 5));
    shots.add(new Coord(2, 5));
    shots.add(new Coord(3, 5));

    // Testing before all ships have been shot
    this.manualControl.setup(6, 6, specifications);
    this.manualControl.reportDamage(shots);
    assertFalse(this.manualControl.hasLost());

    // Testing after all ships have been shot
    this.manualControl.reportDamage(new ArrayList<>(Collections.singleton(new Coord(4, 2))));
    assertTrue(this.manualControl.hasLost());
  }

  /**
   * Tests the takeShots method.
   * Input and validation of shots extensively tested in other methods (primarily in PromptsTest)
   */
  @Test
  void takeShots() {
    // Tests with a variety of inputs. All of which are invalid until the last one
    File fileInputs = new File("./src/test/resources/player/takeShotsInput.txt");

    // Attempting to set file as inputStream
    try {
      fileInputStream = new FileInputStream(fileInputs);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    prompts = new Prompts(fileInputStream, this.display);
    // Re-setting our setup with a new prompts object
    this.manualControl = new ManualControl(prompts, new Random(500));
    this.manualControl.setup(6, 6, specifications);

    // Taking the shots
    final ArrayList<Coord> coordsOutput = new ArrayList<>(this.manualControl.takeShots());
    // The expected output
    ArrayList<Coord> coordExpected = new ArrayList<>();
    coordExpected.add(new Coord(1, 0));
    coordExpected.add(new Coord(2, 0));
    coordExpected.add(new Coord(3, 0));
    coordExpected.add(new Coord(4, 0));
    assertArrayEquals(coordExpected.toArray(), coordsOutput.toArray());
  }

  /**
   * Tests the updateViewOfOpponent method
   */
  @Test
  void updateViewOfOpponent() {
    this.manualControl.setup(6, 6, specifications);

    // Mock data of misses
    ArrayList<Coord> misses = new ArrayList<>();
    misses.add(new Coord(0, 0));
    misses.add(new Coord(1, 1));
    misses.add(new Coord(2, 2));

    // Calling the void method
    this.manualControl.updateViewOfOpponent(misses);

    // Getting the view
    String output = this.manualControl.getOpponentBoardAsString();
    String expected = " M  -  -  -  -  - " + System.lineSeparator()
        + " -  M  -  -  -  - " + System.lineSeparator()
        + " -  -  M  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();

    assertEquals(expected, output);

  }

  /**
   * Extensively tested through other methods
   * Simply a getter for the board, not much processing or testing
   */
  @Test
  void getPlayerBoardAsString() {
    this.manualControl.setup(6, 6, specifications);
    String output = this.manualControl.getPlayerBoardAsString();
    String expected = " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  -  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  S " + System.lineSeparator()
        + " -  -  S  S  S  - " + System.lineSeparator()
        + " -  -  -  S  -  - " + System.lineSeparator();

    assertEquals(expected, output);
  }

  /**
   * Tests the getOpponentBoardAsString method
   * More extensively tested through the side effects of other tests (where we need to see what the
   * opponent's board looks like)
   */
  @Test
  void getOpponentBoardAsString() {
    // Setting up
    this.manualControl.setup(6, 6, specifications);
    String output = this.manualControl.getOpponentBoardAsString();
    String expected = " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator()
        + " -  -  -  -  -  - " + System.lineSeparator();

    assertEquals(expected, output);
  }
}