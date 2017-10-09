package controllers;

import models.helpers.SessionUser;
import models.helpers.forms.LoginForm;
import models.helpers.forms.RegisterForm;
import models.tables.User;
import play.db.jpa.Transactional;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;
import java.util.UUID;

/**
 * The type User controller.
 */
public class UserController extends BaseController {

	private UserService service;

	/**
	 * Sets service.
	 *
	 * @param service the service
	 */
	@Inject
	public void setService(UserService service) {
		this.service = service;
	}

	/**
	 * Login result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result login() {
		return wrapForPublic(() -> {
			User user = this.service.authenticate(formFactory.form(LoginForm.class).bindFromRequest().get());

			UUID id = UUID.randomUUID();
			session().clear();
			session("uid", id.toString());
			this.cache.set(id.toString(), user);

			return user;
		});
	}

	/**
	 * Logout result.
	 *
	 * @return the result
	 */
	@Transactional(readOnly = true)
	public Result logout() {
		return wrapForPublic(() -> {
			this.cache.remove(session("uid"));
			session().clear();
			return "{}";
		});
	}

	/**
	 * Register result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result register() {
		return wrapForPublic(() -> {
			User user = this.service.register(formFactory.form(RegisterForm.class).bindFromRequest().get());
			UUID id = UUID.randomUUID();
			session().clear();
			session("uid", id.toString());
			this.cache.set(id.toString(), user);
			return user;
		});
	}

	/**
	 * Gets current user.
	 *
	 * @return the current user
	 */
	@Transactional(readOnly = true)
	public Result getCurrentUser() {
		return wrapForPublic(() -> {
			User user = this.cache.get(session("uid"));
			if (user != null) {
				return new SessionUser(user);
			} else {
				return new SessionUser();
			}
		});
	}

	/**
	 * Gets all users.
	 *
	 * @return the all users
	 */
	@Transactional(readOnly = true)
	public Result getAllUsers() {
		return wrapForAdmin(() -> this.service.getAllUsers());
	}

	/**
	 * Gets user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	@Transactional(readOnly = true)
	public Result getUser(String userId) {
		return wrapForAdmin(() -> this.service.getUser(UUID.fromString(userId)));
	}

	/**
	 * Edit user result.
	 *
	 * @return the result
	 */
	@Transactional
	public Result editUser() {
		return wrapForAdmin(() -> this.service.editUser(formFactory.form(User.class).bindFromRequest().get()));
	}

	/**
	 * Delete user result.
	 *
	 * @param id the id
	 * @return the result
	 */
	@Transactional
	public Result deleteUser(String id) {
		return wrapForAdmin(() -> this.service.deleteUser(UUID.fromString(id)));
	}
}
