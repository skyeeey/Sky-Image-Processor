package gui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

/**
 * This is a mock class that represents an action event used for testing.
 */
public class MockActionEvent extends ActionEvent {

  /**
   * The constructor that uses the super and creates a 'button press'.
   * @param command command
   */
  public MockActionEvent(String command) {
    super(new JButton(), 1, command);
  }


}
