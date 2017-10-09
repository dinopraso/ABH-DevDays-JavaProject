package services;

import models.helpers.ReservationInquiryResponse;
import models.helpers.UserReservations;
import models.helpers.forms.ReservationConfirmationForm;
import models.helpers.forms.ReservationForm;
import models.tables.Reservation;
import models.tables.RestaurantTable;
import models.tables.User;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Reservation service.
 */
@Singleton
public class ReservationService extends BaseService {

	private static final Long ONE_HOUR_MILLIS = TimeUnit.HOURS.toMillis(1);
	private static final Long FORTY_FIVE_MINUTES_MILLIS = TimeUnit.MINUTES.toMillis(45);
	private static final Long FIFTEEN_MINUTES_MILLIS = TimeUnit.MINUTES.toMillis(15);
	private static final Long FIVE_MINUTES_MILLIS = TimeUnit.MINUTES.toMillis(5);
	private static final String DAY_START_TIME = " 00:00:00";
	private static final String DAY_END_TIME = " 23:59:59";

	@Inject
	private ReservationService() { }

	/**
	 * Gets reservation.
	 *
	 * @param id the id
	 * @return the reservation
	 * @throws Exception the exception
	 */
	public Reservation getReservation(UUID id) throws Exception {
		return (Reservation) getSession()
				.createCriteria(Reservation.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	/**
	 * Process inquiry reservation inquiry response.
	 *
	 * @param reservationForm the reservation form
	 * @return the reservation inquiry response
	 * @throws Exception the exception
	 */
	public ReservationInquiryResponse processInquiry(ReservationForm reservationForm) throws Exception {
		ReservationInquiryResponse response = ReservationInquiryResponse.getObject()
				.setInquiry(reservationForm)
				.setNumberOfReservationsToday((Long) getSession()
						.createCriteria(Reservation.class)
						.createAlias("table", "t")
						.add(Restrictions.eq("t.restaurantId", reservationForm.getRestaurantId()))
						.setProjection(Projections.rowCount())
						.uniqueResult()
				);

		Timestamp desiredDateTime = new Timestamp(reservationForm.getDate().getTime() + reservationForm.getTime().getTime() + ONE_HOUR_MILLIS);

		List<RestaurantTable> freeTables = this.getFreeTables(
				desiredDateTime,
				reservationForm.getRestaurantId(),
				reservationForm.getNumberOfPeople()
		);

		response.setNumberOfTablesLeft((long) freeTables.size());

		if (freeTables.isEmpty()) {
			response.setTimeSuggestions(
					IntStream.rangeClosed(-6, 6).boxed()
							.map(i -> new Time(desiredDateTime.getTime() + (FIFTEEN_MINUTES_MILLIS * i)))
							.filter(timeSuggestion ->
									!getFreeTables(
											new Timestamp(timeSuggestion.getTime()),
											reservationForm.getRestaurantId(),
											reservationForm.getNumberOfPeople()
									).isEmpty())
							.collect(Collectors.toList())
			);
		} else {
			response.setTimeSuggestions(reservationForm.getTime());
		}

		return response;
	}

	@SuppressWarnings("unchecked")
	private List<RestaurantTable> getFreeTables(Timestamp desiredTime, UUID restaurantId, Integer numberOfChairs) {
		Timestamp fortyFiveMinutesBefore = new Timestamp(desiredTime.getTime());
		fortyFiveMinutesBefore.setTime(fortyFiveMinutesBefore.getTime() - FORTY_FIVE_MINUTES_MILLIS);

		Timestamp oneHourAfter = new Timestamp(desiredTime.getTime());
		oneHourAfter.setTime(oneHourAfter.getTime() + ONE_HOUR_MILLIS);

		List<UUID> potentialTableIds = getSession().createCriteria(RestaurantTable.class)
				.add(Restrictions.eq("restaurantId", restaurantId))
				.add(Restrictions.between("numberOfChairs", numberOfChairs, numberOfChairs + 2))
				.setProjection(Projections.property("id"))
				.list();

		List<UUID> freeTableIds = new ArrayList<>();

		if (potentialTableIds.size() > 0) {
			List <Reservation> reservedTables = getSession().createCriteria(Reservation.class)
					.add(Restrictions.between("startTime", fortyFiveMinutesBefore, oneHourAfter))
					.add(Restrictions.in("table.id", potentialTableIds))
					.add(Restrictions.eq("isConfirmed", true))
					.list();

			freeTableIds.addAll(potentialTableIds.stream().filter(potentialTableId ->
				!reservedTables.stream()
						.map(table -> table.getTable().getId())
						.collect(Collectors.toList())
						.contains(potentialTableId)
			).collect(Collectors.toList()));
		}

		if (freeTableIds.size() > 0) {
			return getSession().createCriteria(RestaurantTable.class)
					.add(Restrictions.in("id", freeTableIds))
					.list();
		} else {
			return new ArrayList<>();
		}
	}


	/**
	 * Post reservation reservation.
	 *
	 * @param reservationForm the reservation form
	 * @return the reservation
	 * @throws Exception the exception
	 */
	public Reservation postReservation(ReservationForm reservationForm) throws Exception {
		Reservation reservation = new Reservation();
		reservation.setStartTime(reservationForm.getDate().getTime() + reservationForm.getTime().getTime() + ONE_HOUR_MILLIS);
		reservation.setReservedOn(System.currentTimeMillis());
		reservation.setConfirmed(false);

		reservation.setTable(
				this.getFreeTables(
						reservation.getStartTime(),
						reservationForm.getRestaurantId(),
						reservationForm.getNumberOfPeople()
				).get(0)
		);

		getSession().save(reservation);

		return reservation;
	}

	/**
	 * Confirm reservation boolean.
	 *
	 * @param reservationConfirmationForm the reservation confirmation form
	 * @return the boolean
	 * @throws Exception the exception
	 */
	public Boolean confirmReservation(ReservationConfirmationForm reservationConfirmationForm) throws Exception {
		getSession().saveOrUpdate(reservationConfirmationForm.getReservation());
		return true;
	}

	/**
	 * Gets my reservations.
	 *
	 * @param user the user
	 * @return the my reservations
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public UserReservations getMyReservations(User user) throws Exception {
		return new UserReservations((List<Reservation>) getSession().createCriteria(Reservation.class)
				.add(Restrictions.eq("user.id", user.getId()))
				.addOrder(Order.asc("startTime"))
				.list());
	}

	/**
	 * Gets all reservations.
	 *
	 * @param restaurantId the restaurant id
	 * @param dateFilter   the date filter
	 * @return the all reservations
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservations(UUID restaurantId, String dateFilter) {
		Timestamp t1 = Timestamp.valueOf(dateFilter + DAY_START_TIME);
		Timestamp t2 = Timestamp.valueOf(dateFilter + DAY_END_TIME);

		return getSession().createCriteria(Reservation.class)
				.createAlias("table", "t")
				.add(Restrictions.eq("t.restaurantId", restaurantId))
				.add(Restrictions.between("startTime", t1, t2))
				.add(Restrictions.eq("isConfirmed", true))
				.list();
	}
}
