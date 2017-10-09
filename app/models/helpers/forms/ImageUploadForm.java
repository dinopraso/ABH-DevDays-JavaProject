package models.helpers.forms;

import models.BaseModel;

import java.util.UUID;

/**
 * The type Image upload form.
 */
public class ImageUploadForm extends BaseModel {

    private UUID restaurantId;
    private String imageType;
    private String extension;

    /**
     * Instantiates a new Image upload form.
     */
    public ImageUploadForm() {}

    /**
     * Gets restaurant id.
     *
     * @return the restaurant id
     */
    public UUID getRestaurantId() { return restaurantId; }

    /**
     * Sets restaurant id.
     *
     * @param restaurantId the restaurant id
     */
    public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }

    /**
     * Gets image type.
     *
     * @return the image type
     */
    public String getImageType() { return imageType; }

    /**
     * Sets image type.
     *
     * @param imageType the image type
     */
    public void setImageType(String imageType) { this.imageType = imageType; }

    /**
     * Gets extension.
     *
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets extension.
     *
     * @param extension the extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }
}
