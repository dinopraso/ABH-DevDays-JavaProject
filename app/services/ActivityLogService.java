package services;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.hibernate.criterion.Order;

import models.tables.ActivityLog;
import models.tables.User;
import play.db.jpa.Transactional;

/**
 *The type ActivityLog service
 */
@Singleton
public class ActivityLogService extends BaseService {
	
	@Inject
	public ActivityLogService() { }
	
	/**
	 * Gets all activity logs.
	 * 
	 * @return the all activity logs
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityLog> getAllActivityLogs() {
		return (List<ActivityLog>) getSession().createCriteria(ActivityLog.class)
				.addOrder(Order.desc("date_time"))
				.list();
	}

	/**
	 * Post activity log.
	 *
	 * @param activity the activity
	 * @return the boolean
	 */
	@Transactional
	public Boolean postActivityLog(String activity, User user) throws IOException{
		ActivityLog activity_log = new ActivityLog();
		activity_log.setUser(user);
		activity_log.setActivity(activity.toString());
		activity_log.setDate_time(System.currentTimeMillis());
		getSession().save(activity_log);
		return true;
	}
}