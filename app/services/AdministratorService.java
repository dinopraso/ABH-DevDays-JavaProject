package services;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import models.helpers.forms.ImageUploadForm;
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

}
