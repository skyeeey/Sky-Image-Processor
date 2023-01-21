package model;

/**
 * This is the interface ImageModel. It contains the methods that access and modify the hashmap
 * in the ImageModelImpl class.
 */
public interface ImageModel {

  /**
   * Saves the name of the image into an array list that can be accessed later.
   *
   * @param desiredName the name the image will be referred to
   * @param image       the image being saved
   */
  void saveName(String desiredName, IImage image);

  /**
   * Gets the value(Image) from the existingImages hashmap.
   *
   * @param imageName the value's Image in the hashmap
   * @return the image corresponding to the value
   */
  IImage getValue(String imageName);
}


