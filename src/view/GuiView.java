package view;

import java.awt.event.ActionListener;

import model.IImage;
import model.ImageImpl;

/**
 * This interface represents the methods used to make our GUI view.
 */
public interface GuiView {

  /**
   * Refreshes the screen. This is called when the something on the
   * screen is updated and must be redrawn.
   */
  void refresh();

  /**
   * Displays a message in a suitable area.
   *
   * @param message a message to be displayed
   */
  void renderMessage(String message);

  /**
   * Sets action listeners for each button or feature passed into it. Allows for the controller
   * to see what feature has an action.
   *
   * @param listener the action listener added
   */
  void setListener(ActionListener listener);

  /**
   * Creates a new ImagePanel when necessary. Deletes an old ImagePanel and redraws
   * with a new ImagePanel.
   *
   * @param image the image that will be drawn in the new ImagePanel
   */
  void setImagePanel(IImage image);

  /**
   * Creates a new HistogramPanel when necessary. Deletes an old HistogramPanel if it exists and
   * redraws with a new HistogramPanel
   *
   * @param image the image that is represented in our histogram
   * @param type  the type of histogram that will be printed
   */
  void setHistogramPanel(ImageImpl image, String type);

  /**
   * Determines which type of grayscale will be used to edit an image. The type is determined by
   * what the user selects on the grayscale combobox.
   *
   * @return the specific grayscale edit type
   */
  String getSelectedGrayscale();

  /**
   * We created our own setVisible method so we can use it in our controller for testing.
   *
   * @param b boolean for whether something is visible or not
   */
  void setVisibility(Boolean b);
}
