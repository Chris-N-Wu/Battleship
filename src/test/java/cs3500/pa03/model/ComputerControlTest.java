package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.EnumMap;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Computer Control.
 * Only testing takeShots as that is the only method in this class, and abstract methods are
 * extensively tested in {@link ManualControlTest}.
 *
 */
class ComputerControlTest {

  /**
   * Testing the takeShots method for a Computer
   */
  @Test
  void takeShots() {
    // Setting up ships
    EnumMap<ShipType, Integer> specifications = new EnumMap<>(ShipType.class);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.SUBMARINE, 1);
    specifications.put(ShipType.CARRIER, 1);

    // Having the computer player take a shot
    ComputerControl computerControl = new ComputerControl();
    computerControl.setup(6, 6, specifications);
    final ArrayList<Coord> shots = new ArrayList<>(computerControl.takeShots());

    // Setting up expected shots
    ArrayList<Coord> expectedShots = new ArrayList<>();
    expectedShots.add(new Coord(0, 0));
    expectedShots.add(new Coord(0, 1));
    expectedShots.add(new Coord(0, 2));
    expectedShots.add(new Coord(0, 3));

    // Checking that the shots are equal
    assertArrayEquals(expectedShots.toArray(), shots.toArray());
  }
}