package controllers;

import models.helpers.forms.ReservationConfirmationForm;
import models.helpers.forms.ReservationForm;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.ReservationService;

import javax.inject.Inject;
import java.util.UUID;

/**
 * The type Reservation controller.
 */
public class ReservationController extends BaseController {

	private ReservationService service;

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(ReservationService service) {
		this.service = service;
	}

	/**
	 * Gets reservation.
	 *
	 * @param id the id
	 * @return the reservation
	 */
	@Transactional(readOnly = true)
	public Result getReservation(String id) {
		return wrapForPublic(() -> this.service.getReservation(UUID.fromString(id)));
	}

	/**
	 * Post reservation inquiry result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result postReservationInquiry() {
		return wrapForPublic(() -> this.service.processInquiry(formFactory.form(ReservationForm.class).bindFromRequest().get()));
	}

	/**
	 * Post reservation result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result postReservation() {
		return wrapForPublic(() -> this.service.postReservation(formFactory.form(ReservationForm.class).bindFromRequest().get()));
	}

	/**
	 * Confirm reservation result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result confirmReservation() {
		return wrapForUser(() -> this.service.confirmReservation(formFactory.form(ReservationConfirmationForm.class).bindFromRequest().get()));
	}

	/**
	 * Gets my reservations.
	 *
	 * @return the my reservations
	 */
	@Transactional(readOnly = true)
	public Result getMyReservations() {
		return wrapForUser(() -> this.service.getMyReservations(this.cache.get(session("uid"))));
	}

	/**
	 * Gets all reservations.
	 *
	 * @param restaurantId the restaurant id
	 * @param dateFilter   the date filter
	 * @return the all reservations
	 */
	@Transactional(readOnly = true)
	public Result getAllReservations(String restaurantId, String dateFilter) {
		return wrapForAdmin(() -> this.service.getAllReservations(UUID.fromString(restaurantId), dateFilter));
	}
}
