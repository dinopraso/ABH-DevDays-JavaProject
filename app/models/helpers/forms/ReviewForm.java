package models.helpers.forms;

import models.BaseModel;

import java.util.UUID;

/**
 * The type Review form.
 */
public class ReviewForm extends BaseModel {

	private UUID restaurantId;
	private Integer reviewScore;
	private String reviewText;

	/**
	 * Instantiates a new Review form.
	 */
	public ReviewForm() { }

	/**
	 * Gets restaurant id.
	 *
	 * @return the restaurant id
	 */
	public UUID getRestaurantId()  { return this.restaurantId; }

	/**
	 * Sets restaurant id.
	 *
	 * @param restaurantId the restaurant id
	 */
	public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }

	/**
	 * Gets review text.
	 *
	 * @return the review text
	 */
	public String getReviewText() { return this.reviewText; }

	/**
	 * Sets review text.
	 *
	 * @param reviewText the review text
	 */
	public void setReviewText(String reviewText) { this.reviewText = reviewText; }

	/**
	 * Gets review score.
	 *
	 * @return the review score
	 */
	public Integer getReviewScore() { return this.reviewScore; }

	/**
	 * Sets review score.
	 *
	 * @param reviewScore the review score
	 */
	public void setReviewScore(Integer reviewScore) { this.reviewScore = reviewScore; }
}
