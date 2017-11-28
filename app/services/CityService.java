package services;

import models.tables.City;
import models.tables.User;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

/**
 * The type City service.
 */
@Singleton
public class CityService extends BaseService {

	private ActivityLogService logService = new ActivityLogService();
	private static final String ORDER_KEY = "name";

	@Inject
	private CityService() { }

	/**
	 * Gets all cities.
	 *
	 * @return the all cities
	 */
	@SuppressWarnings("unchecked")
	public List<City> getAllCities() {
		return (List<City>) getSession().createCriteria(City.class)
				.addOrder(Order.asc(ORDER_KEY))
				.list();
	}

	/**
	 * Gets city.
	 *
	 * @param locationId the location id
	 * @return the city
	 */
	public City getCity(final UUID locationId) {
		return (City) getSession().createCriteria(City.class)
				.add(Restrictions.eq("id", locationId))
				.uniqueResult();
	}

	/**
	 * Create city boolean.
	 *
	 * @param city the city
	 * @throws Exception the exception
	 */
	public Boolean createCity(final City city, final User user) throws Exception {
		getSession().save(city);
		
		try {
			logService.postActivityLog("Added city " + city.getName(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Edit city boolean.
	 *
	 * @param city the city
	 * @throws Exception the exception
	 */
	public Boolean editCity(final City city, final User user) throws Exception {
		getSession().update(city);
		
		try {
			logService.postActivityLog("Edited city " + city.getName(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Delete city boolean.
	 *
	 * @param id the id
	 * @throws Exception the exception
	 */
	public Boolean deleteCity(final UUID id, final User user) throws Exception {
		City city = (City) getSession().createCriteria(City.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(city);
		
		try {
			logService.postActivityLog("Deleted city " + city.getName(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
