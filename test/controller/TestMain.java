package controller;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import control.Controller;
import model.ImageModelImpl;
import view.ImageView;
import view.ImageViewImpl;

/**
 * This is the main class that is used for testing the Script gets parsed correctly.
 */
public final class TestMain {

  /**
   * This is the main method that runs the controller. The input method is System.in and the output
   * is the console.
   *
   * @param args the user inputs
   * @throws IllegalArgumentException if the file is unreadable or not found
   */
  public static void main(String[] args) {
    ImageModelImpl model = new ImageModelImpl();
    Appendable appendable = new StringBuilder();
    ImageView view = new ImageViewImpl(appendable);


    // java -jar hw5.jar -file script

    if (args.length == 0) {
      Controller controller = new Controller(model, view, new InputStreamReader(System.in), "");
      controller.run();
    }

    if (args[0].equals("-file")) {
      Readable readable = getContents(args[1]);
      Controller controller = new Controller(model, view, readable, "");
      controller.run();
      System.out.println(appendable);
    }
  }

  /**
   * GetContents uses the FileReader class to collect the contents from a file and put them into
   * a readable to be read later.
   *
   * @param fileName the filename to be parsed
   * @return the contents of a file in the form of a Readable
   * @throws IllegalArgumentException if the file is unreadable or not found
   */
  public static Readable getContents(String fileName) throws IllegalArgumentException {

    try {
      return new FileReader(fileName);
    } catch (IOException e) {
      throw new IllegalArgumentException("file is unreadable or not found");
    }
  }
}
