package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data model for Xplenty Connection Type
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:09
 */
public class ConnectionType extends XplentyObject<ConnectionType> {
    @JsonProperty
    protected String type;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String description;
    @JsonProperty("icon_url")
    protected String iconUrl;
    @JsonProperty
    protected List<ConnectionTypeGroup> groups;


    protected ConnectionType() {
        super(ConnectionType.class);
    }

    /**
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return connection type name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return connection type description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return url of icon
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     *
     * @return list of groups this connection type belongs to
     */
    public List<ConnectionTypeGroup> getGroups() {
        return groups;
    }
}
