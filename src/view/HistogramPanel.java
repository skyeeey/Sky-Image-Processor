package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;

import model.ImageImpl;

/**
 * This class represents a panel built for histogram, which is going to be shown on our GUI view.
 */
public class HistogramPanel extends JPanel {

  private final ImageImpl image;
  private final String type;
  private double maxValue;
  private final Dimension screenSize;

  /**
   * Constructor for the histogram panel that shown on GUI view. Intializes all the designated
   * variables that form a histogram panel.
   * @param image an image that represent a histogram.
   * @param type there are 3 types of histograms, red/green/blue.
   */
  public HistogramPanel(ImageImpl image, String type) {
    this.image = Objects.requireNonNull(image);
    this.type = Objects.requireNonNull(type);

    this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setPreferredSize(new Dimension(256, screenSize.height / 3));

    this.maxValue = 0;
    if (type.equals("red")) {
      this.setForeground(new Color(182, 38, 71));
    }
    if (type.equals("green")) {
      this.setForeground(new Color(39, 161, 73));
    }
    if (type.equals("blue")) {
      this.setForeground(new Color(45,150,195));
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    Map<Integer,Integer> values = image.getHistogramValues(type);
    int y1;
    int y2;

    for (int x = 0; x < values.size(); x++) {
      int current = values.get(x);
      if (maxValue < current) {
        maxValue = current;
      }
    }

    for (int i = 0; i < values.size() - 1; i++) {
      double scale = (double) 300 / maxValue;
      y1 = values.get(i);
      y2 = values.get(i + 1);
      g.drawLine(i, (screenSize.height / 3) - (int) (scale * y1),
              i + 1,(screenSize.height / 3) - (int) (scale * y2));
    }
  }
}
