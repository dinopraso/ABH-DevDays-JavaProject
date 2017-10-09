package models.helpers;

import models.tables.User;

/**
 * The type Session user.
 */
public class SessionUser {
	private Boolean isLoggedIn = false;
	private User object;

	/**
	 * Instantiates a new Session user.
	 */
	public SessionUser() { }

	/**
	 * Instantiates a new Session user.
	 *
	 * @param object the object
	 */
	public SessionUser(User object) {
		this.object = object;
		this.isLoggedIn = true;
	}

	/**
	 * Gets is logged in.
	 *
	 * @return the is logged in
	 */
	public Boolean getIsLoggedIn() { return this.isLoggedIn; }

	/**
	 * Gets object.
	 *
	 * @return the object
	 */
	public User getObject() { return this.object; }
}
