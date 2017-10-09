package models.helpers;

import models.helpers.forms.ReservationForm;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Reservation inquiry response.
 */
public class ReservationInquiryResponse {

	private static final String DATE_PATTERN = "HH:mm:ss";

	private ReservationForm inquiry;
	private Long numberOfTablesLeft;
	private Long numberOfReservationsToday;
	private List<Time> timeSuggestions;

	private final SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);

	private  ReservationInquiryResponse() {}

	/**
	 * Gets object.
	 *
	 * @return the object
	 */
	public static ReservationInquiryResponse getObject() {
		return new ReservationInquiryResponse();
	}

	/**
	 * Gets inquiry.
	 *
	 * @return the inquiry
	 */
	public ReservationForm getInquiry() { return this.inquiry; }

	/**
	 * Sets inquiry.
	 *
	 * @param inquiry the inquiry
	 * @return the inquiry
	 */
	public ReservationInquiryResponse setInquiry(ReservationForm inquiry) {
		this.inquiry = inquiry;
		return this;
	}

	/**
	 * Gets number of tables left.
	 *
	 * @return the number of tables left
	 */
	public Long getNumberOfTablesLeft() { return this.numberOfTablesLeft; }

	/**
	 * Sets number of tables left.
	 *
	 * @param numberOfTablesLeft the number of tables left
	 * @return the number of tables left
	 */
	public ReservationInquiryResponse setNumberOfTablesLeft(Long numberOfTablesLeft) {
		this.numberOfTablesLeft = numberOfTablesLeft;
		return this;
	}

	/**
	 * Gets number of reservations today.
	 *
	 * @return the number of reservations today
	 */
	public Long getNumberOfReservationsToday() { return this.numberOfReservationsToday; }

	/**
	 * Sets number of reservations today.
	 *
	 * @param numberOfReservationsToday the number of reservations today
	 * @return the number of reservations today
	 */
	public ReservationInquiryResponse setNumberOfReservationsToday(Long numberOfReservationsToday) {
		this.numberOfReservationsToday = numberOfReservationsToday;
		return this;
	}

	/**
	 * Gets time suggestions.
	 *
	 * @return the time suggestions
	 */
	public List<Time> getTimeSuggestions() { return this.timeSuggestions; }

	public ReservationInquiryResponse setTimeSuggestions(Time timeSuggestion) {
		this.setTimeSuggestions(Arrays.asList(timeSuggestion));
		return this;
	}

	/**
	 * Sets time suggestions.
	 *
	 * @param timeSuggestions the time suggestions
	 * @return the time suggestions
	 */
	@SuppressWarnings("unchecked")
	public ReservationInquiryResponse setTimeSuggestions(final List<?> timeSuggestions) {
		if (!timeSuggestions.isEmpty()) {
			if (timeSuggestions.get(0) instanceof Time) {
				this.setTimeSuggestionsFromTime((List<Time>) timeSuggestions);
			} else if (timeSuggestions.get(0) instanceof String) {
				this.setTimeSuggestionsFromString((List<String>) timeSuggestions);
			}
		}
		return this;
	}

	private void setTimeSuggestionsFromTime(final List<Time> timeSuggestions) {
		this.timeSuggestions = timeSuggestions;
	}

	private void setTimeSuggestionsFromString(final List<String> timeSuggestions) {
		this.timeSuggestions = timeSuggestions.stream().map(this::timeFromString).collect(Collectors.toList());
	}

	private Time timeFromString(final String timeString) {
		try {
			return new Time(df.parse(timeString).getTime());
		} catch (ParseException e) {
			return null;
		}
	}
}
