package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.amazonaws.services.ec2.model.Reservation;

import models.helpers.PaginationAdapter;
import models.helpers.PopularLocation;
import models.helpers.PopularRestaurantsBean;
import models.helpers.RestaurantFilter;
import models.helpers.UserHabitsBean;
import models.helpers.forms.ImageUploadForm;
import models.helpers.forms.ReviewForm;
import models.tables.Restaurant;
import models.tables.RestaurantReview;
import models.tables.User;

/**
 * The type Restaurant service.
 */
@Singleton
public class RestaurantService extends BaseService
{

    private static final String AWS_BASE_PATH = "https://abhrestaurants.s3.amazonaws.com/";

    @Inject
    private RestaurantService()
    {
    }

    /**
     * Create restaurant boolean.
     *
     * @param restaurant
     *            the restaurant
     * @throws Exception
     *             the exception
     */
    public Boolean createRestaurant(final Restaurant restaurant) throws Exception
    {
	getSession().save(restaurant);
	return true;
    }

    /**
     * Edit restaurant boolean.
     *
     * @param restaurant
     *            the restaurant
     * @throws Exception
     *             the exception
     */
    public Boolean editRestaurant(final Restaurant restaurant) throws Exception
    {
	getSession().merge(restaurant);
	return true;
    }

    /**
     * Delete restaurant boolean.
     *
     * @param id
     *            the id
     * @throws Exception
     *             the exception
     */
    public Boolean deleteRestaurant(final UUID id) throws Exception
    {
	Restaurant restaurant = (Restaurant) getSession().createCriteria(Restaurant.class)
		.add(Restrictions.eq("id", id)).uniqueResult();

	getSession().delete(restaurant);
	return true;
    }

    /**
     * Find restaurants with filter pagination adapter.
     *
     * @param restaurantFilter
     *            the restaurant filter
     * @return the pagination adapter
     */
    @SuppressWarnings("unchecked")
    public PaginationAdapter<Restaurant> findRestaurantsWithFilter(final RestaurantFilter restaurantFilter)
    {
	Criteria criteria = getSession().createCriteria(Restaurant.class);

	if (restaurantFilter.name != null)
	{
	    criteria.add(Restrictions.ilike("name", restaurantFilter.name, MatchMode.ANYWHERE));
	}

	if (restaurantFilter.cityId != null)
	{
	    criteria.add(Restrictions.eq("city.id", restaurantFilter.cityId));
	}

	Long numberOfPages = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult())
		/ restaurantFilter.pageSize;

	criteria.setProjection(null).setFirstResult((restaurantFilter.pageNumber - 1) * restaurantFilter.pageSize)
		.setMaxResults(restaurantFilter.pageSize);

	if (restaurantFilter.sortBy.equals("price"))
	{
	    criteria.addOrder(Order.desc("priceRange"));
	}

	criteria.addOrder(Order.asc("name"));

	List<Restaurant> restaurants = criteria.list();

	switch (restaurantFilter.sortBy)
	{
	case "rating":
	    restaurants.sort((o1, o2) -> o2.getAverageRating().compareTo(o1.getAverageRating()));
	    break;
	}

