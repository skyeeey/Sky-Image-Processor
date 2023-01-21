package controller;

import org.junit.Test;

import java.io.StringReader;

import control.Controller;
import control.IController;
import control.PPMUtil;
import model.IImage;
import model.ImageModelImpl;
import model.Pixel;
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This class tests if controller runs the correct methods rather than
 * test if the methods work themselves.
 */
public class ControllerTests {

  private final IImage image = (PPMUtil.readPPM("test/test.ppm"));
  private final ImageModelImpl model = new ImageModelImpl();
  private final Appendable appendable = new StringBuilder();
  private final ImageView view = new ImageViewImpl(appendable);

  @Test (expected = IllegalArgumentException.class)
  public void testNullModel() {

    Readable readable = new StringReader("");
    IController controller = new Controller(null, view, readable, "");

    // this should fail, but it does not because of the exception thrown when constructing
    // the controller
    controller.run();
    assertEquals("hi was successfully loaded\n",appendable.toString());
  }


  @Test (expected = IllegalArgumentException.class)
  public void testNullView() {

    Readable readable = new StringReader("load test/test.ppm hi");
    IController controller = new Controller(model, null, readable, "");

    // this should fail, but it does not because of the exception thrown when constructing
    // the controller
    controller.run();
    assertEquals("hi was successfully loaded\n",appendable.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullInput() {

    IController controller = new Controller(model,view,null, "");

    // this should fail, but it does not because of the exception thrown when constructing
    // the controller
    controller.run();
    assertEquals("hi was successfully loaded\n",appendable.toString());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNullType() {

    Readable readable = new StringReader("");
    IController controller = new Controller(model, view, readable, null);

    // this should fail, but it does not because of the exception thrown when constructing
    // the controller
    controller.run();
    assertEquals("hi was successfully loaded\n",appendable.toString());
  }

  @Test
  public void testQWithLoadAfter() {
    Readable readable = new StringReader("Q load test/test.ppm hi");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }

  @Test
  public void testQuitWithLoadAfter() {
    Readable readable = new StringReader("Quit load test/test.ppm hi");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }

  @Test
  public void testLowercaseQWithLoadAfter() {
    Readable readable = new StringReader("q load test/test.ppm hi");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }

  @Test
  public void testLowercaseQuitWithLoadAfter() {
    Readable readable = new StringReader("quit load test/test.ppm hi");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }

  @Test
  public void testUppercaseQuit() {
    Readable readable = new StringReader("Quit");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }


  @Test
  public void testLowercaseQuit() {
    Readable readable = new StringReader("quit");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }

  @Test
  public void testUppercaseQ() {
    Readable readable = new StringReader("Q");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }


  @Test
  public void testLowercaseQ() {
    Readable readable = new StringReader("q");
    IController controller = new Controller(model,view,readable,"");

    controller.run();
    assertEquals("you quit the program.\n",appendable.toString());

    // hi is not put into the hashmap
    assertNotEquals(image, model.getValue("hi"));
  }

  @Test
  public void testLoadPPM() {
    Readable readable = new StringReader("load test/test.ppm hi q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
                 + "you quit the program.\n",
            appendable.toString());
    IImage loaded = model.getValue("hi");
    assertEquals(2, loaded.getHeight());
  }

  @Test
  public void testLoadJPG() {
    Readable readable = new StringReader("load test/test.jpg hi q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
                    + "you quit the program.\n",
            appendable.toString());
    IImage loaded = model.getValue("hi");
    assertEquals(2, loaded.getHeight());
  }

  @Test
  public void testLoadPNG() {
    Readable readable = new StringReader("load test/test.png hi q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
                    + "you quit the program.\n",
            appendable.toString());
    IImage loaded = model.getValue("hi");
    assertEquals(2, loaded.getHeight());
  }

  @Test
  public void testLoadBMP() {
    Readable readable = new StringReader("load test/test.bmp hi q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
                    + "you quit the program.\n",
            appendable.toString());
    IImage loaded = model.getValue("hi");
    assertEquals(2, loaded.getHeight());
  }

  @Test
  public void testRedGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi grayscale red hi red q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully processed using red grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("red");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(1,newPixel.getR());
    assertEquals(1,newPixel.getG());
    assertEquals(1,newPixel.getB());
  }

  @Test
  public void testGreenGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi grayscale green hi green q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully processed using green grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("green");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(2,newPixel.getR());
    assertEquals(2,newPixel.getG());
    assertEquals(2,newPixel.getB());
  }


  @Test
  public void testBlueGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi grayscale blue hi blue q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully processed using blue grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("blue");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(3,newPixel.getR());
    assertEquals(3,newPixel.getG());
    assertEquals(3,newPixel.getB());
  }

