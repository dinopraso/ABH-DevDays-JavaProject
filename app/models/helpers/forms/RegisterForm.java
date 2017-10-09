package models.helpers.forms;

import models.BaseModel;
import models.tables.User;

import java.util.UUID;

/**
 * The type Register form.
 */
public class RegisterForm extends BaseModel {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private UUID cityId;

    /**
     * Instantiates a new Register form.
     */
    public RegisterForm() { }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

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
    public void setPassword(String password) { this.password = password; }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) { this.phone = phone; }

    /**
     * Sets city id.
     *
     * @param cityId the city id
     */
    public void setCityId(UUID cityId) { this.cityId = cityId; }

    /**
     * Create account account.
     *
     * @return the account
     */
    public User createAccount() {
        User newUser = new User(this.firstName, this.lastName, this.email, this.password, this.cityId);
        newUser.setAddress(this.address);
        newUser.setPhone(this.phone);
        return newUser;
    }
}
