package model.commands;

import model.IImage;
import model.ImageImpl;
import model.Pixel;

/**
 * This is the class Flip that flips an image that is retrieved from a hashmap. The type of flip
 * that occurs is up to the user's specification.
 */
public class Flip implements ImageProcessingCommands {

  private final String type;

  /**
   * This is the constructor for flip. The constructor gets the contents that are passed into
   *    * and carries out a flip edit to them.
   *
   * @param type        the type of operation
   */
  public Flip(String type) {
    this.type = type;
  }

  @Override
  public ImageImpl process(IImage image) {
    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {

        switch (type) {
          case "horizontal":
            newPixels[row][col] = image.getPixelAt(row, image.getWidth() - col - 1);
            break;
          case "vertical":
            newPixels[row][col] = image.getPixelAt(image.getHeight() - row - 1, col);
            break;
          default:
            break;
        }
      }
    }

    return new ImageImpl(image.getWidth(), image.getHeight(), image.getMaxValue(), newPixels);
  }
}
