package model;

import java.util.HashMap;
import java.util.Map;


/**
 * This class represents the hashmap of saved edits done by the user with their corresponding name.
 */
public class ImageModelImpl implements ImageModel {

  private final Map<String, IImage> existingImages;

  /**
   * This is the constructor for ImageModel. An ImageModel object stores Images
   * in a hashmap that can be called from at any time.
   */
  public ImageModelImpl() {
    this.existingImages = new HashMap<>();
  }

  @Override
  public void saveName(String desiredName, IImage image)  {
    this.existingImages.put(desiredName, image);
  }

  @Override
  public IImage getValue(String imageName) {
    return this.existingImages.get(imageName);
  }
}
