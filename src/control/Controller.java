package control;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;


import javax.imageio.ImageIO;

import model.IImage;
import model.ImageImpl;
import model.ImageModel;
import model.commands.ColorTransformation;
import model.commands.Flip;
import model.commands.GrayScale;
import model.commands.ImageProcessingCommands;
import model.commands.Brightness;
import model.commands.Quality;
import view.ImageView;


/**
 * This is the controller for our Image Processor. It takes in an input and runs commands based on
 * that input and outputs data.
 */
public class Controller implements IController {

  private final ImageModel model;
  private final ImageView view;
  private final Readable input;
  private String type;
  private boolean validCommand;
  Map<String, Function<Scanner, ImageProcessingCommands>> knownCommands;

  /**
   * Constructor for the controller class. A controller object contains all aspects of the
   * program and carries out operations on images
   *
   * @param model a list of saved images in a hashmap
   * @param view  an image view
   * @param input a user input
   * @param type  a type of command to run
   * @throws IllegalArgumentException if any of the parameters are null
   */
  public Controller(ImageModel model, ImageView view, Readable input, String type)
          throws IllegalArgumentException {

    if (model == null || view == null || input == null || type == null) {
      throw new IllegalArgumentException("One or more of the "
              + "parameters for the constructor is null");
    }

    this.model = model;
    this.view = view;
    this.input = input;
    this.type = type;
    this.validCommand = true;
  }

  /**
   * This method is the helper for load which calls the load Util based on the type of file.
   *
   * @param s     a scanner for the user input
   * @param image an image that is going to be loaded
   */
  private void loadController(Scanner s, ImageImpl image) {
    String location = s.next();
    String name = s.next();
    if (location.endsWith(".ppm")) {
      image = PPMUtil.loadPPM(location);
    } else {
      if (location.endsWith(".bmp") || location.endsWith(".jpg") || location.endsWith(".png")) {
        image = ImageUtil.loadImage(location);
      }
    }
    if (image == null) {
      writeMessage("load unsuccessful: file type is not a ppm or file name does not exist\n");
      return;
    }
    model.saveName(name, image);
    writeMessage(name + " was successfully loaded\n");
  }


  /**
   * This is the helper for save when the file type is a PPM.
   *
   * @param image      an image that is going to be saved
   * @param desired    a string represents the desired name of a file
   * @param appendable input stream
   */
  private void savePPMController(ImageImpl image, String desired,
                                 Appendable appendable) {
    try {
      // creates the contents of the file
      PPMUtil.savePPM(appendable, image);
      // make the file and append the tag '.ppm'
      File file = new File(desired + ".ppm");
      if (!file.exists()) {
        try {
          // creates the new file and writes appendable inside
          file.createNewFile();
          FileWriter myWriter = new FileWriter(file);
          myWriter.write(appendable.toString());
          myWriter.close();
        } catch (IOException e) {
          writeMessage("an exception occurred");
          validCommand = false;
        }
      } else {
        writeMessage("same file name exists save failed\n");
        validCommand = false;
      }
    } catch (IllegalStateException e) {
      writeMessage("save was unsuccessful. try again\n");
      validCommand = false;
    }
  }

  /**
   * This is the helper for save when the file is of the type jpg, png, or bmp.
   *
   * @param image   an image to be saved
   * @param desired the filename an image will be saved as
   */
  private void saveOtherFileController(ImageImpl image, String desired) {
    {
      BufferedImage bufferedImage = ImageUtil.saveImage(image);
      File file = new File(desired + "." + type);
      if (!file.exists()) {
        try {
          file.createNewFile();
          ImageIO.write(bufferedImage, type, file);
        } catch (IOException e) {
          throw new IllegalStateException("an exception occurred");
        }
      } else {
        writeMessage("same file name exists save failed\n");
        validCommand = false;
      }
    }
  }

  /**
   * This is a helper for flip which carries out a flip edit to an ImageImpl.
   *
   * @param s a scanner that holds the user input
   */
  private void flipController(Scanner s) {
    type = s.next();
    String name = s.next();
    String desired = s.next();
    IImage image = model.getValue(name);
    ImageProcessingCommands cmd = new Flip(type);
    model.saveName(desired, cmd.process(image));
    writeMessage(name + " was successfully " + type + "ly flipped\n");
  }

