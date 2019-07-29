package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Data model for Xplenty Notification
 * A notification represents events that you've received by watching clusters or jobs.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 20:28
 */
public class Notification extends XplentyObject<Notification> {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String title;
    @JsonProperty
    private String message;
    @JsonProperty("last_read_at")
    private Date lastReadAt;

    protected Notification() {
        super(Notification.class);
    }

    /**
     *
     * @return the notification's numeric identifier
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the title of the event
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return the description of the event
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return the last point that the notification was checked
     */
    public Date getLastReadAt() {
        return lastReadAt;
    }
}
