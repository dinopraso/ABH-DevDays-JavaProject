package models.tables;

import models.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * The type Cuisine.
 */
@Entity
@Table(name = "cuisine")
public class Cuisine extends BaseModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private UUID id;

	@Column(name = "name")
	private String name;

	/**
	 * Instantiates a new Cuisine.
	 */
	public Cuisine() { }

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
}