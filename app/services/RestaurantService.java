package services;

import models.helpers.PaginationAdapter;
import models.helpers.PopularLocation;
import models.helpers.PopularRestaurantsBean;
import models.helpers.RestaurantFilter;
import models.helpers.forms.ImageUploadForm;
import models.helpers.forms.ReviewForm;
import models.tables.Reservation;
import models.tables.Restaurant;
import models.tables.RestaurantReview;
import models.tables.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Restaurant service.
 */
@Singleton
public class RestaurantService extends BaseService {

	private static final String AWS_BASE_PATH = "https://abhrestaurants.s3.amazonaws.com/";

	@Inject
	private RestaurantService() { }

	/**
	 * Create restaurant boolean.
	 *
	 * @param restaurant the restaurant
	 * @throws Exception the exception
	 */
	public Boolean createRestaurant(final Restaurant restaurant) throws Exception {
		getSession().save(restaurant);
		return true;
	}

	/**
	 * Edit restaurant boolean.
	 *
	 * @param restaurant the restaurant
	 * @throws Exception the exception
	 */
	public Boolean editRestaurant(final Restaurant restaurant) throws Exception {
		getSession().merge(restaurant);
		return true;
	}

	/**
	 * Delete restaurant boolean.
	 *
	 * @param id the id
	 * @throws Exception the exception
	 */
	public Boolean deleteRestaurant(final UUID id) throws Exception {
		Restaurant restaurant = (Restaurant) getSession().createCriteria(Restaurant.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();

		getSession().delete(restaurant);
		return true;
	}

	/**
	 * Find restaurants with filter pagination adapter.
	 *
	 * @param restaurantFilter the restaurant filter
	 * @return the pagination adapter
	 */
	@SuppressWarnings("unchecked")
	public PaginationAdapter<Restaurant> findRestaurantsWithFilter(final RestaurantFilter restaurantFilter) {
		Criteria criteria = getSession().createCriteria(Restaurant.class);

		if (restaurantFilter.name != null) {
			criteria.add(Restrictions.ilike("name", restaurantFilter.name, MatchMode.ANYWHERE));
		}

		if (restaurantFilter.cityId != null) {
			criteria.add(Restrictions.eq("city.id", restaurantFilter.cityId));
		}

		if (restaurantFilter.price != null && restaurantFilter.price > 0) {
			criteria.add(Restrictions.eq("priceRange", restaurantFilter.price));
		}

		if (restaurantFilter.cuisine != null) {
			criteria.createCriteria("cuisines")
				.add(Restrictions.in("name", restaurantFilter.cuisine));
		}

if (restaurantFilter.rating != null && restaurantFilter.rating > 0) {
			List<RestaurantReview> listOfRewiews =  getSession()
			.createSQLQuery("select distinct on (restaurant_id) *  FROM restaurant_review group by id,restaurant_id HAVING avg(rating) >= :avgRating - 0.5 and  avg(rating) < :avgRating + 0.5")
			.addEntity(RestaurantReview.class)
			.setParameter("avgRating",restaurantFilter.rating)
			.list();

			if(!listOfRewiews.isEmpty()){
				List<UUID> listOfIds = listOfRewiews.stream().map(RestaurantReview::getRestaurantId).collect(Collectors.toList());
				criteria.add(Restrictions.in("id",listOfIds));
			}
			else
				criteria.add(Restrictions.eq("id", null));
		}
		
		Long numberOfPages = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()) / restaurantFilter.pageSize;

		criteria.setProjection(null)
				.setFirstResult((restaurantFilter.pageNumber - 1) * restaurantFilter.pageSize)
				.setMaxResults(restaurantFilter.pageSize);

		if (restaurantFilter.sortBy.equals("price")) {
			criteria.addOrder(Order.desc("priceRange"));
		}

		criteria.addOrder(Order.asc("name"));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<Restaurant> restaurants = criteria.list();

		switch (restaurantFilter.sortBy) {
			case "rating":
				restaurants.sort((o1, o2) -> o2.getAverageRating().compareTo(o1.getAverageRating()));
				break;
		}


		return PaginationAdapter.createOutput()
				.setPageNumber(restaurantFilter.pageNumber)
				.setPageSize(restaurantFilter.pageSize)
				.setModel(restaurants)
				.setNumberOfPages(numberOfPages);
	}

