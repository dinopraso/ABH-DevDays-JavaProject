package models.tables;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * The type Restaurant review.
 */
@Entity
@Table(name = "restaurant_review")
public class RestaurantReview extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "restaurant_id")
	private UUID restaurantId;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "rating")
	private Integer rating;

	@Column(name = "review")
	private String review;

	/**
	 * Instantiates a new Restaurant review.
	 */
	public RestaurantReview() { }

	/**
	 * Instantiates a new Restaurant review.
	 *
	 * @param restaurantId the restaurant id
	 * @param userId       the user id
	 * @param rating       the rating
	 * @param review       the review
	 */
	public RestaurantReview(UUID restaurantId, UUID userId, Integer rating, String review) {
		this.restaurantId = restaurantId;
		this.userId = userId;
		this.rating = rating;
		this.review = review;
	}


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
	 * Gets restaurant id.
	 *
	 * @return the restaurant id
	 */
	public UUID getRestaurantId() { return restaurantId; }

	/**
	 * Sets restaurant id.
	 *
	 * @param restaurantId the restaurant id
	 */
	public void setRestaurantId(UUID restaurantId) { this.restaurantId = restaurantId; }

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public UUID getUserId() { return userId; }

	/**
	 * Sets user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(UUID userId) { this.userId = userId; }

	/**
	 * Gets rating.
	 *
	 * @return the rating
	 */
	public Integer getRating() { return rating; }

	/**
	 * Sets rating.
	 *
	 * @param rating the rating
	 */
	public void setRating(Integer rating) { this.rating = rating; }

	/**
	 * Gets review.
	 *
	 * @return the review
	 */
	public String getReview() { return review; }

	/**
	 * Sets review.
	 *
	 * @param review the review
	 */
	public void setReview(String review) { this.review = review; }
}
