package model.commands;

import model.IImage;
import model.ImageImpl;

/**
 * This is the ImageProcessingCommands interface that contains the method each function object uses.
 */
public interface ImageProcessingCommands {

  /**
   * Executes a process on an image. What is executed is determined by the function object the
   * method is in.
   * @param image the image that is to be edited
   * @return a new edited Image
   */
  ImageImpl process(IImage image);

}