	/**
	 * Gets restaurant with id.
	 *
	 * @param id the id
	 * @return the restaurant with id
	 */
	public Restaurant getRestaurantWithId(final UUID id) {
		return (Restaurant) getSession().createCriteria(Restaurant.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * Gets nearby restaurants.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 * @return the nearby restaurants
	 */
	@SuppressWarnings("unchecked")
	public List<Restaurant> getNearbyRestaurants(final Float latitude, final Float longitude) {
		return getSession()
				.createSQLQuery("SELECT * FROM restaurant WHERE restaurant.longitude <> 0 AND restaurant.latitude <> 0 ORDER BY ST_Distance(ST_GeomFromText('POINT(' || restaurant.longitude || ' ' || restaurant.latitude || ')' ,4326), ST_GeomFromText('POINT(' || :longitude || ' ' || :latitude || ')',4326)) ASC LIMIT 3")
				.addEntity(Restaurant.class)
				.setParameter("longitude", longitude)
				.setParameter("latitude", latitude)
				.list();
	}

	/**
	 * Gets popular restaurants.
	 *
	 * @return the popular restaurants
	 */
	@SuppressWarnings("unchecked")
	public List<Restaurant> getPopularRestaurants() {

		List<PopularRestaurantsBean> popularRestaurantsBeans = getSession().createCriteria(Reservation.class, "reservation")
				.createAlias("reservation.table", "table")
				.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("table.restaurantId").as("restaurantId"))
						.add(Projections.count("table").as("tableCount")))
				.addOrder(Order.asc("tableCount"))
				.setResultTransformer(Transformers.aliasToBean(PopularRestaurantsBean.class))
				.setMaxResults(6)
				.list();

		if (popularRestaurantsBeans.size() > 0) {
			List<UUID> popularRestaurantsIds = popularRestaurantsBeans.stream().map(PopularRestaurantsBean::getRestaurantId).collect(Collectors.toList());

			return (List<Restaurant>) getSession().createCriteria(Restaurant.class)
					.add(Restrictions.in("id", popularRestaurantsIds))
					.addOrder(Order.asc("name"))
					.list();
		}

		return new ArrayList<>();
	}

	/**
	 * Gets popular locations.
	 *
	 * @return the popular locations
	 */
	@SuppressWarnings("unchecked")
	public List<PopularLocation> getPopularLocations() {
		List<Object[]> popularLocations = getSession().createCriteria(Restaurant.class)
				.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("city"))
						.add(Projections.count("id").as("numberOfRestaurants")))
				.addOrder(Order.desc("numberOfRestaurants"))
				.list();

		return popularLocations.stream().map(PopularLocation::new).collect(Collectors.toList());
	}

	/**
	 * Post review boolean.
	 *
	 * @param reviewForm the review form
	 * @param user       the user
	 */
	public Boolean postReview(final ReviewForm reviewForm, final User user) {
		RestaurantReview restaurantReview = (RestaurantReview) getSession().createCriteria(RestaurantReview.class)
				.add(Restrictions.eq("restaurantId", reviewForm.getRestaurantId()))
				.add(Restrictions.eq("userId", user.getId()))
				.uniqueResult();
		if (restaurantReview == null) {
			restaurantReview = new RestaurantReview(
					reviewForm.getRestaurantId(),
					user.getId(),
					reviewForm.getReviewScore(),
					reviewForm.getReviewText()
			);
		} else {
			restaurantReview.setReview(reviewForm.getReviewText());
			restaurantReview.setRating(reviewForm.getReviewScore());
		}

		getSession().save(restaurantReview);
		return true;
	}

	/**
	 * Gets number of restaurants.
	 *
	 * @return the number of restaurants
	 */
	public Long getNumberOfRestaurants() {
		return Long.valueOf(getSession().createCriteria(Restaurant.class)
				.setProjection(Projections.rowCount())
				.uniqueResult().toString());
	}

	/**
	 * Update picture string.
	 *
	 * @param imageUploadForm the image upload form
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updatePicture(final ImageUploadForm imageUploadForm) throws Exception {
		Restaurant restaurant = (Restaurant) getSession().createCriteria(Restaurant.class)
				.add(Restrictions.eq("id", imageUploadForm.getRestaurantId()))
				.uniqueResult();

		String newImagePath = AWS_BASE_PATH + imageUploadForm.getRestaurantId() + "-" + imageUploadForm.getImageType() + "." + imageUploadForm.getExtension();

		if (imageUploadForm.getImageType().equals("profile")) {
			restaurant.setProfileImagePath(newImagePath);
		} else {
			restaurant.setCoverImagePath(newImagePath);
		}

		getSession().update(restaurant);
		return "{ \"imageFor\": \"" + imageUploadForm.getImageType() + "\", \"url\": \"" + newImagePath + "\"}";
	}
}
