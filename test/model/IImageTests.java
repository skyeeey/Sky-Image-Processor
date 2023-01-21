package model;

import org.junit.Test;

import control.PPMUtil;
import model.commands.Brightness;
import model.commands.ColorTransformation;
import model.commands.Flip;
import model.commands.GrayScale;
import model.commands.ImageProcessingCommands;
import model.commands.Quality;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Image class constructors and command function objects.
 */
public class IImageTests {

  private final IImage image = (PPMUtil.readPPM("test/test.ppm"));
  private final Pixel zeroZero = image.getPixelAt(0,0);
  private final Pixel zeroOne = image.getPixelAt(0,1);
  private final Pixel oneZero = image.getPixelAt(1,0);
  private final Pixel oneOne = image.getPixelAt(1,1);
  private final ImageModel model = new ImageModelImpl();

  ImageProcessingCommands cmd;



  @Test
  public void testImageDefaultPixelValues() {

    // for Pixel(1,100,2)
    assertEquals(1, zeroZero.getR());
    assertEquals(2, zeroZero.getG());
    assertEquals(3, zeroZero.getB());

    // for Pixel(100,3,4)
    assertEquals(4, zeroOne.getR());
    assertEquals(5, zeroOne.getG());
    assertEquals(6, zeroOne.getB());

    // for Pixel(5,6,100)
    assertEquals(7, oneZero.getR());
    assertEquals(8, oneZero.getG());
    assertEquals(9, oneZero.getB());

    // for (7,8,9)
    assertEquals(10, oneOne.getR());
    assertEquals(11, oneOne.getG());
    assertEquals(12, oneOne.getB());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImageNullConstructor() {
    ImageImpl nullImage = new ImageImpl(2, 2, 255, null);

    assertEquals( 2, nullImage.getWidth());
  }

  @Test
  public void testImageGetPixelAt() {
    Pixel zeroZero = image.getPixelAt(0,0);
    Pixel fakeZeroZero = new PixelImpl(1,2,3);

    Pixel zeroOne = image.getPixelAt(0,1);
    Pixel fakeZeroOne = new PixelImpl(4,5,6);

    Pixel oneZero = image.getPixelAt(1,0);
    Pixel fakeOneZero = new PixelImpl(7,8,9);

    Pixel oneOne = image.getPixelAt(1,1);
    Pixel fakeOneOne = new PixelImpl(10,11,12);

    assertEquals(zeroZero, fakeZeroZero);
    assertEquals(zeroOne, fakeZeroOne);
    assertEquals(oneZero, fakeOneZero);
    assertEquals(oneOne, fakeOneOne);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImageGetPixelAtOutOfBounds() {

    image.getPixelAt(3,3);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testImageGetPixelAtNegative() {
    image.getPixelAt(-1,-1);

    assertEquals(new PixelImpl(3, 3, 3), image.getPixelAt(-1, -1));
  }

  @Test
  public void testGetWidth() {
    assertEquals(2, image.getWidth());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetWidthZero() {
    ImageImpl imageInvalid = new ImageImpl(0, 2, 255, new PixelImpl[2][2]);

    // tests width 10 to show that the constructor is throwing the error
    assertEquals(10, imageInvalid.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(2, image.getHeight());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetHeightZero() {
    ImageImpl imageInvalid = new ImageImpl(2, 0, 255, new PixelImpl[2][2]);

    // tests height 10 to show that the constructor is throwing the error
    assertEquals(10, imageInvalid.getHeight());
  }

  @Test
  public void testGetMaxValue() {
    assertEquals(255, image.getMaxValue());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetMaxValueZero() {
    ImageImpl imageInvalid = new ImageImpl(2, 2, 0, new PixelImpl[2][2]);

    // tests max value at 10 to show that the constructor is throwing the error
    assertEquals(10, imageInvalid.getMaxValue());
  }


  @Test
  public void testFlipHorizontal() {
    model.saveName("hi",image);

    cmd = new Flip("horizontal");

    IImage flipped = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(flipped.getPixelAt(0,0), zeroOne);
    assertEquals(flipped.getPixelAt(0,1), zeroZero);
    assertEquals(flipped.getPixelAt(1,0), oneOne);
    assertEquals(flipped.getPixelAt(1,1), oneZero);

  }

  @Test
  public void testFlipVertical() {
    model.saveName("hi",image);

    cmd = new Flip("vertical");

    IImage flipped = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    Pixel flipZZ = flipped.getPixelAt(0, 0);
    assertEquals(flipZZ, oneZero);

    Pixel flipZO = flipped.getPixelAt(0, 1);
    assertEquals(flipZO, oneOne);

    Pixel flipOZ = flipped.getPixelAt(1, 0);
    assertEquals(flipOZ, zeroZero);

    Pixel flipOO = flipped.getPixelAt(1, 1);
    assertEquals(flipOO, zeroOne);
  }

  @Test
  public void testFlipVerticalTwice() {
    model.saveName("hi",image);

    cmd = new Flip("vertical");

    IImage flipped = cmd.process(image);

    IImage normal = cmd.process(flipped);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(normal.getPixelAt(0, 0), zeroZero);

    assertEquals(normal.getPixelAt(0, 1), zeroOne);

    assertEquals(normal.getPixelAt(1, 0), oneZero);

    assertEquals(normal.getPixelAt(1, 1), oneOne);
  }

  @Test
  public void testFlipHorizontalTwice() {
    model.saveName("hi",image);

    cmd = new Flip("horizontal");

    IImage flipped = cmd.process(image);

    IImage normal = cmd.process(flipped);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(normal.getPixelAt(0, 0), zeroZero);

    assertEquals(normal.getPixelAt(0, 1), zeroOne);

    assertEquals(normal.getPixelAt(1, 0), oneZero);

    assertEquals(normal.getPixelAt(1, 1), oneOne);
  }

  @Test
  public void testFlipVerticalThenHorizontal() {
    model.saveName("hi",image);

    cmd = new Flip("vertical");

    IImage flipped = cmd.process(image);

    cmd = new Flip("horizontal");

    IImage flipped2 = cmd.process(flipped);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(flipped2.getPixelAt(0, 0), oneOne);

    assertEquals(flipped2.getPixelAt(0, 1), oneZero);

    assertEquals(flipped2.getPixelAt(1, 0), zeroOne);

    assertEquals(flipped2.getPixelAt(1, 1), zeroZero);
  }

  @Test
  public void testRedGrayScale() {
    model.saveName("hi",image);

    cmd = new GrayScale("red");

    IImage gray = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(1,1,1), gray.getPixelAt(0,0));
    assertEquals(new PixelImpl(4,4,4), gray.getPixelAt(0,1));
    assertEquals(new PixelImpl(7,7,7), gray.getPixelAt(1,0));
    assertEquals(new PixelImpl(10,10,10), gray.getPixelAt(1,1));
  }

  @Test
  public void testGreenGrayScale() {
    model.saveName("hi",image);

    cmd = new GrayScale("green");

    IImage gray = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(2,2,2), gray.getPixelAt(0, 0));
    assertEquals(new PixelImpl(5,5,5), gray.getPixelAt(0, 1));
    assertEquals(new PixelImpl(8,8,8), gray.getPixelAt(1, 0));
    assertEquals(new PixelImpl(11,11,11), gray.getPixelAt(1, 1));
  }

  @Test
  public void testBlueGrayScale() {
    model.saveName("hi",image);

    cmd = new GrayScale("blue");

    IImage gray = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(3,3,3), gray.getPixelAt(0, 0));
    assertEquals(new PixelImpl(6,6,6), gray.getPixelAt(0, 1));
    assertEquals(new PixelImpl(9,9,9), gray.getPixelAt(1, 0));
    assertEquals(new PixelImpl(12,12,12), gray.getPixelAt(1, 1));
  }

  @Test
  public void testBrightenPositiveValid() {

    model.saveName("hi",image);

    cmd = new Brightness(50);

    IImage brighten = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(51,52,53), brighten.getPixelAt(0, 0));
    assertEquals(new PixelImpl(54,55,56), brighten.getPixelAt(0, 1));
    assertEquals(new PixelImpl(57,58,59), brighten.getPixelAt(1, 0));
    assertEquals(new PixelImpl(60,61,62), brighten.getPixelAt(1, 1));
  }

  @Test
  public void testBrightenPositiveInvalid() {

    model.saveName("hi",image);

    cmd = new Brightness(4000);

    IImage brighten = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(255,255,255), brighten.getPixelAt(0, 0));
    assertEquals(new PixelImpl(255,255,255), brighten.getPixelAt(0, 1));
    assertEquals(new PixelImpl(255,255,255), brighten.getPixelAt(1, 0));
    assertEquals(new PixelImpl(255,255,255), brighten.getPixelAt(1, 1));
  }


  @Test
  public void testDarkenNegativeValid() {
    model.saveName("hi",image);

    cmd = new Brightness(-50);

    IImage brighten = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(0, 0));
    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(0, 1));
    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(1, 0));
    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(1, 1));
  }

  @Test
  public void testDarkenNegativeInvalid() {
    model.saveName("hi",image);

    cmd = new Brightness(-4000);

    IImage brighten = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(0, 0));
    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(0, 1));
    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(1, 0));
    assertEquals(new PixelImpl(0,0,0), brighten.getPixelAt(1, 1));
  }

  @Test
  public void testDarkenNegativeHalfInvalid() {
    model.saveName("hi",image);

    cmd = new Brightness(-2);

    IImage brighten = cmd.process(image);

    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(0,0,1), brighten.getPixelAt(0, 0));
    assertEquals(new PixelImpl(2,3,4), brighten.getPixelAt(0, 1));
    assertEquals(new PixelImpl(5,6,7), brighten.getPixelAt(1, 0));
    assertEquals(new PixelImpl(8,9,10), brighten.getPixelAt(1, 1));
  }

  @Test
  public void testValueGrayScale() {
    model.saveName("hi",image);

    cmd = new GrayScale("value");

    IImage gray = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(3,3,3), gray.getPixelAt(0, 0));
    assertEquals(new PixelImpl(6,6,6), gray.getPixelAt(0, 1));
    assertEquals(new PixelImpl(9,9,9), gray.getPixelAt(1, 0));
    assertEquals(new PixelImpl(12,12,12), gray.getPixelAt(1, 1));
  }

  @Test
  public void testIntensityGrayScale() {
    model.saveName("hi",image);

    cmd = new GrayScale("intensity");

    IImage gray = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(2,2,2), gray.getPixelAt(0, 0));
    assertEquals(new PixelImpl(5,5,5), gray.getPixelAt(0, 1));
    assertEquals(new PixelImpl(8,8,8), gray.getPixelAt(1, 0));
    assertEquals(new PixelImpl(11,11,11), gray.getPixelAt(1, 1));
  }

  @Test
  public void testLumaGrayScale() {
    model.saveName("hi",image);

    cmd = new GrayScale("luma");

    IImage gray = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(2,2,2), gray.getPixelAt(0, 0));
    assertEquals(new PixelImpl(5,5,5), gray.getPixelAt(0, 1));
    assertEquals(new PixelImpl(8,8,8), gray.getPixelAt(1, 0));
    assertEquals(new PixelImpl(11,11,11), gray.getPixelAt(1, 1));
  }

  @Test
  public void testSharpen() {
    model.saveName("hi",image);

    cmd = new Quality("sharpen");

    IImage quality = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(2,2,2), quality.getPixelAt(0, 0));
    assertEquals(new PixelImpl(5,5,5), quality.getPixelAt(0, 1));
    assertEquals(new PixelImpl(8,8,8), quality.getPixelAt(1, 0));
    assertEquals(new PixelImpl(11,11,11), quality.getPixelAt(1, 1));
  }

  @Test
  public void testBlur() {
    model.saveName("hi",image);

    cmd = new Quality("blur");

    IImage quality = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(2,2,2), quality.getPixelAt(0, 0));
    assertEquals(new PixelImpl(5,5,5), quality.getPixelAt(0, 1));
    assertEquals(new PixelImpl(8,8,8), quality.getPixelAt(1, 0));
    assertEquals(new PixelImpl(11,11,11), quality.getPixelAt(1, 1));
  }

  @Test
  public void testFilterSepia() {
    model.saveName("hi",image);

    cmd = new ColorTransformation("sepia");

    IImage filter = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(0,0).getR() * 0.393),
            (int) (image.getPixelAt(0,0).getG() * 0.769),
            (int) (image.getPixelAt(0,0).getB() * 0.189)), filter.getPixelAt(0, 0));
    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(0,1).getR() * 0.393),
            (int) (image.getPixelAt(0,1).getG() * 0.769),
            (int) (image.getPixelAt(0,1).getB() * 0.189)), filter.getPixelAt(0, 1));
    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(1,0).getR() * 0.393),
            (int) (image.getPixelAt(1,0).getG() * 0.769),
            (int) (image.getPixelAt(1,0).getB() * 0.189)), filter.getPixelAt(1, 0));
    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(1,1).getR() * 0.393),
            (int) (image.getPixelAt(1,1).getG() * 0.769),
            (int) (image.getPixelAt(1,1).getB() * 0.189)), filter.getPixelAt(1, 1));
  }

  @Test
  public void testFilterLuma() {
    model.saveName("hi",image);

    cmd = new ColorTransformation("luma");

    IImage filter = cmd.process(image);


    // shows that the original image is not mutating
    assertEquals(new PixelImpl(1,2,3), image.getPixelAt(0, 0));
    assertEquals(new PixelImpl(4,5,6), image.getPixelAt(0, 1));
    assertEquals(new PixelImpl(7,8,9), image.getPixelAt(1, 0));
    assertEquals(new PixelImpl(10,11,12), image.getPixelAt(1, 1));

    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(0,0).getR() * 0.2126),
            (int) (image.getPixelAt(0,0).getG() * 0.7152),
            (int) (image.getPixelAt(0,0).getB() * 0.0722)), filter.getPixelAt(0, 0));
    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(0,1).getR() * 0.2126),
            (int) (image.getPixelAt(0,1).getG() * 0.7152),
            (int) (image.getPixelAt(0,1).getB() * 0.0722)), filter.getPixelAt(0, 1));
    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(1,0).getR() * 0.2126),
            (int) (image.getPixelAt(1,0).getG() * 0.7152),
            (int) (image.getPixelAt(1,0).getB() * 0.0722)), filter.getPixelAt(1, 0));
    assertEquals(new PixelImpl(
            (int) (image.getPixelAt(1,1).getR() * 0.2126),
            (int) (image.getPixelAt(1,1).getG() * 0.7152),
            (int) (image.getPixelAt(1,1).getB() * 0.0722)), filter.getPixelAt(1, 1));
  }

  @Test
  public void testSavePPM() {

    Appendable appendable = new StringBuilder();

    PPMUtil.savePPM(appendable, image);

    assertEquals("P3\n"
         + "2 2\n"
         + "255\n"
         + "1\n"
         + "2\n"
         + "3\n"
         + "4\n"
         + "5\n"
         + "6\n"
         + "7\n"
         + "8\n"
         + "9\n"
         + "10\n"
         + "11\n"
         + "12\n", appendable.toString());
  }
}