package services;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import models.helpers.PaginationAdapter;
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
	 * @param activityLog the activity log
	 * @return the pagination adapter
	 */
	@SuppressWarnings("unchecked")
	public PaginationAdapter<ActivityLog> getAllActivityLogs(final ActivityLog activityLog) {
		
		Criteria criteria = getSession().createCriteria(ActivityLog.class);
		
		Long numberOfPages = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()) / activityLog.pageSize;
		
		if((((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()) % activityLog.pageSize) > 0){
			numberOfPages++;
		}
		
		criteria.setProjection(null)
			.setFirstResult((activityLog.pageNumber - 1) * activityLog.pageSize)
			.setMaxResults(activityLog.pageSize);
		
		criteria.addOrder(Order.desc("date_time"));
		
		List<ActivityLog> logs = criteria.list();
		
		return PaginationAdapter.createOutput()
				.setPageNumber(activityLog.pageNumber)
				.setPageSize(activityLog.pageSize)
				.setModel(logs)
				.setNumberOfPages(numberOfPages);
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