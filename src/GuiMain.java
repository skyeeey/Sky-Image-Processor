import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import control.Controller;
import control.GuiControllerImpl;
import model.ImageModelImpl;
import view.GuiViewImpl;
import view.ImageViewImpl;

/**
 * This main class allows the GUI to be visible. It creates a new window that represents the GUI.
 */
public class GuiMain {

  /**
   * This is the main method in our main class. It takes in three different command line arguments
   * paths for different ways to run our image processor.
   * @param args the input
   */
  public static void main(String[] args) {
    ImageModelImpl model = new ImageModelImpl();

    if (args.length == 0) {
      GuiViewImpl hi = new GuiViewImpl();
      GuiControllerImpl controller = new GuiControllerImpl(model, hi);
      controller.run();
    } else {
      if (args[0].equals("-file")) {
        try {
          ImageViewImpl view = new ImageViewImpl();
          Readable out = new FileReader(args[1]);
          Controller controller = new Controller(model, view, out, "");
          controller.run();
        } catch (IOException e) {
          throw new IllegalArgumentException("file is unreadable or not found");
        }
      } else {
        if (args[0].equals("-text")) {
          ImageViewImpl view = new ImageViewImpl();
          Controller controller = new Controller(model, view, new InputStreamReader(System.in), "");
          controller.run();
        }
      }
    }
  }
}
