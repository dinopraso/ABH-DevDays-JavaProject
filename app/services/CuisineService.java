package services;

import models.tables.Cuisine;
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
	public Boolean createCuisine(final Cuisine cuisine) {
		getSession().save(cuisine);
		return true;
	}

	/**
	 * Edit cuisine boolean.
	 *
	 * @param cuisine the cuisine
	 * @return the boolean
	 */
	public Boolean editCuisine(final Cuisine cuisine) {
		getSession().update(cuisine);
		return true;
	}

	/**
	 * Delete cuisine boolean.
	 *
	 * @param id the id
	 * @return the boolean
	 */
	public Boolean deleteCuisine(final UUID id) {
		Cuisine cuisine = (Cuisine) getSession().createCriteria(Cuisine.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(cuisine);
		return true;
	}
}
