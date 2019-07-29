package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.List;

/**
 * Data model for Xplenty Web hook
 * An Xplenty web hook is kind of notification that will be fired as a HTTP request to specified URL when state of resource (cluster or job) is changed.
 * Types of notifications can be specified with events variable.
 * Notification request contains hash which is generated using SHA-1 from string created by concatenation of the following fields:
 * <ul>
 * <li>id</li>
 * <li>event name</li>
 * <li>url</li>
 * <li>salt</li>
 * </ul>
 * Notified application can verify with salt if request was sent by Xplenty.
 * Author: Xardas
 * Date: 04.01.16
 * Time: 17:42
 */
public class WebHook extends XplentyObject<WebHook> {
    @JsonProperty
    private Long id;
    @JsonProperty
    private Boolean active;
    @JsonProperty
    private WebHookSettings settings;
    @JsonProperty
    private String salt;
    @JsonProperty
    private List<WebHookEvent> events;
    @JsonProperty("add_events")
    private List<Xplenty.WebHookEvent> addEvents;
    @JsonProperty("remove_events")
    private List<Xplenty.WebHookEvent> removeEvents;

    protected WebHook() {
        super(WebHook.class);
    }

    public WebHook(WebHookSettings settings, List<WebHookEvent> events) {
        super(WebHook.class);
        this.settings = settings;
        this.events = events;
    }

    public WebHook(Long id, WebHookSettings settings, List<Xplenty.WebHookEvent> addEvents, List<Xplenty.WebHookEvent> removeEvents) {
        super(WebHook.class);
        this.addEvents = addEvents;
        this.removeEvents = removeEvents;
        this.id = id;
        this.settings = settings;
    }

    public WebHook(Long id, Boolean active) {
        super(WebHook.class);
        this.id = id;
        this.active = active;
    }

    /**
     *
     * @return the numeric hook ID
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return indicates whether the web hook is active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @return settings specific for the web hook. It contains the following attributes
     */
    public WebHookSettings getSettings() {
        return settings;
    }

    /**
     *
     * @return salt needed for verification
     */
    public String getSalt() {
        return salt;
    }

    /**
     *
     * @return list of notification events.
     */
    public List<WebHookEvent> getEvents() {
        return events;
    }
}
