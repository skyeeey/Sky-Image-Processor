package control;

/**
 * This IController interface represents a controller for our Image processor,
 * which contains method takes in an input and runs commands based on
 * an input and outputs data.
 */
public interface IController {

  /**
   * This method runs the controller that reads an input script and carries out
   * commands accordingly.
   */
  void run();
}
