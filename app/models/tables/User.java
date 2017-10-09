package models.tables;

import models.BaseModel;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jpa.HibernateEntityManager;
import play.db.jpa.JPA;
import services.Passwords;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Base64;
import java.util.UUID;

/**
 * The type User.
 */
@Entity
@Table(name = "\"user\"")
public class User extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "city_id", referencedColumnName = "id")
	private City city;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "hash")
	private String hash;

	@Column(name = "salt")
	private String salt;

	@Column(name = "is_admin")
	private Boolean isAdmin;

	/**
	 * Instantiates a new User.
	 */
	public User() { }

	/**
	 * Instantiates a new User.
	 *
	 * @param firstName the first name
	 * @param lastName  the last name
	 * @param email     the email
	 * @param password  the password
	 * @param cityId    the city id
	 */
	public User(String firstName, String lastName, String email, String password, UUID cityId) {
		this.name = firstName + " " + lastName;
		this.email = email;
		this.setPassword(password);
		this.city = (City)((HibernateEntityManager) JPA.em()).getSession().createCriteria(City.class).add(Restrictions.eq("id", cityId)).uniqueResult();
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
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() { return name; }

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) { this.name = name; }

	/**
	 * Gets address.
	 *
	 * @return the address
	 */
	public String getAddress() { return address; }

	/**
	 * Sets address.
	 *
	 * @param address the address
	 */
	public void setAddress(String address) { this.address = address; }

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	public City getCity() { return city; }

	/**
	 * Sets city.
	 *
	 * @param city the city
	 */
	public void setCity(City city) { this.city = city; }

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	public String getPhone() { return phone; }

	/**
	 * Sets phone.
	 *
	 * @param phone the phone
	 */
	public void setPhone(String phone) { this.phone = phone; }

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail() { return email; }

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email) { this.email = email; }

	/**
	 * Sets password.
	 *
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.salt = base64Encode(Passwords.getNextSalt());
		this.hash = base64Encode(Passwords.hash(password.toCharArray(), base64Decode(this.salt)));
	}

	private String base64Encode(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}

	private byte[] base64Decode(String string) {
		return Base64.getDecoder().decode(string);
	}

	/**
	 * Gets is admin.
	 *
	 * @return the is admin
	 */
	public Boolean getIsAdmin() { return isAdmin; }

	/**
	 * Sets is admin.
	 *
	 * @param admin the admin
	 */
	public void setIsAdmin(Boolean admin) { isAdmin = admin; }
}
