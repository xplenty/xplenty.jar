package com.xplenty.api.model;

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
@JsonSerialize(using = HookEvent.WebHookEventSerializer.class)
public class HookEvent {
    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty("last_response")
    protected WebHookEventResponse lastResponse;
    @JsonProperty("last_trigger_status")
    protected String lastTriggerStatus;
    @JsonProperty("last_trigger_time")
    protected Date lastTriggerTime;

    public HookEvent() {}

    public HookEvent(String name) {
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

    public Xplenty.HookEvent getEvent() {
        try {
            return Xplenty.HookEvent.fromString(name);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     *
     * @return last response
     */
    public WebHookEventResponse getLastResponse() {
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

    protected static class WebHookEventSerializer extends JsonSerializer<HookEvent> {

        @Override
        public void serialize(HookEvent hookEvent, JsonGenerator jGen, SerializerProvider sp) throws IOException, JsonProcessingException {
            jGen.writeString(hookEvent.getName());
        }
    }
}
