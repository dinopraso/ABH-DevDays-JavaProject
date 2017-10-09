package services;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * A utility class to hash passwords and check passwords vs hashed values. It uses a combination of hashing and unique
 * salt. The algorithm used is PBKDF2WithHmacSHA1 which, although not the best for hashing password (vs. bcrypt) is
 * still considered robust and <a href="http://security.stackexchange.com/a/6415/12614"> recommended by NIST </a>.
 * The hashed value has 256 bits.
 */
public class Passwords {

	private static final Random RANDOM = new SecureRandom();
	private static final Integer ITERATIONS = 10000;
	private static final Integer KEY_LENGTH = 256;

	private static final String SECRET_KEY_FACTORY_KEY = "PBKDF2WithHmacSHA1";

	/**
	 * static utility class
	 */
	private Passwords() { }

	/**
	 * Returns a random salt to be used to hash a password.
	 *
	 * @return a 16 bytes random salt
	 */
	public static byte[] getNextSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	/**
	 * Returns a salted and hashed password using the provided hash.<br>
	 * Note - side effect: the password is destroyed (the char[] is filled with zeros)
	 *
	 * @param password the password to be hashed
	 * @param salt     a 16 bytes salt, ideally obtained with the getNextSalt method
	 *
	 * @return the hashed password with a pinch of salt
	 */
	public static byte[] hash(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY_KEY);
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
		} finally {
			spec.clearPassword();
		}
	}

	/**
	 * Returns true if the given password and salt match the hashed value, false otherwise.<br>
	 * Note - side effect: the password is destroyed (the char[] is filled with zeros)
	 *
	 * @param password     the password to check
	 * @param salt         the salt used to hash the password
	 * @param expectedHash the expected hashed value of the password
	 *
	 * @return true if the given password and salt match the hashed value, false otherwise
	 */
	public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
		byte[] pwdHash = hash(password, salt);
		Arrays.fill(password, Character.MIN_VALUE);
		if (pwdHash.length != expectedHash.length) return false;
		for (int i = 0; i < pwdHash.length; i++) {
			if (pwdHash[i] != expectedHash[i]) return false;
		}
		return true;
	}
}