  @Test
  public void testIntensityGrayscale() {
    Readable readable =
            new StringReader("load test/test.ppm hi grayscale intensity hi intensity q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully processed using intensity grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("intensity");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(2,newPixel.getR());
    assertEquals(2,newPixel.getG());
    assertEquals(2,newPixel.getB());
  }

  @Test
  public void testValueGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi grayscale value hi value q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully processed using value grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("value");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(3,newPixel.getR());
    assertEquals(3,newPixel.getG());
    assertEquals(3,newPixel.getB());
  }

  @Test
  public void testLumaGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi grayscale luma hi luma q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully processed using luma grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("luma");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(2,newPixel.getR());
    assertEquals(2,newPixel.getG());
    assertEquals(2,newPixel.getB());
  }

  @Test
  public void testBrighten() {
    Readable readable = new StringReader("load test/test.ppm hi brightness 200 hi bright q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
         + "hi was successfully brightened by 200\n"
         + "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("bright");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(201,newPixel.getR());
    assertEquals(202,newPixel.getG());
    assertEquals(203,newPixel.getB());
  }

  @Test
  public void testDarken() {
    Readable readable = new StringReader("load test/test.ppm hi brightness -200 hi dark q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
         + "hi was successfully darkened by -200\n"
         + "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("dark");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(0,newPixel.getR());
    assertEquals(0,newPixel.getG());
    assertEquals(0,newPixel.getB());
  }

  @Test
  public void testSharpen() {
    Readable readable = new StringReader("load test/test.ppm hi quality sharpen hi sharp q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully blurred/sharpened\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("sharp");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(6,newPixel.getR());
    assertEquals(8,newPixel.getG());
    assertEquals(10,newPixel.getB());
  }

  @Test
  public void testBlur() {
    Readable readable = new StringReader("load test/test.ppm hi quality blur hi blur q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n"
            + "hi was successfully blurred/sharpened\n"
            + "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("blur");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(2,newPixel.getR());
    assertEquals(3,newPixel.getG());
    assertEquals(3,newPixel.getB());
  }

  @Test
  public void testFilterSepia() {
    Readable readable = new StringReader("load test/test.ppm hi filter sepia hi sepia q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully filtered\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("sepia");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(1,newPixel.getR());
    assertEquals(1,newPixel.getG());
    assertEquals(1,newPixel.getB());
  }

  @Test
  public void testFilterLuma() {
    Readable readable = new StringReader("load test/test.ppm hi filter luma hi luma q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully filtered\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("luma");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(1,newPixel.getR());
    assertEquals(1,newPixel.getG());
    assertEquals(1,newPixel.getB());
  }

  @Test
  public void testFlipHorizontalGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi flip horizontal hi flipped"
         + " grayscale red flipped final q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully horizontally flipped\n" +
            "flipped was successfully processed using red grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("final");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(4,newPixel.getR());
    assertEquals(4,newPixel.getG());
    assertEquals(4,newPixel.getB());
  }

  @Test
  public void testBrightnessGrayscale() {
    Readable readable = new StringReader("load test/test.ppm hi brightness 10 hi bright"
         + " grayscale red bright final q");

    IController controller = new Controller(model,view, readable,"");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "hi was successfully brightened by 10\n" +
            "bright was successfully processed using red grayscale\n" +
            "you quit the program.\n",appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("final");

    Pixel originalPixel = original.getPixelAt(0,0);
    Pixel newPixel = newImage.getPixelAt(0,0);

    assertEquals(1,originalPixel.getR());
    assertEquals(2,originalPixel.getG());
    assertEquals(3,originalPixel.getB());

    assertEquals(11,newPixel.getR());
    assertEquals(11,newPixel.getG());
    assertEquals(11,newPixel.getB());

  }


  @Test
  public void testBrightnessFlipVertical() {
    Readable readable = new StringReader("load test/test.ppm hi brightness 10 hi bright"
            + " flip vertical bright final q");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("hi was successfully loaded\n"
            + "hi was successfully brightened by 10\n"
            + "bright was successfully vertically flipped\n"
            + "you quit the program.\n", appendable.toString());

    IImage original = model.getValue("hi");
    IImage newImage = model.getValue("final");

    Pixel originalPixel = original.getPixelAt(0, 0);
    Pixel newPixel = newImage.getPixelAt(0, 0);

    assertEquals(1, originalPixel.getR());
    assertEquals(2, originalPixel.getG());
    assertEquals(3, originalPixel.getB());

    assertEquals(17, newPixel.getR());
    assertEquals(18, newPixel.getG());
    assertEquals(19, newPixel.getB());

  }
  
  @Test
  public void testLoadOntoExisting() {
    Readable readable = new StringReader("load test/test.ppm hi load test/watermelon.ppm hi q");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("hi was successfully loaded\n"
            + "hi was successfully loaded\n"
            + "you quit the program.\n", appendable.toString());

    IImage image = model.getValue("hi");

    // shows that the width of the saved image "hi" is overwritten when loaded twice
    assertEquals(16,image.getWidth());
  }

  @Test
  public void testLoadFileNotFound() {
    Readable readable = new StringReader("load failure.ppm hi");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("load unsuccessful: file type is not a ppm or "
            + "file name does not exist\n", appendable.toString());
  }

  @Test
  public void testLoadFileNotPPM() {
    Readable readable = new StringReader("load failure hi");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("load unsuccessful: file type is not a ppm or file name "
            + "does not exist\n", appendable.toString());
  }

  @Test
  public void testInputIsNotACommand() {
    Readable readable = new StringReader("failure");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("Unknown command. Try again\n", appendable.toString());
  }

  @Test
  public void testSaveImageLocationAlreadyExists() {
    Readable readable = new StringReader("load test/test.ppm hi"
            + " save hi test/test ppm");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "same file name exists save failed\n", appendable.toString());
  }

  @Test
  public void testSaveImageExtensionInvalid() {
    Readable readable = new StringReader("load test/test.ppm hi"
            + " save hi test/test yu");

    IController controller = new Controller(model, view, readable, "");

    controller.run();
    assertEquals("hi was successfully loaded\n" +
            "file type not supported\n", appendable.toString());
  }

  @Test
  public void testScript() {

    String[] mainInput = new String[]{"-file", "res/script"};

    Readable out = TestMain.getContents("res/script");

    TestMain.main(mainInput);

    Controller controller = new Controller(model,view,TestMain.getContents("res/script"), "");

    controller.run();


    // the saves should fail since the script is run on line 559 already creating a file with
    // the same name
    assertEquals("default was successfully loaded\n" +
            "default was successfully processed using red grayscale\n" +
            "default was successfully processed using green grayscale\n" +
            "default was successfully processed using blue grayscale\n" +
            "default was successfully processed using value grayscale\n" +
            "default was successfully processed using intensity grayscale\n" +
            "default was successfully processed using luma grayscale\n" +
            "default was successfully brightened by 100\n" +
            "default was successfully darkened by -100\n" +
            "default was successfully horizontally flipped\n" +
            "default was successfully vertically flipped\n" +
            "default was successfully blurred/sharpened\n" +
            "default was successfully blurred/sharpened\n" +
            "default was successfully filtered\n" +
            "default was successfully filtered\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n" +
            "same file name exists save failed\n", appendable.toString());

  }

}


