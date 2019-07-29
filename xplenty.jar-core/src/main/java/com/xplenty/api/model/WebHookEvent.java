package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;

/**
 * Data Model for Xplenty Web Hook event
 * Author: Xardas
 * Date: 04.01.16
 * Time: 18:01
 */
public class WebHookEvent {
    @JsonProperty
    private Long id;
    @JsonProperty
    private Xplenty.WebHookEvent name;
    @JsonIgnore
    private String lastResponse;
    @JsonProperty("last_trigger_status")
    private String lastTriggerStatus;
    @JsonProperty("last_trigger_time")
    private Date lastTriggerTime;

    public WebHookEvent() {}

    public WebHookEvent(Xplenty.WebHookEvent name) {
        this.name = name;
    }

    /**
     *
     * @return id of the event
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return name constant of the event
     */
    public Xplenty.WebHookEvent getName() {
        return name;
    }

    /**
     *
     * @return last response
     */
    public String getLastResponse() {
        return lastResponse;
    }

    /**
     *
     * @return last trigger status
     */
    public String getLastTriggerStatus() {
        return lastTriggerStatus;
    }

    /**
     *
     * @return time web hook was last triggered
     */
    public Date getLastTriggerTime() {
        return lastTriggerTime;
    }
}
