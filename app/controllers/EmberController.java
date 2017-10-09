package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * The type Ember controller.
 */
public class EmberController extends Controller {

	/**
	 * Index result.
	 *
	 * @param slug the slug
	 * @return the result
	 */
	public Result index(String slug) {
		return ok(index.render());
	}

}
