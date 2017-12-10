package controllers;

import services.AdministratorService;

import javax.inject.Inject;
import play.mvc.Result;
import play.db.jpa.Transactional;

/**
 * The type Administrator controller.
 */
public class AdministratorController extends BaseController {

	private AdministratorService service;

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(final AdministratorService service) {
		this.service = service;
	}

	/**
	 * Gets administrators statistics.
	 *
	 * @return the administrators statistics
	 */
	@Transactional(readOnly = true)
	public Result getAdministratorStatistics() {
		return wrapForAdmin(() -> this.service.getAdministratorStatistics());
	}
}
