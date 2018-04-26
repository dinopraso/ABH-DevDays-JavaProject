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
@Table(name = "logs")
public class Log extends BaseModel {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "logging_time")
    private long logging_time;

    @Column(name = "description")
    private String description;

    /**
     * Instantiates a new City.
     */
    public Log() { }

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
    public long getLogging_time() { return logging_time; }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setLogging_time(long logging_time) { this.logging_time = logging_time; }

    /**
     * Gets bounds.
     *
     * @return the bounds
     */
    public String getDescription() { return description; }

    /**
     * Sets bounds.
     *
     * @param bounds the bounds
     */
    public void setDescription(String description) { this.description = description; }
}