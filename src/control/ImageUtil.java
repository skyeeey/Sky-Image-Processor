package control;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.IImage;
import model.ImageImpl;
import model.PixelImpl;


/**
 * This class contains utility methods to read or save to a jpg, png, or bmp image from file and
 * simply print its contents .
 */
public class ImageUtil {

  /**
   * Loads an image with one of four extensions: jpg, jpeg, png, bmp.
   * @param filename the path and name of a file
   * @return an ImageImpl with the loaded image's data
   */
  public static ImageImpl loadImage(String filename) {

    try {
      BufferedImage image = ImageIO.read(new File(filename));
      int width = image.getWidth();
      int height = image.getHeight();
      PixelImpl[][] pixels = new PixelImpl[height][width];
      for (int row = 0; row < height; row++) {
        for (int col = 0; col < width; col++) {
          int color = image.getRGB(col, row);
          Color c = new Color(color);
          pixels[row][col] = new PixelImpl(c.getRed(), c.getGreen(), c.getBlue());
        }
      }
      return new ImageImpl(width, height, 255, pixels);
    } catch (IOException e) {
      throw new IllegalStateException("something went wrong with the load");
    }
  }

  /**
   * Observes fields from an ImageImpl in our program and converts them into a BufferedImage to be
   * saved to a file.
   *
   * @param image Takes in a specific Image from our program to be saved.
   * @return a BufferedImage
   */
  public static BufferedImage saveImage(IImage image) {

    BufferedImage bufferImage = new BufferedImage(image.getWidth(),
            image.getHeight(), BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < image.getHeight(); row++) {
      for (int col = 0; col < image.getWidth(); col++) {
        // (get pixels at image).getR
        int r = image.getPixelAt(row, col).getR();
        int g = image.getPixelAt(row, col).getG();
        int b = image.getPixelAt(row, col).getB();

        Color color = new Color(r,g,b);

        bufferImage.setRGB(col, row, color.getRGB());
      }
    }
    return bufferImage;
  }
}