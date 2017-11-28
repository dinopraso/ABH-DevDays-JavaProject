package controllers;

import javax.inject.Inject;

import play.db.jpa.Transactional;
import play.mvc.Result;
import services.ActivityLogService;

/**
 * The type ActivityLog controller.
 */

public class ActivityLogController extends BaseController {

	private ActivityLogService service;
	
	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(ActivityLogService service){
		this.service = service;
	}
	
	/**
	 * Gets all activity logs.
	 * 
	 * @return the all activity logs
	 */
	@Transactional(readOnly = true)
	public Result getAllActivityLogs() {
		return wrapForAdmin(() -> this.service.getAllActivityLogs());
	}
}