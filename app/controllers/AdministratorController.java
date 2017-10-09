package controllers;

import services.AdministratorService;

import javax.inject.Inject;

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
}