	return PaginationAdapter.createOutput().setPageNumber(restaurantFilter.pageNumber)
		.setPageSize(restaurantFilter.pageSize).setModel(restaurants).setNumberOfPages(numberOfPages);
    }

    /**
     * Gets restaurant with id.
     *
     * @param id
     *            the id
     * @return the restaurant with id
     */
    public Restaurant getRestaurantWithId(final UUID id)
    {
	return (Restaurant) getSession().createCriteria(Restaurant.class).add(Restrictions.eq("id", id)).uniqueResult();
    }

    /**
     * Gets nearby restaurants.
     *
     * @param latitude
     *            the latitude
     * @param longitude
     *            the longitude
     * @return the nearby restaurants
     */
    @SuppressWarnings("unchecked")
    public List<Restaurant> getNearbyRestaurants(final Float latitude, final Float longitude)
    {
	return getSession()
		.createSQLQuery(
			"SELECT * FROM restaurant WHERE restaurant.longitude <> 0 AND restaurant.latitude <> 0 ORDER BY ST_Distance(ST_GeomFromText('POINT(' || restaurant.longitude || ' ' || restaurant.latitude || ')' ,4326), ST_GeomFromText('POINT(' || :longitude || ' ' || :latitude || ')',4326)) ASC LIMIT 3")
		.addEntity(Restaurant.class).setParameter("longitude", longitude).setParameter("latitude", latitude)
		.list();
    }

    /**
     * Gets popular restaurants.
     *
     * @return the popular restaurants
     */
    @SuppressWarnings("unchecked")
    public List<Restaurant> getPopularRestaurants(UUID userId)
    {

	UserHabitsBean userHabits = getUserHabits(userId);
      
	List<Restaurant> restaurants = (List<Restaurant>) getSession().createCriteria(Restaurant.class)
		.add(Restrictions.eq("city.id", userHabits.getCity()))
		.add(Restrictions.eq("priceRange", userHabits.getUserPriceRange())).list();

	List<Object> toRemove = new ArrayList<Object>();

	for (Restaurant restaurant : restaurants)
	{

	    if (restaurant.getAverageRating() < (userHabits.getUserRatingRange() - 0.5d)
		    || restaurant.getAverageRating() > (userHabits.getUserRatingRange() + 0.5d))
	    {
		toRemove.add(restaurant);
	    }

	}
	restaurants.removeAll(toRemove);

	if (restaurants.size() > 0)
	{
	    return restaurants;
	}

	return new ArrayList<>();
    }
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
    public List<PopularLocation> getPopularLocations()
    {
	List<Object[]> popularLocations = getSession().createCriteria(Restaurant.class)
		.setProjection(Projections.projectionList().add(Projections.groupProperty("city"))
			.add(Projections.count("id").as("numberOfRestaurants")))
		.addOrder(Order.desc("numberOfRestaurants")).list();

	return popularLocations.stream().map(PopularLocation::new).collect(Collectors.toList());
    }

    /**
     * Post review boolean.
     *
     * @param reviewForm
     *            the review form
     * @param user
     *            the user
     */
    public Boolean postReview(final ReviewForm reviewForm, final User user)
    {
	RestaurantReview restaurantReview = (RestaurantReview) getSession().createCriteria(RestaurantReview.class)
		.add(Restrictions.eq("restaurantId", reviewForm.getRestaurantId()))
		.add(Restrictions.eq("userId", user.getId())).uniqueResult();
	if (restaurantReview == null)
	{
	    restaurantReview = new RestaurantReview(reviewForm.getRestaurantId(), user.getId(),
		    reviewForm.getReviewScore(), reviewForm.getReviewText());
	} else
	{
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
    public Long getNumberOfRestaurants()
    {
	return Long.valueOf(getSession().createCriteria(Restaurant.class).setProjection(Projections.rowCount())
		.uniqueResult().toString());
    }

    /**
     * Update picture string.
     *
     * @param imageUploadForm
     *            the image upload form
     * @return the string
     * @throws Exception
     *             the exception
     */
    public String updatePicture(final ImageUploadForm imageUploadForm) throws Exception
    {
	Restaurant restaurant = (Restaurant) getSession().createCriteria(Restaurant.class)
		.add(Restrictions.eq("id", imageUploadForm.getRestaurantId())).uniqueResult();

	String newImagePath = AWS_BASE_PATH + imageUploadForm.getRestaurantId() + "-" + imageUploadForm.getImageType()
		+ "." + imageUploadForm.getExtension();

	if (imageUploadForm.getImageType().equals("profile"))
	{
	    restaurant.setProfileImagePath(newImagePath);
	} else
	{
	    restaurant.setCoverImagePath(newImagePath);
	}

	getSession().update(restaurant);
	return "{ \"imageFor\": \"" + imageUploadForm.getImageType() + "\", \"url\": \"" + newImagePath + "\"}";
    }

    @SuppressWarnings("unchecked")
    private UserHabitsBean getUserHabits(UUID userId)
    {

	Integer price = 0;

	UUID cityID = (UUID) getSession().createCriteria(User.class).setProjection(Projections.property("city.id"))
		.uniqueResult();

	List<RestaurantReview> restaurantReviews = getSession().createCriteria(RestaurantReview.class)
		.add(Restrictions.eq("userId", userId)).list();

	List<UUID> userReviewRestaurantIds = restaurantReviews.stream().map(RestaurantReview::getRestaurantId)
		.collect(Collectors.toList());

	List<Restaurant> userVisitedRestaurant = getSession().createCriteria(Restaurant.class)
		.add(Restrictions.in("id", userReviewRestaurantIds)).list();

	for (Restaurant restaurant : userVisitedRestaurant)
	{
	    price += restaurant.getPriceRange();
	}
	price /= userVisitedRestaurant.size();

	UserHabitsBean userHabitsBean = (UserHabitsBean) getSession().createCriteria(RestaurantReview.class, "review")
		.add(Restrictions.eq("userId", userId))
		.setProjection(Projections.projectionList().add(Projections.groupProperty("review.userId").as("userId"))
			.add(Projections.avg("review.rating").as("userRatingRange")))
		.setResultTransformer(Transformers.aliasToBean(UserHabitsBean.class)).uniqueResult();

	userHabitsBean.setUserPriceRange(price);
	userHabitsBean.setCity(cityID);
	return userHabitsBean;
    }
}
