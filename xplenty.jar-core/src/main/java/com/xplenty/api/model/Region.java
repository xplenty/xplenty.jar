package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty region
 * Author: Xardas
 * Date: 07.01.16
 * Time: 17:52
 */
public class Region extends XplentyObject<Region> {
    @JsonProperty
    protected String id;
    @JsonProperty
    protected String name;
    @JsonProperty("group_name")
    protected String groupName;

    protected Region() {
        super(Region.class);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGroupName() {
        return groupName;
    }
}
