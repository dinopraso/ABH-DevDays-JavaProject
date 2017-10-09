package models.helpers.forms;

import models.BaseModel;
import models.tables.Reservation;

/**
 * The type Reservation confirmation form.
 */
public class ReservationConfirmationForm extends BaseModel {

	private Reservation reservation;

	/**
	 * Instantiates a new Reservation confirmation form.
	 */
	public ReservationConfirmationForm() { }

	/**
	 * Gets reservation.
	 *
	 * @return the reservation
	 */
	public Reservation getReservation() { return reservation; }

	/**
	 * Sets reservation.
	 *
	 * @param reservation the reservation
	 */
	public void setReservation(Reservation reservation) { this.reservation = reservation; }
}
