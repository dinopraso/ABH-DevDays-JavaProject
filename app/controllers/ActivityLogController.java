package controllers;

import javax.inject.Inject;

import models.tables.ActivityLog;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.ActivityLogService;

/**
 * The type ActivityLog controller.
 */

public class ActivityLogController extends BaseController {

	private ActivityLogService service;
	
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_SIZE = "pageSize";
	
	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 19;
	
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
		return wrapForAdmin(() -> this.service.getAllActivityLogs(
				ActivityLog.createLog()
				.setPageNumber(getQueryInt(request().getQueryString(PAGE_NUMBER), DEFAULT_PAGE_NUMBER))
				.setPageSize(getQueryInt(request().getQueryString(PAGE_SIZE), DEFAULT_PAGE_SIZE))));
	}
}