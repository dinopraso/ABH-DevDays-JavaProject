package models.tables;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import models.BaseModel;

/**
 *The type ActivityLog.
 */
@Entity
@Table(name = "activity_log")
public class ActivityLog extends BaseModel {
	private static final String DATE_PATTERN = "EEEE, MMM dd, yyyy";
	private static final String TIME_PATTERN = "h:mm a";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "activity")
	private String activity;

	@Column(name = "date_time")
	private Timestamp date_time;
	
	@Transient
	private String date;

	@Transient
	private String time;
	
	/**
	 * Instantiates a new ActivityLog.
	 */
	public ActivityLog() { }
	
	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public UUID getId() {	return id;	}
	
	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(UUID id) {	this.id = id;	}
	
	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public User getUser() {	return user;	}
	
	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(User user) {	this.user = user; }
	
	/**
	 * Gets activity.
	 *
	 * @return the activity
	 */
	public String getActivity() {	return activity;	}
	
	/**
	 * Sets activity.
	 *
	 * @param activity the activity
	 */
	public void setActivity(String activity) {	this.activity = activity;	}

	/**
	 * Gets date_time.
	 *
	 * @return the date_time
	 */
	public Timestamp getDate_time() {	return date_time; }
	
	/**
	 * Sets date_time.
	 *
	 * @param date_time the date_time
	 */
	public void setDate_time(Long date_time) {	
		this.date_time = new Timestamp(date_time);
	}
	
	/**
	 * Gets date.
	 *
	 * @return the date
	 */
	public String getDate() {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		return df.format(this.date_time.getTime());
	}
	
	/**
	 * Sets date.
	 *
	 * @param date the date
	 */
	public void setDate(String date) {	this.date = date;	}
	
	/**
	 * Gets time.
	 *
	 * @return the time
	 */
	public String getTime() {
		DateFormat tf = new SimpleDateFormat(TIME_PATTERN);
		return tf.format(this.date_time.getTime());
	}
	
	/**
	 * Sets time.
	 *
	 * @param time the time
	 */
	public void setTime(String time) {	this.time = time;	}
	
}