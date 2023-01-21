package model;

/**
 * This is the Pixel interface. It contains methods that help access values in the PixelImpl class.
 */
public interface Pixel {

  /**
   * Get the red value of the pixel.
   * @return the red value of the pixel
   */
  int getR();

  /**
   * Get the green value of the pixel.
   * @return the green value of the pixel
   */
  int getG();

  /**
   * Get the blue value of the pixel.
   * @return the blue value of the pixel
   */
  int getB();

  /**
   * Creates a new Pixel that is a copy of the original one it is called on. The fields inside the
   * copy are equal to the original one.
   * @return the copy of the original pixel
   */
  PixelImpl copyPixel();
}
