package models.helpers;


/**
 * The type Popular location.
 */
public class PopularLocation {
	private Object city;
	private Object numberOfRestaurants;
	private Boolean isPlural;

	/**
	 * Instantiates a new Popular location.
	 *
	 * @param popularLocation the popular location
	 */
	public PopularLocation(Object[] popularLocation) {
		this.city = popularLocation[0];
		this.numberOfRestaurants = popularLocation[1];
	}

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	public Object getCity() { return city; }

	/**
	 * Sets city.
	 *
	 * @param city the city
	 */
	public void setCity(Object city) { this.city = city; }

	/**
	 * Gets number of restaurants.
	 *
	 * @return the number of restaurants
	 */
	public Long getNumberOfRestaurants() {
		return Long.parseLong(this.numberOfRestaurants.toString());
	}

	/**
	 * Gets is plural.
	 *
	 * @return the is plural
	 */
	public Boolean getIsPlural() {
		return ((Long) this.numberOfRestaurants) > 1;
	}

	/**
	 * Sets plural.
	 *
	 * @param plural the plural
	 */
	public void setPlural(Boolean plural) { this.isPlural = plural; }
}
