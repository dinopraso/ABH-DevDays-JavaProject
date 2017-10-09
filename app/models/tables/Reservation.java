package models.tables;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * The type Reservation.
 */
@Entity
@Table(name = "reservation")
public class Reservation extends BaseModel {
	private static final String DATE_PATTERN = "EEEE, MMM dd, yyyy";
	private static final String TIME_PATTERN = "h:mm a";

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "table_id", referencedColumnName = "id")
	private RestaurantTable table;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "reserved_on")
	private Timestamp reservedOn;

	@Column(name = "is_confirmed")
	private Boolean isConfirmed = false;

	@Transient
	private String date;

	@Transient
	private String time;

	/**
	 * Instantiates a new Reservation.
	 */
	public Reservation() { }

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public UUID getId() { return id; }

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(UUID id) { this.id = id; }

	/**
	 * Gets table.
	 *
	 * @return the table
	 */
	public RestaurantTable getTable() { return table; }

	/**
	 * Sets table.
	 *
	 * @param table the table
	 */
	public void setTable(RestaurantTable table) { this.table = table; }

	/**
	 * Gets user.
	 *
	 * @return the user
	 */
	public User getUser() { return user; }

	/**
	 * Sets user.
	 *
	 * @param user the user
	 */
	public void setUser(User user) { this.user = user; }

	/**
	 * Gets start time.
	 *
	 * @return the start time
	 */
	public Timestamp getStartTime() { return startTime; }

	/**
	 * Sets start time.
	 *
	 * @param startTime the start time
	 */
	public void setStartTime(Long startTime) { this.startTime = new Timestamp(startTime); }

	/**
	 * Gets reserved on.
	 *
	 * @return the reserved on
	 */
	public Timestamp getReservedOn() { return reservedOn; }

	/**
	 * Sets reserved on.
	 *
	 * @param reservedOn the reserved on
	 */
	public void setReservedOn(Long reservedOn) { this.reservedOn = new Timestamp(reservedOn); }

	/**
	 * Gets confirmed.
	 *
	 * @return the confirmed
	 */
	public Boolean getConfirmed() { return isConfirmed; }

	/**
	 * Sets confirmed.
	 *
	 * @param confirmed the confirmed
	 */
	public void setConfirmed(Boolean confirmed) { isConfirmed = confirmed; }

	/**
	 * Gets date.
	 *
	 * @return the date
	 */
	public String getDate() {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		return df.format(this.startTime.getTime());
	}

	/**
	 * Sets date.
	 *
	 * @param date the date
	 */
	public void setDate(String date) { this.date = date; }

	/**
	 * Gets time.
	 *
	 * @return the time
	 */
	public String getTime() {
		DateFormat df = new SimpleDateFormat(TIME_PATTERN);
		return df.format(this.startTime.getTime());
	}

	/**
	 * Sets time.
	 *
	 * @param time the time
	 */
	public void setTime(String time) { this.time = time; }
}