package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test class for the Pixel class constructor and methods.
 */
public class PixelTests {

  Pixel pixel = new PixelImpl(1, 2, 3);


  @Test
  public void testGetR() {

    assertEquals(1, pixel.getR());
  }


  @Test
  public void testGetG() {

    assertEquals(2, pixel.getG());
  }

  @Test
  public void testGetB() {

    assertEquals(3, pixel.getB());
  }

  @Test
  public void testCopyPixel() {
    PixelImpl copy = new PixelImpl(1,2, 3);
    assertEquals(copy, pixel.copyPixel());
  }

  // equals method is overridden from a java interface. it is used in our ImageClassTests
}