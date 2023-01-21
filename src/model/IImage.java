
package model;

import java.util.Map;

/**
 * This Image interface represents an image,
 * which can work for difference sub-classes implemented inside.
 * also contains methods that retrieve data from an image.
 */
public interface IImage {

  /**
   * Represents the method that get the pixel at given row and col.
   *
   * @param row the height coordinate
   * @param col the width coordinate
   * @return the pixel at a certain coordinate in the 2D array
   * @throws IllegalArgumentException when the given row is smaller than the height of the image,
   *                                  or when the given col is smaller than the width of the image,
   *                                  or when the given row or col is smaller than zero.
   */
  Pixel getPixelAt(int row, int col);

  /**
   * Gets the integer assigned to the variable height.
   *
   * @return the height as an integer
   */
  int getHeight();

  /**
   * Gets the integer assigned to the variable width.
   *
   * @return the width as an integer
   */
  int getWidth();

  /**
   * Gets the integer assigned to the variable's max value.
   *
   * @return the max value as an integer
   */
  int getMaxValue();

  /**
   * Maps all the specific values in an Image by iterating them though it. Then counts the
   * frequency of all 256 pixel values.
   * @param type the pixel value type to be counted
   * @return a map of each pixel value and its corresponding frequency
   */
  Map<Integer, Integer> getHistogramValues(String type);
}
