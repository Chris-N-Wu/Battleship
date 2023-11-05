package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.Display;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents possible prompts to the user
 */
public class Prompts {
  private final InputStream inputStream;
  private final Display display;
  private final Scanner sc;

  /**
   * Default constructor for the Prompts class.
   *
   * @param inputStream The desired method for inputs.
   * @param display     The method which to display messages.
   */
  public Prompts(InputStream inputStream, Display display) {
    this.inputStream = inputStream;
    this.display = display;
    this.sc = new Scanner(inputStream);
  }

  /**
   * Prompts the user to input their board size
   *
   * @param lowerLimit The lowest valid board size
   * @param upperLimit The highest valid board size
   * @return A coord contain the x (rows) and y (cols) size of the board
   */
  public Coord promptBoardSize(int lowerLimit, int upperLimit) {

    // Prompts the user for an integer. 6 and 15 represents the minimum and maximum board sizes
    int[] sizes;
    do {
      this.display.printMessage("Enter Board Size [6, 15]:");
      sizes = this.promptInt();
    } while (!validateBoardSize(sizes, lowerLimit, upperLimit));

    Coord coord = new Coord(sizes[0], sizes[1]);
    this.display.printMessage("You Entered: " + coord);
    return coord;
  }

  /**
   * Prompts the user to select their fleet.
   *
   * @param maximumFleetSize The maximum allowable number of ships.
   * @return An EnumMap representing each ship with its count.
   */
  public EnumMap<ShipType, Integer> promptFleetSelection(int maximumFleetSize) {
    // continues until a valid entry has been made.
    fleetSelection:
    while (true) {
      EnumMap<ShipType, Integer> inputShips = new EnumMap<>(ShipType.class);
      this.display.printMessage(
          "Enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");

      // Tries gathering user input
      try {
        String input = sc.nextLine();
        // Cleans the user's input
        String[] inputs = input.trim().split("\\s+");

        // Makes sure correct # of inputs
        if (inputs.length != ShipType.values().length) {
          this.display.printError(ShipType.values().length + " Arguments Required");
          continue;
        }

        // Adds each ship and its # of desired occurrences to inputShips array.
        ShipType[] shipTypes = ShipType.values();
        for (int i = 0; i < shipTypes.length; i += 1) {
          try {
            inputShips.put(shipTypes[i], Integer.parseInt(inputs[i]));
          } catch (NumberFormatException e) {
            this.display.printError("Inputs Must Be Integers");
            continue fleetSelection;
          }
        }

        // Checks that the fleet selection is valid
        if (this.validateFleetSelection(inputShips, maximumFleetSize)) {
          return inputShips;
        } else {
          this.display.printError(
              "Total # of Ships Must Not Exceed " + maximumFleetSize + " ships");
        }
      } catch (NoSuchElementException e) {
        this.display.printError("Unknown Input");
      }
    }
  }

  /**
   * Prompts the user to input their shots.
   *
   * @param previousShots Previous shots that the player has taken
   * @param numShots The # of shots a Player can make
   * @param max Denotes which shots are within bounds of the board.
   * @return An ArrayList containing the shots that the player has taken
   */
  public ArrayList<Coord> promptShots(HashSet<Coord> previousShots, int numShots, Coord max) {
    ArrayList<Coord> targets = new ArrayList<>();
    Coord coord;

    // Displays prompts
    this.display.printMessage("Enter Targets...");
    this.display.printMessage(
        "Row Input Must Be From: [0, " + max.x() + ")"
            + ", Col Input Must Be From: [0 to " + max.y() + ")");

    // Loops through the # of shots a player can make.
    for (int i = 0; i < numShots; i += 1) {
      this.display.printMessage("Shot: " + (i + 1));
      int[] shots;

      // Loops through prompting the player until a valid shot is made
      do {
        shots = this.promptInt();
        coord = new Coord(shots[0], shots[1]);
      } while (!this.validateShotSelection(previousShots, targets, coord, max)
          && previousShots.size() < max.x() * max.y());

      this.display.printMessage("You Entered: " + coord);
    }

    return targets;
  }

  /**
   * Prompts the user for two integers. From [lowerLimit, upperLimit].
   *
   * @return The user's input.
   */
  private int[] promptInt() {
    int[] numbers = new int[2];

    // continues until a valid entry has been made.
    lookForInput:
    while (true) {
      try {
        String input = sc.nextLine();
        // parses and processes the inputs.
        String[] inputs = input.trim().replace(System.lineSeparator(), "").split("\\s+");

        // Checks that only two values were inputted
        if (inputs.length != 2) {
          this.display.printError("Must Input 2 Values");
          continue;
        }

        // Verifies that both inputs are integers.
        for (int i = 0; i < numbers.length; i += 1) {
          try {
            // Attempts to parse String into an integer.
            numbers[i] = Integer.parseInt(inputs[i]);
          } catch (NumberFormatException e) {
            this.display.printError("Inputs Must Be Integers");
            continue lookForInput;
          }
        }

        return numbers;

      } catch (NoSuchElementException e) {
        this.display.printError("Unknown Input");
      }
    }
  }

  /**
   * Validates a user's input for shots
   *
   * @param previouslyShot Previous shots, so the player cannot shoot the same place twice.
   * @param list           The player's inputs
   * @param coord          The coordinates that a player inputted
   * @param bounds         The maximum row and column.
   * @return True if the shot is valid.
   */
  private boolean validateShotSelection(HashSet<Coord> previouslyShot, ArrayList<Coord> list,
                                        Coord coord, Coord bounds) {
    // Checks that an input is within bounds
    if (coord.x() >= 0 && coord.x() < bounds.x() && coord.y() >= 0 && coord.y() < bounds.y()) {
      if (!list.contains(coord) && !previouslyShot.contains(coord)) {
        previouslyShot.add(coord);
        list.add(coord);
        return true;
      } else {
        this.display.printError("Coord Already Inputted. Try Again");
        return false;
      }
    } else {
      this.display.printError(
          "Rows must be [0 and " + coord.x() + "), Cols must be [0 and " + coord.y() + ")");
      return false;
    }
  }

  /**
   * Validates the board size that the player inputted
   *
   * @param inputs     The player inputs
   * @param lowerLimit The lower limit of sizes that are valid.
   * @param upperLimit The upper limit of sizes that are valid.
   * @return True if the inputs are both valid.
   */
  private boolean validateBoardSize(int[] inputs, int lowerLimit, int upperLimit) {
    for (int num : inputs) {
      if (num < lowerLimit || num > upperLimit) {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks that the input values are valid.
   *
   * @param inputShips An EnumMap representing each Ship with its # count.
   * @param maxShips   The maximum number of ships allowed by this board size.
   * @return True if all conditions are met.
   */
  private boolean validateFleetSelection(EnumMap<ShipType, Integer> inputShips, int maxShips) {
    int totalShips = 0;

    // Loops through each entry in the EnumMap, checking that no ship has a count of 0 or less.
    for (Map.Entry<ShipType, Integer> entry : inputShips.entrySet()) {
      int entryValue = entry.getValue();

      // Checking that the entry value is a positive integer
      if (entryValue <= 0) {
        this.display.printError("Input Must Be An Integer Greater Than Zero");
        return false;
      }

      totalShips += entryValue;
    }

    // checking that the total number of ships does not exceed the maximum allowed by board.
    return (totalShips <= maxShips);
  }

  /**
   * Closes the InputStream to prevent memory leakage
   */
  public void clean() {
    try {
      this.inputStream.close();
    } catch (IOException e) {
      throw new RuntimeException("No Input Stream Open");
    }
  }

}


