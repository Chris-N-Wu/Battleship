package cs3500.pa03.view;

import java.io.IOException;

/**
 * An Object for displaying messages (or errors) to the User.
 */
public class Display {
  private final Appendable appendable;

  /**
   * Default constructor.
   *
   * @param appendable The desired output method.
   */
  public Display(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   * Prints the provided message to the output.
   *
   * @param phrase The message to be printed.
   */
  public void printMessage(String phrase) {
    try {
      appendable.append(phrase).append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable To Append Phrase");
    }
  }

  /**
   * Prints the provided error to the output.
   *
   * @param error The error to be printed
   */
  public void printError(String error) {
    try {
      appendable.append("[INVALID] ").append(error).append(System.lineSeparator());
    } catch (IOException e) {
      throw new IllegalArgumentException("Error: Unable To Append Phrase");
    }
  }
}
