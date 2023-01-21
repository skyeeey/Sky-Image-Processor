package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.IImage;
import model.ImageImpl;
import model.ImageModel;
import model.commands.Brightness;
import model.commands.ColorTransformation;
import model.commands.Flip;
import model.commands.GrayScale;
import model.commands.ImageProcessingCommands;
import model.commands.Quality;
import view.GuiView;

/**
 * GuiControllerImpl contains the methods that get information that the user inputs 
 * from the view and alters images accordingly.
 */
public class GuiControllerImpl implements ActionListener, GuiController {
  private final GuiView view;
  private final ImageModel model;
  private int imageNum;
  private IImage image;
  private ImageProcessingCommands cmd;
  private final JFileChooser fChooser;
  private final FileNameExtensionFilter filter;

  /**
   * Constructor for the GUI controller class. A controller object contains all aspects of the
   * program and carries out operations on images
   *
   * @param model the ImageModel that will be referenced
   * @param view  the GuiViewImpl that will show what gets altered
   */
  public GuiControllerImpl(ImageModel model, GuiView view) {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    view.setListener(this);
    imageNum = 0;
    fChooser = new JFileChooser(".");
    filter = new FileNameExtensionFilter("JPG/PNG/PPM/BMP files",
            "jpg", "gif", "png", "ppm", "bmp");
  }

