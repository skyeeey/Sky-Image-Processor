package view;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertThrows;


/**
 * The ImageViewTests makes sure renderMessage and the View controller throw t
 * heir respective exceptions.
 */
public class IImageViewTests {

  @Test (expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    ImageView view = new ImageViewImpl(null);
  }

  @Test
  public void testRenderMessageIOException() {
    ImageView view = new ImageViewImpl(new MockAppendable());
    assertThrows(IOException.class, () -> view.renderMessage("i hope this is correct"));
  }
}
