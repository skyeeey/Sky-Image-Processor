package gui;

import org.junit.Test;

import control.GuiControllerImpl;
import control.PPMUtil;
import model.IImage;
import model.ImageImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.GuiView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the controller input using a fake View class that just appends
 * the messages to a StringBuilder.
 */
public class GuiControllerTest {

  @Test
  public void testLoadIsNotFirst() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    IImage image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("0", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);

    controller.actionPerformed(new MockActionEvent("horizontal"));

    controller.run();

    assertEquals("load an image first", appendable.toString());
  }

  @Test
  public void testFlipHorizontal() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("horizontal"));

    controller.run();

    assertEquals("setImagePanel image was flipped horizontally", appendable.toString());
  }

  @Test
  public void testFlipVertical() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("vertical"));

    controller.run();

    assertEquals("setImagePanel image was flipped vertically", appendable.toString());
  }

  @Test
  public void testBlur() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("blur"));

    controller.run();

    assertEquals(" setImagePanel image was blurred", appendable.toString());
  }

  @Test
  public void testSharpen() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("blur"));

    controller.run();

    assertEquals(" setImagePanel image was sharpened", appendable.toString());
  }

  @Test
  public void testSepia() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("sepia filter"));

    controller.run();

    assertEquals(" setImagePanel the filter sepia was applied", appendable.toString());
  }

  @Test
  public void testLumaFilter() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("luma filter"));

    controller.run();

    assertEquals(" setImagePanel the filter luma was applied", appendable.toString());
  }

  @Test
  public void testBrighten() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();

    //enter the value 10
    controller.actionPerformed(new MockActionEvent("brighten"));

    controller.run();

    assertEquals(" setImagePanel image was brightened by 10", appendable.toString());
  }

  @Test
  public void testBrightenInputNotInteger() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();

    //enter anything that is not an integer
    controller.actionPerformed(new MockActionEvent("brighten"));

    controller.run();

    assertEquals("enter a valid integer in the text box", appendable.toString());
  }

  @Test
  public void testDarken() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();

    //enter the value 10
    controller.actionPerformed(new MockActionEvent("darken"));

    controller.run();

    assertEquals(" setImagePanel image was darkened by 10", appendable.toString());
  }

  @Test
  public void testRenderMessage() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();

    view.renderMessage("hello");

    controller.run();

    assertEquals("hello", appendable.toString());
  }

  @Test
  public void testRedHistogram() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("red histogram"));

    controller.run();

    assertEquals("  setHistogramPanel showing red histogram", appendable.toString());
  }

  @Test
  public void testGreenHistogram() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("green histogram"));

    controller.run();

    assertEquals("  setHistogramPanel showing green histogram", appendable.toString());
  }

  @Test
  public void testBlueHistogram() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("blue histogram"));

    controller.run();

    assertEquals("  setHistogramPanel showing blue histogram", appendable.toString());
  }

  @Test
  public void testIntensityHistogram() {
    Appendable appendable = new StringBuilder();
    GuiView view = new FakeView(appendable);
    ImageModel model = new ImageModelImpl();
    ImageImpl image = PPMUtil.readPPM("images/koala.ppm");
    model.saveName("1", image);

    GuiControllerImpl controller = new GuiControllerImpl(model, view);
    controller.incrementImageNum();
    controller.actionPerformed(new MockActionEvent("intensity histogram"));

    controller.run();

    assertEquals("  setHistogramPanel showing intensity histogram", appendable.toString());
  }
}
