package models.tables;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * The type Restaurant photo.
 */
@Entity
@Table(name = "restaurant_photo")
public class RestaurantPhoto extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "restaurant_id")
	private UUID restaurantId;

	@Column(name = "photo_path")
	private String path;

	/**
	 * Instantiates a new Restaurant photo.
	 */
	public RestaurantPhoto() { }

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public UUID getId() { return id; }

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(UUID id) { this.id = id; }

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
	 * Gets path.
	 *
	 * @return the path
	 */
	public String getPath() { return path; }

	/**
	 * Sets path.
	 *
	 * @param path the path
	 */
	public void setPath(String path) { this.path = path; }
}