package model.commands;

import model.IImage;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;


/**
 * This is the class that changes the quality of an image. It transforms each
 * individual pixel on an image that is retrieved from a hashmap.
 * The edit that is applied is up to the user's specification.
 */
public class Quality implements ImageProcessingCommands {

  private final String type;

  /**
   * This is the constructor for Quality. The constructor gets the contents that are passed into
   * and carries out a quality edit to them.
   *
   * @param type        the type of operation
   */
  public Quality( String type) {
    this.type = type;
  }

  @Override
  public ImageImpl process(IImage image) {
    double[][] kernel;

    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];

    if (type.equals("blur")) {
      kernel = new double[][]{
              {0.0625, 0.125, 0.0625},
              {0.125, 0.25, 0.125},
              {0.0625, 0.125, 0.0625}};
      kernelHelper(kernel, newPixels, image);
    }

    if (type.equals("sharpen")) {
      kernel = new double[][]{
              {-0.125, -0.125, -0.125, -0.125, -0.125},
              {-0.125, 0.25, 0.25, 0.25, -0.125},
              {-0.125, 0.25, 1, 0.25, -0.125},
              {-0.125, 0.25, 0.25, 0.25, -0.125},
              {-0.125, -0.125, -0.125, -0.125, -0.125}};
      kernelHelper(kernel, newPixels, image);
    }

    return new ImageImpl(image.getWidth(), image.getHeight(), image.getMaxValue(), newPixels);
  }

  /**
   * This is the helper that iterates through the kernel and through the picture and applies the
   * filter to each pixel.
   *
   * @param kernel the kernel that represent a specific filter data
   * @param newPixels a 2d array of pixels
   * @param image an image
   */
  private void kernelHelper(double[][] kernel, Pixel[][] newPixels, IImage image) {

    // for loop for image row
    // for loop for image col
    for (int iRow = 0; iRow < image.getHeight(); iRow++) {
      for (int iCol = 0; iCol < image.getWidth(); iCol++) {

        // accumulator
        double redAccumulator = 0;
        double greenAccumulator = 0;
        double blueAccumulator = 0;

        // for loop for kernel row
        // for loop for kernel col
        for (int kRow = kernel.length / -2; kRow < kernel.length / 2 + 1; kRow++) {
          for (int kCol = kernel.length / -2; kCol < kernel.length / 2 + 1; kCol++) {

            if (iRow + kRow > -1 && iCol + kCol > -1
                    && iRow + kRow < image.getHeight() && iCol + kCol < image.getWidth()) {
              redAccumulator += image.getPixelAt(iRow + kRow, iCol + kCol).getR()
                      * kernel[kRow + kernel.length / 2][kCol + kernel.length / 2];
              greenAccumulator += image.getPixelAt(iRow + kRow, iCol + kCol).getG()
                      * kernel[kRow + kernel.length / 2][kCol + kernel.length / 2];
              blueAccumulator += image.getPixelAt(iRow + kRow, iCol + kCol).getB()
                      * kernel[kRow + kernel.length / 2][kCol + kernel.length / 2];
            }
          }
        }

        // set pixel value equal to iterated accumulator
        newPixels[iRow][iCol] = new PixelImpl((int) Math.round(redAccumulator),
                (int) Math.round(greenAccumulator), (int) Math.round(blueAccumulator));
      }
    }
  }
}

