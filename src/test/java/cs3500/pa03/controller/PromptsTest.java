package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Display;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the prompts (controller) class
 */
class PromptsTest {
  private Appendable collector;
  private Display display;
  private FileInputStream fileInputStream;
  private Prompts prompts;
  private File fileInputs;
  private File fileOutputs;

  /**
   * Setting up commonly used fields.
   */
  @BeforeEach
  public void setUp() {
    this.collector = new StringWriter();
    this.display = new Display(collector);
    this.fileInputStream = null;
  }

  /**
   * Preventing memory leakage
   */
  @AfterEach
  public void cleanUp() {
    this.prompts.clean();
  }

  /**
   * Tests the promptBoardSize method in Prompts
   */
  @Test
  void promptBoardSize() {
    // Tests with a variety of inputs. All of which are invalid until the last one
    fileInputs = new File("./src/test/resources/prompt/boardSizeInputs.txt");

    // Attempting to set file as inputStream
    try {
      fileInputStream = new FileInputStream(fileInputs);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    prompts = new Prompts(fileInputStream, this.display);
    Coord coord = prompts.promptBoardSize(6, 15);

    // Testing that the final coordinate is correct
    Coord expectedCoord = new Coord(6, 15);
    assertEquals(expectedCoord, coord);

    // Testing that the output messages are correct when invalid inputs are provided.
    fileOutputs = new File("./src/test/resources/prompt/boardSizeOutputs.txt");
    String content;
    try {
      content = Files.readString(fileOutputs.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(content, collector.toString());
  }

  /**
   * Testing the promptFleetSelection method in Prompts
   */
  @Test
  void promptFleetSelection() {
    // Tests with a variety of inputs. All of which are invalid until the last one
    fileInputs = new File("./src/test/resources/prompt/fleetSelectionInputs.txt");

    // Attempting to set file as inputStream
    try {
      fileInputStream = new FileInputStream(fileInputs);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }


    // Testing if outputted EnumMaps match up with expected
    EnumMap<ShipType, Integer> enumMapExpected = new EnumMap<>(ShipType.class);
    enumMapExpected.put(ShipType.DESTROYER, 1);
    enumMapExpected.put(ShipType.BATTLESHIP, 1);
    enumMapExpected.put(ShipType.SUBMARINE, 1);
    enumMapExpected.put(ShipType.CARRIER, 1);

    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    prompts = new Prompts(fileInputStream, this.display);
    EnumMap<ShipType, Integer> enumMapOutput = prompts.promptFleetSelection(5);

    assertEquals(enumMapExpected, enumMapOutput);

    // Testing that the output messages are correct when invalid inputs are provided.
    fileOutputs = new File("./src/test/resources/prompt/fleetSelectionOutputs.txt");
    String content;
    try {
      content = Files.readString(fileOutputs.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(content, collector.toString());
  }

  /**
   * Testing the promptShots method in Prompts
   */
  @Test
  void promptShots() {
    // Tests with a variety of inputs. All of which are invalid until the last one
    fileInputs = new File("./src/test/resources/prompt/promptShotsInputs.txt");

    // Attempting to set file as inputStream
    try {
      fileInputStream = new FileInputStream(fileInputs);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Setting up testing data
    final HashSet<Coord> previousShots = new HashSet<>();
    previousShots.add(new Coord(1, 2));
    previousShots.add(new Coord(2, 1));
    previousShots.add(new Coord(2, 2));
    Coord maximumValues = new Coord(8, 15);

    // Creating a new prompt object. First argument is our inputs, second argument is outputs
    prompts = new Prompts(fileInputStream, this.display);
    final ArrayList<Coord> shotsInput = prompts.promptShots(previousShots, 4, maximumValues);

    // Setting up expected output
    final ArrayList<Coord> shotsExpected = new ArrayList<>();
    shotsExpected.add(new Coord(3, 3));
    shotsExpected.add(new Coord(4, 4));
    shotsExpected.add(new Coord(5, 5));
    shotsExpected.add(new Coord(6, 6));

    // Checking that both the resulting ArrayList is equal to the expected output
    assertArrayEquals(shotsExpected.toArray(), shotsInput.toArray());

    // Testing that the output messages are correct when invalid inputs are provided.
    fileOutputs = new File("./src/test/resources/prompt/promptShotsOutputs.txt");
    String content;
    try {
      content = Files.readString(fileOutputs.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(content, collector.toString());
  }
}