package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing the Display (view) class
 */
class DisplayTest {
  private StringBuilder expectedString;
  private Appendable collector;
  private Display display;

  /**
   * Setting up commonly used fields.
   */
  @BeforeEach
  public void setUp() {
    expectedString = new StringBuilder();
    collector = new StringWriter();
    display = new Display(collector);
  }

  /**
   * Tests the print a normal message.
   */
  @Test
  void printMessage() {
    // Message input
    expectedString.append("This is a simple message").append(System.lineSeparator());
    display.printMessage("This is a simple message");

    assertEquals(expectedString.toString(), collector.toString());

    expectedString.append("This is another message").append(System.lineSeparator());
    display.printMessage("This is another message");
    assertEquals(expectedString.toString(), collector.toString());

  }

  /**
   * Tests the printing error.
   */
  @Test
  void printError() {
    // Message input
    expectedString.append("[INVALID] This is a simple message").append(System.lineSeparator());
    display.printError("This is a simple message");

    assertEquals(expectedString.toString(), collector.toString());

    expectedString.append("[INVALID] This is another message").append(System.lineSeparator());
    display.printError("This is another message");
    assertEquals(expectedString.toString(), collector.toString());
  }
}