package view;


import java.io.IOException;

/**
 * This is the implementation class of ImageView. It contains the methods defined in the ImageView
 * interface.
 */
public class ImageViewImpl implements ImageView {

  private Appendable appendable;

  /**
   * Constructor for the ImageView class. The output for this constructor is
   * the console.
   */
  public ImageViewImpl() {

    this.appendable = System.out;

  }

  /**
   * Constructor for the ImageView class. The output for this constructor is unspecified.
   *
   * @throws IllegalArgumentException if appendable are null
   */
  public ImageViewImpl(Appendable appendable) {

    if (appendable == null) {
      throw new IllegalArgumentException("Appendable is null");
    }

    this.appendable = appendable;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.appendable.append(message);
  }
}
