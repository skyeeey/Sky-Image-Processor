package gui;


import java.awt.event.ActionListener;
import java.io.IOException;

import model.IImage;
import model.ImageImpl;
import view.GuiView;

/**
 * Represents a fake view class for testing.
 */
public class FakeView implements GuiView {

  private final Appendable appendable;

  private int num;

  /**
   * Constructs the fake view class, override the method that refresh.
   * @param appendable input stream
   */
  public FakeView(Appendable appendable) {
    this.appendable = appendable;
    this.num = 0;
  }

  @Override
  public void refresh() {
    num++;
  }

  @Override
  public void renderMessage(String message) {
    try {
      appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("error");
    }
  }

  @Override
  public void setListener(ActionListener listener) {
    num++;
  }

  @Override
  public void setImagePanel(IImage image) {
    try {
      appendable.append(" setImagePanel ");
    } catch (IOException e) {
      throw new IllegalStateException("error");
    }
  }

  @Override
  public void setHistogramPanel(ImageImpl image, String type) {
    try {
      appendable.append(" setHistogramPanel ");
    } catch (IOException e) {
      throw new IllegalStateException("error");
    }
  }

  @Override
  public String getSelectedGrayscale() {
    return null;
  }

  @Override
  public void setVisibility(Boolean b) {
    num++;
  }
}
