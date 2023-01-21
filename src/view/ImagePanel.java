package view;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import control.ImageUtil;
import model.IImage;

/**
 * This class represents a panel built for the image loaded by user, which is going to be shown on
 * GUI view.
 */
public class ImagePanel extends JPanel {

  private final Dimension screenSize;
  private final BufferedImage bufferedImage;

  /**
   * Constructor for the image panel that shown on GUI view (without taking in an image).
   * Initialize all the desginated variables that form a image panel.
   */
  public ImagePanel() {
    bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(new Dimension((screenSize.width * 3 / 4), (screenSize.height)));
  }

  /**
   * Constructor for the image panel that shown on GUI view (taking in an image).
   * Initialize all the desginate variables that form a image panel.
   */
  public ImagePanel(IImage image) {
    bufferedImage = ImageUtil.saveImage(image);
    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(new Dimension((screenSize.width * 3 / 4), (screenSize.height)));
  }

  @Override
  protected void paintComponent(Graphics g) {
    g.drawImage(bufferedImage, (screenSize.width / 2 * 3 / 4) - (bufferedImage.getWidth() / 2),
            (screenSize.height / 2 * 9 / 10) - (bufferedImage.getHeight() / 2), null);

    JLabel icon = new JLabel(new ImageIcon(bufferedImage));
    this.add(icon);
  }
}