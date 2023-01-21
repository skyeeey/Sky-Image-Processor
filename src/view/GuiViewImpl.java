package view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import model.IImage;
import model.ImageImpl;

/**
 * GuiViewImpl class represents the implementation of GUI view,
 * contains methods that form the GUI View of our image processor.
 */
public class GuiViewImpl extends JFrame implements GuiView {

  private JPanel mainPanel;
  private ImagePanel imagePanel;
  private JPanel topPanel;
  private JPanel rightPanel;
  private JButton blur;
  private JPanel comboBoxPanel;
  private JButton sharpen;
  private JButton horizontal;
  private JButton vertical;
  private JButton sepia;
  private JButton luma;
  private JTextArea console;
  private JButton load;
  private JButton save;
  private JButton brighten;
  private JButton darken;
  private final Dimension screenSize;
  private JComboBox<String> grayscaleTypes;
  private JPanel histogramInfo;
  private JButton redHistogram;
  private JButton greenHistogram;
  private JButton blueHistogram;
  private JButton intensityHistogram;
  private HistogramPanel histogramPanel;

  /**
   * The constructor for GuiViewImpl initializes the view and designated variables
   * that will be needed in the methods. Also, it calls all the methods that creating
   * arguments of the view.
   */
  public GuiViewImpl() {
    super("Image Processor");

    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setSize(new Dimension(screenSize.width, screenSize.height));
    this.setResizable(false);
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit out of the application

    makeMainPanel();
    makeTopPanel();
    makeRightPanel();
    makeGrayscaleOptions();
    makeBrightness();
    makeQualityButtons();
    makeFlipButtons();
    makeFilterButtons();
    makeConsole();
    makeImagePanel();
    makeScrollPanes();

    this.getContentPane().add(BorderLayout.PAGE_START, topPanel);
    this.getContentPane().add(BorderLayout.CENTER, mainPanel);
    this.getContentPane().add(BorderLayout.LINE_END, rightPanel);

    pack();
  }

