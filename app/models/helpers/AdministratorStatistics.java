package models.helpers;

/**
 * The type Administrator statistics.
 */
public class AdministratorStatistics {

	Long numberOfRestaurants;
	Long numberOfCities;
	Long numberOfUsers;
	Long numberOfCuisines;

	private AdministratorStatistics() {}

	/**
	 * Gets object.
	 *
	 * @return the object
	 */
	public static AdministratorStatistics getObject() {
		return new AdministratorStatistics();
	}

	/**
	 * Gets number of restaurants.
	 *
	 * @return the number of restaurants
	 */
	public Long getNumberOfRestaurants() { return this.numberOfRestaurants; }

	/**
	 * Sets number of restaurants.
	 *
	 * @param numberOfRestaurants the number of restaurants
	 * @return the number of restaurants
	 */
	public AdministratorStatistics setNumberOfRestaurants(Long numberOfRestaurants) { 
		this.numberOfRestaurants = numberOfRestaurants; 
		return this;
		}

	/**
	 * Gets number of cities.
	 *
	 * @return the number of cities
	 */
	public Long getNumberOfCities() { return this.numberOfCities; }

	/**
	 * Sets number of cities.
	 *
	 * @param numberOfCities the number of cities
	 * @return the number of cities
	 */
	public AdministratorStatistics setNumberOfCities(Long numberOfCities) {
		 this.numberOfCities = numberOfCities; 
		 return this;
		 }

	/**
	 * Gets number of users.
	 *
	 * @return the number of users
	 */
	public Long getNumberOfUsers() { return this.numberOfUsers; }

	/**
	 * Sets number of users.
	 *
	 * @param numberOfUsers the number of users
	 * @return the number of users
	 */
	public AdministratorStatistics setNumberOfUsers(Long numberOfUsers) { 
		this.numberOfUsers = numberOfUsers; 
		return this;
		}

	/**
	 * Gets number of cuisines.
	 *
	 * @return the number of cuisines
	 */
	public Long getNumberOfCuisines() { return this.numberOfCuisines; }

	/**
	 * Sets number of cuisines.
	 *
	 * @param numberOfCuisines the number of cuisines
	 * @return the number of cuisines
	 */
	public AdministratorStatistics setNumberOfCuisines(Long numberOfCuisines) { 
		this.numberOfCuisines = numberOfCuisines; 
		return this;
		}
}
