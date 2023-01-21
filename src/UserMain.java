import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import control.Controller;
import model.ImageModelImpl;
import view.ImageViewImpl;

/**
 * This is the main class where the user can either run the JAR file and start the program, or
 * enter inputs through System.In.
 */
public final class UserMain {

  /**
   * This is the main method that runs the controller. The input method is System.in and the output
   * is the console.
   * @param args the user inputs
   * @throws IllegalArgumentException if the file is unreadable or not found
   */
  public static void main(String[] args) throws IllegalArgumentException {
    ImageModelImpl model = new ImageModelImpl();
    ImageViewImpl view = new ImageViewImpl();


    // java -jar hw5.jar -file script

    if (args.length == 0) {
      Controller controller = new Controller(model, view, new InputStreamReader(System.in), "");
      controller.run();
    } else {
      if (args[0].equals("-file")) {
        try {
          Readable out = new FileReader(args[1]);
          Controller controller = new Controller(model, view, out, "");
          controller.run();
        } catch (IOException e) {
          throw new IllegalArgumentException("file is unreadable or not found");
        }
      }
    }
  }
}
