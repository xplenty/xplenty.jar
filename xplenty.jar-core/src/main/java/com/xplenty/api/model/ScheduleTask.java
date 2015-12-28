package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.List;

/**
 * Data model for Xplenty schedule task
 * Author: Xardas
 * Date: 16.12.15
 * Time: 19:56
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleTask {

    @JsonProperty
    protected Integer nodes;
    @JsonProperty("terminate_on_idle")
    @JsonDeserialize(using = Number2BooleanJsonConverter.class)
    protected Boolean terminateOnIdle;
    @JsonProperty("time_to_idle")
    protected Integer timeToIdle;
    @JsonProperty
    protected List<ScheduleTaskPackage> packages;


    public Integer getNodes() {
        return nodes;
    }

    public Boolean getTerminateOnIdle() {
        return terminateOnIdle;
    }

    public Integer getTimeToIdle() {
        return timeToIdle;
    }

    public List<ScheduleTaskPackage> getPackages() {
        return packages;
    }


    @SuppressWarnings("unused")
    private void setNodes(Integer nodes) {
        this.nodes = nodes;
    }

    @SuppressWarnings("unused")
    private void setTerminateOnIdle(Boolean terminateOnIdle) {
        this.terminateOnIdle = terminateOnIdle;
    }

    @SuppressWarnings("unused")
    private void setTimeToIdle(Integer timeToIdle) {
        this.timeToIdle = timeToIdle;
    }

    @SuppressWarnings("unused")
    private void setPackages(List<ScheduleTaskPackage> packages) {
        this.packages = packages;
    }

    protected static class Number2BooleanJsonConverter extends JsonDeserializer<Boolean> {

        @Override
        public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return jsonParser.getValueAsInt() == 1;
        }
    }
}