  // saves an image to a file specified by the user
  private void saveHelper() {
    if (imageNum == 0) {
      view.renderMessage("You must load an image first");
      return;
    }
    int retvalue = fChooser.showSaveDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      String type = f.toString().substring(f.toString().length() - 3);
      if (!f.exists()) {
        try {
          f.createNewFile();
          image = Objects.requireNonNull(model.getValue(String.valueOf(imageNum)));
          if (type.equals("png") || type.equals("jpg") || type.equals("bmp")) {
            ImageIO.write(ImageUtil.saveImage(image), "png", f);
          } else {
            if (type.equals("ppm")) {
              FileWriter myWriter = new FileWriter(f);
              Appendable appendable = new StringBuilder();
              PPMUtil.savePPM(appendable, image);
              myWriter.write(appendable.toString());
              myWriter.close();
            } else {
              view.renderMessage("extension must be either jpg, png, ppm or bmp");
            }
          }
          view.renderMessage("saved to " + f);
        } catch (IOException error) {
          view.renderMessage("something went wrong");
        }
      } else {
        view.renderMessage("filepath already exists");
      }
      view.setImagePanel(image);
    }
  }

  // loads an image and creates a new ImagePanel with it
  private void loadHelper() {
    fChooser.setFileFilter(filter);
    int retValue = fChooser.showOpenDialog(null);
    if (retValue == JFileChooser.APPROVE_OPTION) {
      File f = fChooser.getSelectedFile();
      if (f.toString().endsWith(".ppm")) {
        image = PPMUtil.loadPPM(String.valueOf(f));
        view.setImagePanel(image);
        model.saveName(String.valueOf(imageNum + 1), image);
      }
      if (f.toString().endsWith(".png")
              || f.toString().endsWith(".bmp")
              || f.toString().endsWith(".jpg")) {
        image = ImageUtil.loadImage(String.valueOf(f));
        view.setImagePanel(image);
        model.saveName(String.valueOf(imageNum + 1), image);
      }
      view.renderMessage("loaded" + f);
      imageNum++;
    }
  }

  // sharpens an image and creates a new ImagePanel with it
  private void sharpenHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new Quality("sharpen");
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("image was sharpened");
    imageNum++;
  }

  // blurs an image and creates a new ImagePanel with it
  private void blurHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new Quality("blur");
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("image was blurred");
    imageNum++;
  }

  // flips an image horizontally and creates a new ImagePanel with it
  private void horizontalHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new Flip("horizontal");
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("image was flipped horizontally");
    imageNum++;
  }

  // flips an image vertically and creates a new ImagePanel with it
  private void verticalHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new Flip("vertical");
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("image was flipped vertically");
    imageNum++;
  }

  // retrieves the increment to be brightened by and passes it to brightnessHelper
  private void brightenHelper(String input) {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    int increment = 0;
    try {
      increment = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      view.renderMessage("enter a valid integer in the text box");
      view.refresh();
      return;
    }
    brightnessHelper(increment);
    view.renderMessage("image was brightened by " + increment);
  }

  // retrieves the increment to be darkened by and passes it to brightnessHelper
  private void darkenHelper(String input) {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    int increment = 0;
    try {
      increment = Integer.parseInt(input) * -1;
    } catch (NumberFormatException e) {
      view.renderMessage("enter a valid integer in the textbox");
      view.refresh();
      return;
    }
    brightnessHelper(increment);
    view.renderMessage("image was darkened by " + increment);
  }

  // helper to perform the action of brighten/darken an image
  private void brightnessHelper(int increment) {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new Brightness(increment);
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    imageNum++;
  }

  // helper to perform the action of adding a sepia filter on an image
  private void sepiaHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new ColorTransformation("sepia");
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("the filter sepia was applied");
    imageNum++;
  }

  // helper to perform the action of adding a luma filter on an image
  private void lumaHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new ColorTransformation("luma");
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("the filter luma was applied");
    imageNum++;
  }

  // helper to perform the action of add a grayscale on an image
  private void grayscaleHelper() {
    if (imageNum == 0) {
      view.renderMessage("load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    cmd = new GrayScale(view.getSelectedGrayscale());
    model.saveName(String.valueOf(imageNum + 1), cmd.process(image));
    view.setImagePanel(cmd.process(image));
    view.renderMessage("the image was grayscaled using "
            + view.getSelectedGrayscale() + " grayscale");
  }

  // helper to perform the action of showing red histogram of an image
  private void redHistogramHelper() {
    if (imageNum == 0) {
      view.renderMessage("You must load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    String type = "red";
    view.setHistogramPanel((ImageImpl) image, type);
    view.renderMessage("showing red histogram");
  }

  // helper to perform the action of showing green histogram of an image
  private void greenHistogramHelper() {
    if (imageNum == 0) {
      view.renderMessage("You must load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    String type = "green";
    view.setHistogramPanel((ImageImpl) image, type);
    view.renderMessage("showing green histogram");
  }

  // helper to perform the action of showing blue histogram of an image
  private void blueHistogramHelper() {
    if (imageNum == 0) {
      view.renderMessage("You must load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    String type = "blue";
    view.setHistogramPanel((ImageImpl) image, type);
    view.renderMessage("showing blue histogram");
  }

  // helper to perform the action of changing the intensity of an image
  private void intensityHistogramHelper() {
    if (imageNum == 0) {
      view.renderMessage("You must load an image first");
      return;
    }
    image = model.getValue(String.valueOf(imageNum));
    String type = "intensity";
    view.setHistogramPanel((ImageImpl) image, type);
    view.renderMessage("showing intensity histogram");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "save":
        saveHelper();
        break;
      case "load":
        loadHelper();
        break;
      case "sharpen":
        sharpenHelper();
        break;
      case "blur":
        blurHelper();
        break;
      case "horizontal":
        horizontalHelper();
        break;
      case "vertical":
        verticalHelper();
        break;
      case "brighten":
        String brightenInput = JOptionPane.showInputDialog("Please enter your value");
        brightenHelper(brightenInput);
        break;
      case "darken":
        String darkenInput = JOptionPane.showInputDialog("Please enter your value");
        darkenHelper(darkenInput);
        break;
      case "sepia":
        sepiaHelper();
        break;
      case "luma":
        lumaHelper();
        break;
      case "grayscales":
        grayscaleHelper();
        break;
      case "red histogram":
        redHistogramHelper();
        break;
      case "green histogram":
        greenHistogramHelper();
        break;
      case "blue histogram":
        blueHistogramHelper();
        break;
      case "intensity histogram":
        intensityHistogramHelper();
        break;
      default:
        break;
    }
  }

  @Override
  public void run() {
    view.setVisibility(true);
  }

  @Override
  public void incrementImageNum() {
    imageNum++;
  }
}