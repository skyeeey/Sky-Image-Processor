package view;

import java.io.IOException;

/**
 * This is the ImageView interface that contains the methods that write messages to an appendable.
 */
public interface ImageView {

  /**
   * Render a specific message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the message to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;

}
