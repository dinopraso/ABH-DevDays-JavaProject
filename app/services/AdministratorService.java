package services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import models.helpers.AdministratorStatistics;
import models.helpers.PopularLocation;
import models.helpers.forms.ImageUploadForm;
import models.tables.City;
import models.tables.Reservation;
import models.tables.Restaurant;
import models.tables.User;
import play.api.mvc.MultipartFormData;
import play.mvc.Http.RequestBody;

/**
 * The type Administrator service.
 */
@Singleton
public class AdministratorService extends BaseService {

    
	@Inject
	private AdministratorService() { }
	
	public String Test(final RequestBody body ) throws Exception {
	
	    return "Ok";
	}
	/**
	 * Gets popular locations.
	 *
	 * @return the popular locations
	 */
	@SuppressWarnings("unchecked")
	public AdministratorStatistics getStatistic() {   		
	    return AdministratorStatistics.createStatistics()
	    		.setNumberOfCity(this.getNumberOfCity())
	    		.setNumberOfReservation(this.getNumberOfReservation())
	    		.setNumberOfRestoraunt(this.getNumberOfRestaurants())
	    		.setNumberOfUsers(this.getNumberOfUsers())
	    		.setPopularLocations(this.getPopularLocation());
	    
	    
	}
        /**
         * Get number of Restaurants
         * @return
         */
        private Long getNumberOfRestaurants() {
        	return Long.valueOf(getSession().createCriteria(Restaurant.class)
        			.setProjection(Projections.rowCount())
        			.uniqueResult().toString());
        }
        /**
         * Get number of Users
         * @return
         */
        private Long getNumberOfUsers(){
            return Long.valueOf(getSession().createCriteria(User.class)
        	    .setProjection(Projections.rowCount())
        	    .uniqueResult().toString());
        	    
        }
        /**
         * Get number of City
         * @return
         */
        private Long getNumberOfCity(){
            return Long.valueOf(getSession().createCriteria(City.class)
        	    .setProjection(Projections.rowCount())
        	    .uniqueResult().toString());
        }
        /**
         * Get number of Reservation
         * @return
         */
        private Long getNumberOfReservation(){
            return Long.valueOf(getSession().createCriteria(Reservation.class)
        	    .setProjection(Projections.rowCount())
        	    .uniqueResult().toString());
        }
        /**
         * Get popular locations
         * @return
         */
       @SuppressWarnings("unchecked")
       private List<PopularLocation> getPopularLocation(){
	   List<Object[]> popularLocations = getSession().createCriteria(Restaurant.class)
			.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("city"))
					.add(Projections.count("id").as("numberOfRestaurants")))
			.addOrder(Order.desc("numberOfRestaurants"))
			.list();
	    return popularLocations.stream().map(PopularLocation::new).collect(Collectors.toList());
	    
       }
       

}
