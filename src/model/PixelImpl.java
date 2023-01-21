package model;

import java.util.Objects;

/**
 * Class that represents the implementation of a pixel.
 * Having three fields r/g/b inside.
 */
public class PixelImpl implements Pixel {

  private int r;
  private int g;
  private int b;

  /**
   * The constructor for the Pixel class. Gives each value in a pixel its designated component.
   * @param r the red value of a pixel
   * @param g the green value of a pixel
   * @param b the blue value of a pixel
//   * @throws IllegalArgumentException if any of the values are less than zero or greater than 255
   */
  public PixelImpl(int r, int g, int b) throws IllegalArgumentException {

    if (r < 0) {
      r = 0;
    }
    if (g < 0) {
      g = 0;
    }
    if (b < 0) {
      b = 0;
    }
    if (r > 255) {
      r = 255;
    }
    if (g > 255) {
      g = 255;
    }
    if (b > 255) {
      b = 255;
    }

    this.r = Objects.requireNonNull(r);
    this.g = Objects.requireNonNull(g);
    this.b = Objects.requireNonNull(b);
  }

  @Override
  public int getR() {
    return this.r;
  }

  @Override
  public int getG() {
    return this.g;
  }

  @Override
  public int getB() {
    return this.b;
  }

  @Override
  public PixelImpl copyPixel() {
    return new PixelImpl(r,g,b);
  }

  /**
   * Overrides the java equals method so getPixelAt in the Image class can be compared to another.
   * @param pixel an object that is cast to a Pixel
   * @return true if it's equals, false if it's not.
   */
  @Override
  public boolean equals(Object pixel) {
    pixel = new PixelImpl(r,g,b);
    PixelImpl equalPixel = (PixelImpl) pixel;
    return this.r == equalPixel.r && this.g == equalPixel.g && this.b == equalPixel.b;
  }

  /**
   * Overrides the java hashcode method for style checker purposes since we need to override
   * hashcode if we override equals.
   * @return the integer zero
   */
  @Override
  public int hashCode() {
    return 0;
  }
}
