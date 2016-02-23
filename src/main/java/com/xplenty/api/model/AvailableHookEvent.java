package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:15
 */
public class AvailableHookEvent extends XplentyObject<AvailableHookEvent> {
    @JsonProperty
    protected String id;
    @JsonProperty("group_name")
    protected String groupName;
    @JsonProperty("name")
    protected String description;

    protected AvailableHookEvent() {
        super(AvailableHookEvent.class);
    }

    /**
     *
     * @return id of the hook event. This id can be passed to hook create/update requests to subscribe for that type of event
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return Group that this hook event belongs to
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @return description of the event
     */
    public String getDescription() {
        return description;
    }
}
