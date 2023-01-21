package model.commands;

import model.IImage;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;

/**
 * This is the class Brightness that brightens or darkens an image that is retrieved from a hashmap.
 * The class brightens or darkens an image based on the user's specification.
 */
public class Brightness implements ImageProcessingCommands {

  private final int increment;

  /**
   * This is the constructor for Brightness. A Brightness object
   *
   * @param increment   the increment to be brightened or darkened by
   */
  public Brightness(int increment) {
    this.increment = increment;
  }

  @Override
  public ImageImpl process(IImage image) {

    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        //make copy pixel
        Pixel p = image.getPixelAt(row, col);
        Pixel pCopy = p.copyPixel();
        int rValue = pCopy.getR();
        int gValue = pCopy.getG();
        int bValue = pCopy.getB();

        rValue = p.getR() + increment;

        gValue = p.getG() + increment;

        bValue = p.getB() + increment;

        newPixels[row][col] = new PixelImpl(rValue, gValue, bValue);
      }
    }

    return new ImageImpl(image.getWidth(), image.getHeight(), image.getMaxValue(), newPixels);
  }
}