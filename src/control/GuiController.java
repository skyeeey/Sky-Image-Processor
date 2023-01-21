package control;

/**
 * This interface represents the controller for our Gui, which contain methods that allow users to
 * interact with our image processor by processing on loaded images so that they can see the process
 * and save images.
 */
public interface GuiController {

  /**
   * Sets the GUI window to be visible by the user and 'starts' the GUI. Without it nothing
   * would be visible.
   */
  void run();

  /**
   * Used for testing to simulate incrementing imageNum.
   */
  void incrementImageNum();
}
