package controllers;

import io.netty.util.internal.StringUtil;
import models.helpers.RestaurantFilter;
import models.helpers.forms.ImageUploadForm;
import models.helpers.forms.ReviewForm;
import models.tables.Restaurant;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.RestaurantService;

import javax.inject.Inject;
import java.util.UUID;

/**
 * The type Restaurant controller.
 */
public class RestaurantController extends BaseController {

	private RestaurantService service;

	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_SIZE = "pageSize";
	private static final String NAME_FILTER = "nameFilter";
	private static final String CITY_FILTER = "cityFilter";
	private static final String SORT_BY = "sortBy";

	private static final String PRICE_FILTER = "priceFilter";
	private static final String RATING_FILTER = "ratingFilter";
    private static final String CUISINE_FILTER = "cuisineFilter";

	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 9;
	private static final Integer DEFAULT_PRICE_FILTER = 0;
	private static final Integer DEFAULT_RATING_FILTER = 0;

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(RestaurantService service) { this.service = service; }

	/**
	 * Create restaurant result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result createRestaurant() {
		return wrapForAdmin(() -> this.service.createRestaurant(formFactory.form(Restaurant.class).bindFromRequest().get()));
	}

	/**
	 * Edit restaurant result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result editRestaurant() {
		return wrapForAdmin(() -> this.service.editRestaurant(formFactory.form(Restaurant.class).bindFromRequest().get()));
	}

	/**
	 * Delete restaurant result.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Transactional
	public Result deleteRestaurant(String id) {
		return wrapForAdmin(() -> this.service.deleteRestaurant(UUID.fromString(id)));
	}

	/**
	 * Gets all restaurants.
	 *
	 * @return the all restaurants
	 */
	@Transactional(readOnly = true)
	public Result getAllRestaurants() {
		String cityFilter = request().getQueryString(CITY_FILTER);
		String cuisine="";
				String[] cuisineFilterParam =  request().queryString().get(CUISINE_FILTER);
				if (cuisineFilterParam != null) {
						for (String str : cuisineFilterParam) {
								if (!str.isEmpty() && str != null) {
										cuisine = cuisine.concat(str + ",");
									}
							}
					}
		        if(cuisine.endsWith(",")){
			            cuisine = cuisine.substring(0,cuisine.length() - 1);
			        }
		        String cuisineFilter = cuisine;
		return wrapForPublic(() -> this.service.findRestaurantsWithFilter(
				RestaurantFilter.createFilter()
						.setPageNumber(getQueryInt(request().getQueryString(PAGE_NUMBER), DEFAULT_PAGE_NUMBER))
						.setPageSize(getQueryInt(request().getQueryString(PAGE_SIZE), DEFAULT_PAGE_SIZE))
						.setNameFilter(request().getQueryString(NAME_FILTER))
						.setCityFilter(!StringUtil.isNullOrEmpty(cityFilter) ? UUID.fromString(cityFilter) : null)
						.setSort(request().getQueryString(SORT_BY))
						.setPriceFilter(getQueryInt(request().getQueryString(PRICE_FILTER), DEFAULT_PRICE_FILTER))
						.setRatingFilter(getQueryInt(request().getQueryString(RATING_FILTER), DEFAULT_RATING_FILTER))
				        .setCuisineFilter(cuisineFilter
		)));
	}

	/**
	 * Gets restaurant.
	 *
	 * @param id the id
	 * @return the restaurant
	 */
	@Transactional(readOnly = true)
	public Result getRestaurant(String id) {
		return wrapForPublic(() -> this.service.getRestaurantWithId(UUID.fromString(id)));
	}

	/**
	 * Gets nearby restaurants.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 * @return the nearby restaurants
	 */
	@Transactional(readOnly = true)
	public Result getNearbyRestaurants(Float latitude, Float longitude) {
		return wrapForPublic(() -> this.service.getNearbyRestaurants(latitude, longitude));
	}

	/**
	 * Gets popular restaurants.
	 *
	 * @return the popular restaurants
	 */
	@Transactional(readOnly = true)
	public Result getPopularRestaurants() {
		return wrapForPublic(() -> this.service.getPopularRestaurants());
	}

	/**
	 * Gets popular locations.
	 *
	 * @return the popular locations
	 */
	@Transactional(readOnly = true)
	public Result getPopularLocations() {
		return wrapForPublic(() -> this.service.getPopularLocations());
	}

	/**
	 * Post review result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result postReview() {
		return wrapForUser(() -> this.service.postReview(formFactory.form(ReviewForm.class).bindFromRequest().get(), this.cache.get(session("uid"))));
	}

	/**
	 * Gets number of restaurants.
	 *
	 * @return the number of restaurants
	 */
	@Transactional(readOnly = true)
	public Result getNumberOfRestaurants() {
		return wrapForPublic(() -> this.service.getNumberOfRestaurants());
	}

	/**
	 * Update picture result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result updatePicture() {
		return wrapForAdmin(() -> this.service.updatePicture(formFactory.form(ImageUploadForm.class).bindFromRequest().get()));
	}
}
