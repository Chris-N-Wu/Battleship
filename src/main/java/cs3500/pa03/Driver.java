package cs3500.pa03;

import cs3500.pa03.controller.BattleSalvo;
import cs3500.pa03.controller.Prompts;
import cs3500.pa03.model.ComputerControl;
import cs3500.pa03.model.ManualControl;
import cs3500.pa03.view.Display;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Display display = new Display(System.out);
    Prompts prompts = new Prompts(System.in, display);
    BattleSalvo battleSalvo =
        new BattleSalvo(new ManualControl(prompts), new ComputerControl(), prompts, display);
    battleSalvo.startBattleSalvo();
  }
}