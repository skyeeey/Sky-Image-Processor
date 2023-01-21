package model.commands;

import model.IImage;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;

/**
 * This is the class that applies filters to an image. It transforms each
 * individual pixel on an image that is retrieved from a hashmap.
 * The filter that is applied is up to the user's specification.
 */
public class ColorTransformation implements ImageProcessingCommands {

  private final String type;

  /**
   * This is the constructor for ColorTransformation. The constructor gets the contents
   * that are passed into and carries out a filter edit to them.
   *
   * @param type        the type of operation
   */
  public ColorTransformation(String type) {

    this.type = type;
  }

  @Override
  public ImageImpl process(IImage image) {
    double[][] matrix = new double[3][3];
    int[] colors;
    int[] accumulators;
    Pixel[][] newPixels = new Pixel[image.getHeight()][image.getWidth()];

    for (int iRow = 0; iRow < image.getHeight(); iRow++) {
      for (int iCol = 0; iCol < image.getWidth(); iCol++) {
        int red = 0;
        int green = 0;
        int blue = 0;

        colors = new int[] {image.getPixelAt(iRow,iCol).getR(),
                image.getPixelAt(iRow,iCol).getG(),
                image.getPixelAt(iRow,iCol).getB()};

        accumulators = new int[] {red,green,blue};

        for (int mRow = 0; mRow < matrix.length; mRow++) {
          for (int mCol = 0; mCol < matrix.length; mCol++) {

            if (type.equals("luma")) {
              matrix = new double[][]{
                      {0.2126, 0.7152, 0.0722},
                      {0.2126, 0.7152, 0.0722},
                      {0.2126, 0.7152, 0.0722}};
            }

            if (type.equals("sepia")) {
              matrix = new double[][]{
                      {0.393, 0.769, 0.189},
                      {0.349, 0.686, 0.168},
                      {0.272, 0.534, 0.131}};
            }
            //for each pixel, r' = [0][0]r + [0][1]g + [0][2]b
            //for each three doubles,

            accumulators[mRow] += matrix[mRow][mCol] * (double) colors[mCol];

          }
        }
        newPixels[iRow][iCol] = new PixelImpl(accumulators[0], accumulators[1], accumulators[2]);
      }
    }

    return new ImageImpl(image.getWidth(), image.getHeight(),
            image.getMaxValue(), newPixels);
  }
}
