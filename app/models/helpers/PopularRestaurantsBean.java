package models.helpers;

import java.util.UUID;

/**
 * The type Popular restaurants bean.
 */
public class PopularRestaurantsBean {
	private UUID restaurantId;
	private Long tableCount;

	/**
	 * Instantiates a new Popular restaurants bean.
	 */
	public PopularRestaurantsBean() { }

	/**
	 * Gets restaurant id.
	 *
	 * @return the restaurant id
	 */
	public UUID getRestaurantId() { return this.restaurantId; }

	/**
	 * Sets restaurant id.
	 *
	 * @param restaurantId the restaurant id
	 */
	public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }

	/**
	 * Gets table count.
	 *
	 * @return the table count
	 */
	public Long getTableCount() { return this.tableCount; }

	/**
	 * Sets table count.
	 *
	 * @param tableCount the table count
	 */
	public void setTableCount(Long tableCount) { this.tableCount = tableCount; }
}
