package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ComputerControl;
import cs3500.pa03.model.ManualControl;
import cs3500.pa03.view.Display;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests the BattleSalvo class
 */
class BattleSalvoTest {

  /**
   * Tests the startBattleSalvo method and the other two private methods
   */
  @Test
  void startBattleSalvo() {
    Appendable collector = new StringWriter();
    Display display = new Display(collector);

    // Tests with a variety of inputs. All of which are invalid until the last one
    File fileInputs = new File("./src/test/resources/battleSalvo/battleSalvoInputs.txt");

    // Attempting to set file as inputStream
    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(fileInputs);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    // Goes through a game BattleSalvo
    Prompts prompts = new Prompts(fileInputStream, display);
    BattleSalvo battleSalvo =
        new BattleSalvo(new ManualControl(prompts), new ComputerControl(), prompts, display,
            new Random(500));

    battleSalvo.startBattleSalvo();

    // Testing that the output messages are correct when invalid inputs are provided.
    File fileOutputs = new File("./src/test/resources/battleSalvo/battleSalvoOutputs.txt");
    String content;
    try {
      content = Files.readString(fileOutputs.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertEquals(content, collector.toString());


  }
}