package models.tables;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * The type Restaurant table.
 */
@Entity
@Table(name = "restaurant_table")
public class RestaurantTable extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "restaurant_id")
	private UUID restaurantId;

	@Column(name = "number_of_chairs")
	private Integer numberOfChairs;

	/**
	 * Instantiates a new Restaurant table.
	 */
	public RestaurantTable() { }

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
	public UUID getRestaurantId() {  return restaurantId; }

	/**
	 * Sets restaurant id.
	 *
	 * @param restaurantId the restaurant id
	 */
	public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }

	/**
	 * Gets number of chairs.
	 *
	 * @return the number of chairs
	 */
	public Integer getNumberOfChairs() { return numberOfChairs; }

	/**
	 * Sets number of chairs.
	 *
	 * @param numberOfChairs the number of chairs
	 */
	public void setNumberOfChairs(Integer numberOfChairs) { this.numberOfChairs = numberOfChairs; }
}