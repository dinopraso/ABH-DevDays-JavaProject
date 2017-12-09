package services;

import models.helpers.AdministratorStatistics;
import models.tables.Restaurant;
import models.tables.Reservation;
import models.tables.User;
import models.tables.RestaurantTable;
import models.tables.City;
import models.tables.Cuisine;
import org.hibernate.criterion.Projections;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The type Administrator service.
 */
@Singleton
public class AdministratorService extends BaseService {

	@Inject
	private AdministratorService() { }

	public AdministratorStatistics getAdministratorStatistics(){

		return AdministratorStatistics.getObject()
			.setNumberOfRestaurants( (Long) getSession().createCriteria(Restaurant.class)
				.setProjection(Projections.rowCount())
				.uniqueResult())
			.setNumberOfUsers( (Long) getSession().createCriteria(User.class)
				.setProjection(Projections.rowCount())
				.uniqueResult())
			.setNumberOfCities( (Long) getSession().createCriteria(City.class)
				.setProjection(Projections.rowCount())
				.uniqueResult())
			.setNumberOfCuisines( (Long) getSession().createCriteria(Cuisine.class)
				.setProjection(Projections.rowCount())
				.uniqueResult());
	}
}
