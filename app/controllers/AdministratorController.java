package controllers;

import services.AdministratorService;

import javax.inject.Inject;

import models.helpers.forms.ImageUploadForm;
import play.api.data.Form;
import play.api.mvc.MultipartFormData;
import play.db.jpa.Transactional;
import play.mvc.Http.RequestBody;
import play.mvc.Result;

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
	@Transactional(readOnly = true)
	public Result fileUpload() {
	    	RequestBody temp = request().body();
		return wrapForPublic(() -> this.service.Test(temp));
	}
}
