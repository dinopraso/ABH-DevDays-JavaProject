package models.helpers;

import models.tables.Reservation;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User reservations.
 */
public class UserReservations {
	private List<Reservation> upcoming = new ArrayList<>();
	private List<Reservation> past = new ArrayList<>();

	/**
	 * Instantiates a new User reservations.
	 *
	 * @param allReservations the all reservations
	 */
	public UserReservations(final List<Reservation> allReservations) {
		allReservations.forEach(reservation ->
				(reservation.getStartTime().getTime() > System.currentTimeMillis() ? this.upcoming : this.past).add(reservation));
	}

	/**
	 * Gets upcoming.
	 *
	 * @return the upcoming
	 */
	public List<Reservation> getUpcoming() {
		return this.upcoming;
	}

	/**
	 * Gets past.
	 *
	 * @return the past
	 */
	public List<Reservation> getPast() {
		return this.past;
	}
}
