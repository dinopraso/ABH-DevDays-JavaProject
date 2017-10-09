package controllers;

import exceptions.ServiceException;
import models.tables.User;
import play.cache.CacheApi;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.Callable;

/**
 * The type Base controller.
 */
abstract class BaseController extends Controller {

	/**
	 * The Cache.
	 */
	CacheApi cache;

	/**
	 * The Form factory.
	 */
	@Inject
	FormFactory formFactory;

	/**
	 * Sets cache.
	 *
	 * @param cache the cache
	 */
	@Inject
	public void setCache(CacheApi cache) {
		this.cache = cache;
	}

	private Result catchExceptions(final Callable block) {
		try {
			return (Result) block.call();
		} catch (ServiceException se) {
			String message = se.getMessage() != null ? se.getMessage() : "Unknown Error";
			return badRequest(Json.toJson(se.getMessage()));
		} catch (Exception e) {
			String message = e.getMessage() != null ? e.getMessage() : "Unknown Error";
			return internalServerError(Json.toJson(e.getMessage()));
		}
	}

	/**
	 * Wrapper for publicly available methods.
	 *
	 * @param block the block
	 * @return the result
	 */
	Result wrapForPublic(final Callable block) {
		return catchExceptions(() -> ok(Json.toJson(block.call())));
	}

	/**
	 * Wrapper for registered users.
	 *
	 * @param block the block
	 * @return the result
	 */
	Result wrapForUser(final Callable block) {
		return catchExceptions(() -> {
			User user = this.cache.get(session("uid"));
			if (user != null) {
				return wrapForPublic(block);
			} else {
				return unauthorized("You must be logged in to have access to this Request");
			}
		});
	}

	/**
	 * Wrapper for administrators.
	 *
	 * @param block the block
	 * @return the result
	 */
	Result wrapForAdmin(final Callable block) {
		return catchExceptions(() -> {
			User user = this.cache.get(session("uid"));
			if (user != null && user.getIsAdmin()) {
				return wrapForPublic(block);
			} else {
				return unauthorized("Only Administrators have access to this Request");
			}
		});
	}

	/**
	 * Gets query parameter as Integer.
	 *
	 * @param query        the query
	 * @param defaultValue the default value
	 * @return the query integer
	 */
	Integer getQueryInt(final String query, final Integer defaultValue) {
		try {
			return Integer.parseInt(query);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}
}