  // makes the mainPanel that contains the imagePanel and the console
  private void makeMainPanel() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setPreferredSize(new Dimension(screenSize.width * 3 / 4,
            screenSize.height * 21 / 22));
    mainPanel.setBackground(new Color(0x123456));
  }

  // makes the welcome message and the save/load buttons
  private void makeTopPanel() {
    topPanel = new JPanel();
    topPanel.setLayout(new FlowLayout());
    topPanel.setPreferredSize(new Dimension(screenSize.width, screenSize.height / 22));
    topPanel.setBackground(Color.darkGray);

    JTextArea welcome = new JTextArea("Welcome to Our Image Processor!");
    welcome.setEditable(false);
    welcome.setFont(new Font("hi", Font.PLAIN, 16));
    welcome.setForeground(Color.white);
    welcome.setBackground(Color.darkGray);
    topPanel.add(welcome);

    save = new JButton("save");
    save.setActionCommand("save");
    topPanel.add(save);
    load = new JButton("load");
    load.setActionCommand("load");
    topPanel.add(load);

  }

  //makes the right panel that contains the combobox and the histogram
  private void makeRightPanel() {
    rightPanel = new JPanel();
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
    rightPanel.setPreferredSize(new Dimension(screenSize.width / 4,
            screenSize.height));

    comboBoxPanel = new JPanel();
    comboBoxPanel.setBorder(BorderFactory.createTitledBorder("Apply A Process"));
    rightPanel.add(comboBoxPanel);

    histogramInfo = new JPanel();
    histogramInfo.setLayout(new FlowLayout());
    histogramInfo.setBorder(BorderFactory.createTitledBorder("Image Histogram"));
    histogramInfo.setPreferredSize(new Dimension(screenSize.width / 6, screenSize.height / 8));

    makeHistogramButtons();

    rightPanel.add(histogramInfo);
  }

  //makes the histogram buttons that contains r g b and rgb
  private void makeHistogramButtons() {
    redHistogram = new JButton("r");
    redHistogram.setActionCommand("red histogram");
    histogramInfo.add(redHistogram);

    greenHistogram = new JButton("g");
    greenHistogram.setActionCommand("green histogram");
    histogramInfo.add(greenHistogram);

    blueHistogram = new JButton("b");
    blueHistogram.setActionCommand("blue histogram");
    histogramInfo.add(blueHistogram);

    intensityHistogram = new JButton("rgb");
    intensityHistogram.setActionCommand("intensity histogram");
    histogramInfo.add(intensityHistogram);
  }

  //make grayscale drop-downs and adds to the comboBox
  private void makeGrayscaleOptions() {
    grayscaleTypes = new JComboBox<>();
    grayscaleTypes.setBorder(BorderFactory.createTitledBorder("Grayscales"));
    comboBoxPanel.add(grayscaleTypes);
    String[] options = {"Select a grayscale", "red", "green", "blue", "value", "intensity", "luma"};
    for (String option : options) {
      grayscaleTypes.addItem(option);
    }
    grayscaleTypes.setActionCommand("grayscales");
  }

  //make brightness buttons and adds to the comboBox
  private void makeBrightness() {
    JPanel brightPanel = new JPanel();
    brightPanel.setBorder(BorderFactory.createTitledBorder("Brightness"));
    comboBoxPanel.add(brightPanel);

    brighten = new JButton("Brighten");
    brighten.setActionCommand("brighten");
    brightPanel.add(brighten);

    darken = new JButton("Darken");
    darken.setActionCommand("darken");
    brightPanel.add(darken);


    JLabel brightnessDisplay = new JLabel("");
    brightPanel.add(brightnessDisplay);
  }

  //make quality button and adds to the comboBox
  private void makeQualityButtons() {
    JPanel quality = new JPanel();
    quality.setBorder(BorderFactory.createTitledBorder("Quality"));
    comboBoxPanel.add(quality);

    quality.setLayout(new FlowLayout());
    sharpen = new JButton("Sharpen");
    sharpen.setActionCommand("sharpen");
    quality.add(sharpen);

    blur = new JButton("Blur");
    blur.setActionCommand("blur");
    quality.add(blur);
  }

  //make flip buttons and adds to the comboBox
  private void makeFlipButtons() {
    JPanel flip = new JPanel();
    flip.setBorder(BorderFactory.createTitledBorder("Image Flip"));
    comboBoxPanel.add(flip);

    flip.setLayout(new FlowLayout());
    horizontal = new JButton("Horizontal");
    horizontal.setActionCommand("horizontal");
    flip.add(horizontal);

    vertical = new JButton("Vertical");
    vertical.setActionCommand("vertical");
    flip.add(vertical);
  }

  //make filter buttons and adds to the comboBox
  private void makeFilterButtons() {
    JPanel filter = new JPanel();
    filter.setBorder(BorderFactory.createTitledBorder("Filters"));
    comboBoxPanel.add(filter);

    filter.setLayout(new FlowLayout());
    sepia = new JButton("Sepia");
    sepia.setActionCommand("sepia");
    filter.add(sepia);
    luma = new JButton("Luma");
    luma.setActionCommand("luma");
    filter.add(luma);
  }

  //make console. removes the old one and adds a new one
  private void makeConsole() {
    console = new JTextArea();
    console.setText("Please load an image");
    console.setPreferredSize(new Dimension((screenSize.width * 3 / 4),
            (screenSize.height / 9)));
    console.setForeground(Color.white);
    console.setFont(new Font("MV Boli", Font.PLAIN, 20));
    console.setEditable(false);
    console.setBackground(Color.black);
    mainPanel.add(console, BorderLayout.PAGE_END);
  }

  //make image panel. removes the old one and adds a new one
  private void makeImagePanel() {
    imagePanel = new ImagePanel();
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(imagePanel, BorderLayout.CENTER);
  }

  //make scroll panes. removes the old one and adds a new one
  private void makeScrollPanes() {
    JScrollPane imageScrollVertical = new JScrollPane(imagePanel);
    imageScrollVertical.setBackground(new Color(0x123456));
    imageScrollVertical.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    mainPanel.add(imageScrollVertical, BorderLayout.LINE_END);

    //TODO possible fix at PAGE_END?
    JScrollPane imageScrollHorizontal = new JScrollPane(imagePanel);
    imageScrollHorizontal.setBackground(new Color(0x123456));
    imageScrollHorizontal.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    mainPanel.add(imageScrollHorizontal, BorderLayout.PAGE_START);
  }

  /**
   * This method sets the image panel for our image processor view.
   *
   * @param image the image that will be drawn in the new ImagePanel
   */
  public void setImagePanel(IImage image) {
    mainPanel.remove(imagePanel);
    imagePanel = new ImagePanel(image);
    mainPanel.add(imagePanel, BorderLayout.CENTER);
    refresh();
  }

  /**
   * This method sets the histogram panel for our image processor view.
   *
   * @param image the image that is represented in our histogram
   * @param type  the type of histogram that will be printed
   */
  public void setHistogramPanel(ImageImpl image, String type) {
    if (histogramPanel != null) {
      histogramInfo.remove(histogramPanel);
    }
    histogramPanel = new HistogramPanel(image, type);
    histogramInfo.add(histogramPanel);
    setImagePanel(image);
    refresh();
  }

  /**
   * This method gets the selected grayscale from a list of grayscale methods.
   *
   * @return return the selected grayscale chose by the user.
   */

  public String getSelectedGrayscale() {
    return (String) grayscaleTypes.getSelectedItem();
  }

  @Override
  public void setVisibility(Boolean b) {
    super.setVisible(b);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    console.setText(message);
  }

  @Override
  public void setListener(ActionListener listener) {
    save.addActionListener(listener);
    load.addActionListener(listener);
    blur.addActionListener(listener);
    sharpen.addActionListener(listener);
    horizontal.addActionListener(listener);
    vertical.addActionListener(listener);
    brighten.addActionListener(listener);
    darken.addActionListener(listener);
    sepia.addActionListener(listener);
    luma.addActionListener(listener);
    grayscaleTypes.addActionListener(listener);
    redHistogram.addActionListener(listener);
    greenHistogram.addActionListener(listener);
    blueHistogram.addActionListener(listener);
    intensityHistogram.addActionListener(listener);
  }
}
