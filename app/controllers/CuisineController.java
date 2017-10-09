package controllers;

import models.tables.Cuisine;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.CuisineService;

import javax.inject.Inject;
import java.util.UUID;

/**
 * The type Cuisine controller.
 */
public class CuisineController extends BaseController {

	private CuisineService service;

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(CuisineService service) {
		this.service = service;
	}

	/**
	 * Gets cuisine.
	 *
	 * @param id the id
	 * @return the cuisine
	 */
	@Transactional(readOnly = true)
	public Result getCuisine(String id) {
		return wrapForPublic(() -> this.service.getCuisine(UUID.fromString(id)));
	}

	/**
	 * Create cuisine result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result createCuisine() {
		return wrapForAdmin(() -> this.service.createCuisine(formFactory.form(Cuisine.class).bindFromRequest().get()));
	}

	/**
	 * Edit cuisine result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result editCuisine() {
		return wrapForAdmin(() -> this.service.editCuisine(formFactory.form(Cuisine.class).bindFromRequest().get()));
	}

	/**
	 * Delete cuisine result.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Transactional
	public Result deleteCuisine(String id) {
		return wrapForAdmin(() -> this.service.deleteCuisine(UUID.fromString(id)));
	}

	/**
	 * Gets all cuisines.
	 *
	 * @return the all cuisines
	 */
	@Transactional(readOnly = true)
	public Result getAllCuisines() {
		return wrapForPublic(() -> this.service.getAllCuisines());
	}

	/**
	 * Gets all cuisines as string.
	 *
	 * @return the all cuisines as string
	 */
	@Transactional(readOnly = true)
	public Result getAllCuisinesAsString() {
		return wrapForPublic(() -> this.service.getAllCuisinesAsString());
	}
}
