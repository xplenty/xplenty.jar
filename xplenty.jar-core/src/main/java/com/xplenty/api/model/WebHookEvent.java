package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xplenty.api.Xplenty;

import java.io.IOException;
import java.util.Date;

/**
 * Data Model for Xplenty Web Hook event
 * Author: Xardas
 * Date: 04.01.16
 * Time: 18:01
 */
@JsonSerialize(using = WebHookEvent.WebHookEventSerializer.class)
public class WebHookEvent {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonIgnore
    private String lastResponse;
    @JsonProperty("last_trigger_status")
    private String lastTriggerStatus;
    @JsonProperty("last_trigger_time")
    private Date lastTriggerTime;

    public WebHookEvent() {}

    public WebHookEvent(String name) {
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
    public String getName() {
        return name;
    }

    public Xplenty.WebHookEvent getEvent() {
        try {
            return Xplenty.WebHookEvent.valueOf(name);
        } catch (IllegalArgumentException ex) {
            return null;
        }
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

    protected static class WebHookEventSerializer extends JsonSerializer<WebHookEvent> {

        @Override
        public void serialize(WebHookEvent webHookEvent, JsonGenerator jGen, SerializerProvider sp) throws IOException, JsonProcessingException {
            jGen.writeString(webHookEvent.getName());
        }
    }
}
