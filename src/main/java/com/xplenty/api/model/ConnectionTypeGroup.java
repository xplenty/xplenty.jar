package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:10
 */
public class ConnectionTypeGroup {
    @JsonProperty("group_type")
    protected String groupType;
    @JsonProperty("group_name")
    protected String groupName;

    protected ConnectionTypeGroup() {
    }

    /**
     *
     * @return type of group
     */
    public String getGroupType() {
        return groupType;
    }

    /**
     *
     * @return name of group
     */
    public String getGroupName() {
        return groupName;
    }
}
