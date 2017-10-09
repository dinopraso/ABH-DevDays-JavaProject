package exceptions;

/**
 * The type Service exception.
 */
public class ServiceException extends Exception {
	/**
	 * The Error.
	 */
	public String error = "Bad Request";
	/**
	 * The Message.
	 */
	public String message;

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param message the message
	 */
	public ServiceException(String message) {
		this.message = message;
	}

	/**
	 * Instantiates a new Service exception.
	 *
	 * @param error   the error
	 * @param message the message
	 */
	public ServiceException(String error, String message) {
		this(message);
		this.error = error;
	}
}
