package model.commands;

import model.IImage;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;

/**
 * This is the class GrayScale that grayscales an image that is retrieved from a hashmap. The
 * type of grayscale that occurs is up to the user's specification.
 */
public class GrayScale implements ImageProcessingCommands {

  private final String type;

  /**
   * This is the constructor for grayscale. The constructor gets the contents that are passed into
   * and carries out a grayscale edit to them.
   *
   * @param type the type of operation
   */
  public GrayScale( String type) {
    this.type = type;
  }

  @Override
  public ImageImpl process(IImage image) {
    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {

        Pixel p;
        int pixelValue;
        p = image.getPixelAt(row, col);

        switch (type) {
          case "red":
            newPixels[row][col] = new PixelImpl(p.getR(), p.getR(), p.getR());
            break;
          case "green":
            newPixels[row][col] = new PixelImpl(p.getG(), p.getG(), p.getG());
            break;
          case "blue":
            newPixels[row][col] = new PixelImpl(p.getB(), p.getB(), p.getB());
            break;
          case "value":
            pixelValue = Math.max(p.getR(), Math.max(p.getB(), p.getG()));
            newPixels[row][col] = new PixelImpl(pixelValue, pixelValue, pixelValue);
            break;
          case "intensity":
            pixelValue = (p.getR() + p.getG() + p.getB()) / 3;
            newPixels[row][col] = new PixelImpl(pixelValue, pixelValue, pixelValue);
            break;
          case "luma":
            pixelValue = (int) (Math.round(0.2126 * p.getR()
                    + 0.7152 * p.getG() + 0.0722 * p.getB()));
            newPixels[row][col] = new PixelImpl(pixelValue, pixelValue, pixelValue);
            break;
          default:
            break;
        }
      }
    }

    return new ImageImpl(image.getWidth(), image.getHeight(), image.getMaxValue(), newPixels);
  }
}
