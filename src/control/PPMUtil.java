package control;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


import model.IImage;
import model.ImageImpl;
import model.Pixel;
import model.PixelImpl;

/**
 * This class contains utility methods to read or save to a PPM image from file and
 * simply print its contents.
 */
public class PPMUtil {

  /**
   * Load an image from the specified path and refer to it in the program by the given image name.
   *
   * @param ppmFileLocation the location of the image in the folder
   */
  public static ImageImpl loadPPM(String ppmFileLocation) {

    return PPMUtil.readPPM(ppmFileLocation);
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param fileName the path of the file.
   */
  public static ImageImpl readPPM(String fileName) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      // file name does not exist
      return null;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      // file type unsupported
      return null;
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    PixelImpl[][] pixels = new PixelImpl[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        // ADD PIXELS TO IMAGE
        pixels[row][col] = new PixelImpl(r, g, b);
      }
    }
    return new ImageImpl(width, height, maxValue, pixels);
  }

  /**
   * Saves an Image to a specified path in the format .ppm.
   *
   * @param appendable the input stream
   * @param image      the image that is going to be saved
   * @throws IllegalStateException if the file name already exists somewhere
   * @throws IllegalStateException if the file location is unspecified
   * @throws IllegalStateException if the image does not exist
   */
  public static void savePPM(Appendable appendable, IImage image) throws IllegalStateException {
    //put in file
    try {

      appendable.append("P3\n");
      appendable.append(image.getWidth() + " " + image.getHeight() + "\n");
      appendable.append(image.getMaxValue() + "\n");
      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          Pixel pixel = image.getPixelAt(row, col);
          appendable.append(pixel.getR() + "\n" + pixel.getG() + "\n" + pixel.getB() + "\n");
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("file location unspecified");
    }
  }
}

