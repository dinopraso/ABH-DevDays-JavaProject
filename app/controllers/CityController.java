package controllers;

import models.tables.City;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.CityService;

import javax.inject.Inject;
import java.util.UUID;

/**
 * The type City controller.
 */
public class CityController extends BaseController {

	private CityService service;

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(final CityService service) {
		this.service = service;
	}

	/**
	 * Gets city.
	 *
	 * @param locationId the location id
	 * @return the city
	 */
	@Transactional(readOnly = true)
	public Result getCity(final String locationId) {
		return wrapForPublic(() -> this.service.getCity(UUID.fromString(locationId)));
	}

	/**
	 * Create city result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result createCity() {
		return wrapForAdmin(() -> this.service.createCity(formFactory.form(City.class).bindFromRequest().get()));
	}

	/**
	 * Edit city result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result editCity() {
		return wrapForAdmin(() -> this.service.editCity(formFactory.form(City.class).bindFromRequest().get()));
	}

	/**
	 * Delete city result.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Transactional
	public Result deleteCity(final String id) {
		return wrapForAdmin(() -> this.service.deleteCity(UUID.fromString(id)));
	}

	/**
	 * Gets all cities.
	 *
	 * @return the all cities
	 */
	@Transactional(readOnly = true)
	public Result getAllCities() {
		return wrapForPublic(() -> this.service.getAllCities());
	}
}