  /**
   * This is a helper for grayscale which carries out a grayscale edit to an ImageImpl.
   *
   * @param s a scanner that holds the user input
   */
  private void grayscaleController(Scanner s) {
    type = s.next();
    String name = s.next();
    String desired = s.next();
    IImage image = model.getValue(name);
    ImageProcessingCommands cmd = new GrayScale(type);
    model.saveName(desired, cmd.process(image));
    writeMessage(name + " was successfully processed using " + type + " grayscale\n");
  }

  //TODO javadoc saves image to hashmap

  /**
   * This is a helper for brightness which carries out a brighten/darken edit to an ImageImpl.
   *
   * @param s a scanner that holds the user input
   */
  private void brightnessController(Scanner s) {
    int increment = s.nextInt();
    String name = s.next();
    String desired = s.next();
    IImage image = model.getValue(name);
    ImageProcessingCommands cmd = new Brightness(increment);
    model.saveName(desired, cmd.process(image));
    if (increment > 0) {
      writeMessage(name + " was successfully brightened by " + increment + "\n");
    }
    if (increment < 0) {
      writeMessage(name + " was successfully darkened by " + increment + "\n");
    }
  }

  /**
   * This is a helper for quality which carries out a quality edit to an ImageImpl.
   *
   * @param s a scanner that holds the user input
   */
  private void qualityController(Scanner s) {
    type = s.next();
    String name = s.next();
    String desired = s.next();
    IImage image = model.getValue(name);
    ImageProcessingCommands cmd = new Quality(type);
    model.saveName(desired, cmd.process(image));
    writeMessage(name + " was successfully blurred/sharpened\n");
  }

  /**
   * This is a helper for filter which carries out a filter edit to an ImageImpl.
   *
   * @param s a scanner that holds the user input
   */
  private void filterController(Scanner s) {
    type = s.next();
    String name = s.next();
    String desired = s.next();
    IImage image = model.getValue(name);
    ImageProcessingCommands cmd = new ColorTransformation(type);
    model.saveName(desired, cmd.process(image));
    writeMessage(name + " was successfully filtered\n");
  }

  //We want to make the run method shorter, but the time is limited. Vido said we need to make
  //all the helper methods into function objects.

  @Override
  public void run() {
    Scanner s = new Scanner(input);
    ImageImpl image = null;
    String name;
    String desired;
    while (s.hasNext()) {
      String in = s.next();
      Appendable appendable = new StringBuilder();
      switch (in) {
        case "q":
        case "Q":
        case "quit":
        case "Quit":
          writeMessage("you quit the program.\n");
          return;
        case "load":
          loadController(s, image);
          break;
        case "save":
          name = s.next();
          desired = s.next();
          image = (ImageImpl) model.getValue(name);
          type = s.next();
          if (type.equals("ppm")) {
            savePPMController(image, desired, appendable);
          } else {
            if (type.equals("jpg") || type.equals("png") || type.equals("bmp")) {
              saveOtherFileController(image, desired);
            } else {
              writeMessage("file type not supported\n");
              break;
            }
          }
          if (validCommand) {
            writeMessage(name + " was successfully saved to " + desired + "." + type + "\n");
          }
          break;
        case "flip":
          flipController(s);
          break;
        case "grayscale":
          grayscaleController(s);
          break;
        case "brightness":
          brightnessController(s);
          break;
        case "quality":
          qualityController(s);
          break;
        case "filter":
          filterController(s);
          break;
        default:
          writeMessage("Unknown command. Try again\n");
          break;
      }
    }
  }


  /**
   * Catches the IO exception in renderMessage, so it never occurs anywhere else in my code.
   *
   * @param message the message that is to be concatenated
   * @throws IllegalStateException instead of IO exception if the transmission fails
   */
  private void writeMessage(String message) throws IllegalStateException {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("transmission of the message to the "
              + "provided data destination fails");
    }
  }
}