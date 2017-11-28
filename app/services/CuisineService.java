package services;

import models.tables.Cuisine;
import models.tables.User;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Cuisine service.
 */
@Singleton
public class CuisineService extends BaseService {

	private ActivityLogService logService = new ActivityLogService();
	private static final String ORDER_KEY = "name";

	@Inject
	private CuisineService() { }

	/**
	 * Gets all cuisines.
	 *
	 * @return the all cuisines
	 */
	@SuppressWarnings("unchecked")
	public List<Cuisine> getAllCuisines() {
		return (List<Cuisine>) getSession().createCriteria(Cuisine.class)
				.addOrder(Order.asc(ORDER_KEY))
				.list();
	}

	/**
	 * Gets all cuisines as string.
	 *
	 * @return the all cuisines as string
	 */
	public List<String> getAllCuisinesAsString() {
		return this.getAllCuisines().stream()
				.map(Cuisine::getName)
				.collect(Collectors.toList());
	}

	/**
	 * Gets cuisine.
	 *
	 * @param id the id
	 * @return the cuisine
	 */
	public Cuisine getCuisine(final UUID id) {
		return (Cuisine) getSession().createCriteria(Cuisine.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * Create cuisine boolean.
	 *
	 * @param cuisine the cuisine
	 * @return the boolean
	 */
	public Boolean createCuisine(final Cuisine cuisine, final User user) {
		getSession().save(cuisine);
		
		try {
			logService.postActivityLog("Added cuisine " + cuisine.getName(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Edit cuisine boolean.
	 *
	 * @param cuisine the cuisine
	 * @return the boolean
	 */
	public Boolean editCuisine(final Cuisine cuisine, final User user) {
		getSession().update(cuisine);
		
		try {
			logService.postActivityLog("Edited cuisine " + cuisine.getName(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Delete cuisine boolean.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean deleteCuisine(final UUID id, final User user) {
		Cuisine cuisine = (Cuisine) getSession().createCriteria(Cuisine.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(cuisine);
		
		try {
			logService.postActivityLog("Deleted cuisine " + cuisine.getName(), user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
