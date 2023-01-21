package model;

import org.junit.Test;
import control.PPMUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Test class for the ImageModel class methods.
 */
public class IImageModelTests {

  private ImageModel model = new ImageModelImpl();
  private ImageImpl image = (PPMUtil.readPPM("images/test.ppm"));

  private ImageImpl koala = (PPMUtil.readPPM("images/koala.ppm"));

  @Test
  public void testEmptyHashmapGetValue() {

    IImage fail = model.getValue("testImage");

    // shows that the fail image does not get the correct value because the value has not been saved
    // prior to the getter call it
    assertNotEquals(image, fail);


    model.saveName("testImage", image);
    IImage pass = model.getValue("testImage");
    //this should pass because the image is saved into the hashmap
    assertEquals(image, pass);

  }

  @Test
  public void testGetValueFromHashmap() {

    model.saveName("testImage", image);

    assertEquals(image, model.getValue("testImage"));

    model.saveName("koalaImage", koala);

    assertEquals(image, model.getValue("testImage"));
    assertEquals(koala, model.getValue("koalaImage"));
  }

}
