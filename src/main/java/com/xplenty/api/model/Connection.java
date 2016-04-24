package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;

/**
 * Data model for connection
 * An Xplenty connection contain access information required to connect to your various data stores.
 * The access information is stored securely and can only be used by your account's members.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 19:55
 */
public class Connection extends XplentyObject<Connection> {
    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty("unique_id")
    protected String uniqueId;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty
    protected Xplenty.ConnectionType type;
    @JsonProperty
    protected String url;

    protected Connection() {
        super(Connection.class);
    }

    /**
     * @return the connection's numeric identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the descriptive name given to the connection
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the unique connection's identifier
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @return the date and time the connection was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the date and time the connection was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the type of the connection.
     */
    public Xplenty.ConnectionType getType() {
        return type;
    }

    /**
     * @return the connection resource URL
     */
    public String getUrl() {
        return url;
    }
}
