package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The image class is an implementation of the Image interface.
 * An ImageImpl represents one object
 * of our Image interface that is readable by our program.
 * Also implements methods that retrieve data from an image.
 */
public class ImageImpl implements IImage {

  private final int width;
  private final int height;
  private final int maxValue;
  private final Pixel[][] wholeImage;

  /**
   * Constructs an ImageImpl and assigns each pixel its own respective coordinate in the 2d array.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @throws IllegalArgumentException if the width, height, or max value is invalid
   * @throws IllegalArgumentException if the array of pixels is null
   */
  public ImageImpl(int width, int height, int maxValue, Pixel[][] pixels)
          throws IllegalArgumentException {

    if (width < 1 || height < 1 || maxValue < 1) {
      throw new IllegalArgumentException("Width, height, or max value is less than one");
    }

    if (pixels == null) {
      throw new IllegalArgumentException("pixels is null");
    }

    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.wholeImage = pixels;

    // populate method
    // we will only need to call populate once
    // so we cn just add it to the constructor where it is called
    for (int row = 0; row < getHeight(); row++) {
      for (int column = 0; column < getWidth(); column++) {
        this.wholeImage[row][column] = getPixelAt(row, column);
      }
    }
  }

  @Override
  public Pixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row > getHeight() || col > getWidth() || row < 0 || col < 0) {
      throw new IllegalArgumentException("input is greater than bounds of the board");
    }
    return this.wholeImage[row][col];
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public Map<Integer, Integer> getHistogramValues(String type) {
    List<Integer> allValues = new ArrayList<>();
    Map<Integer, Integer> histogram = new HashMap<>();

    for (int row = 0; row < getHeight(); row++) {
      for (int column = 0; column < getWidth(); column++) {
        switch (type) {
          case "red":
            allValues.add(this.wholeImage[row][column].getR());
            break;
          case "green":
            allValues.add(this.wholeImage[row][column].getG());
            break;
          case "blue":
            allValues.add(this.wholeImage[row][column].getB());
            break;
          case "intensity":
            allValues.add((this.wholeImage[row][column].getR()
                    + this.wholeImage[row][column].getG()
                    + this.wholeImage[row][column].getB()) / 3);
            break;
          default:
            break;
        }
      }
    }
    // every pixel value
    for (int pixelValues = 0; pixelValues < 256; pixelValues++) {
      int count = 0;
      for (int value = 0; value < allValues.size(); value++) {
        if (pixelValues == allValues.get(value)) {
          count++;
        }
      }
      histogram.put(pixelValues, count);
    }
    return histogram;
  }
}

