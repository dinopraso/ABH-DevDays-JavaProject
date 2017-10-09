package models.tables;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * The type City.
 */
@Entity
@Table(name = "city")
public class City extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	@Column(name = "bounds")
	private String bounds;

	/**
	 * Instantiates a new City.
	 */
	public City() { }

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
	 * Gets bounds.
	 *
	 * @return the bounds
	 */
	public String getBounds() { return bounds; }

	/**
	 * Sets bounds.
	 *
	 * @param bounds the bounds
	 */
	public void setBounds(String bounds) { this.bounds = bounds; }
}