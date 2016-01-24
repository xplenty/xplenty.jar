package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.List;

/**
 * Data model for Xplenty schedule task
 * Author: Xardas
 * Date: 16.12.15
 * Time: 19:56
 */
public class ScheduleTask {

    @JsonProperty
    protected Integer nodes;
    @JsonProperty("terminate_on_idle")
    @JsonDeserialize(using = Number2BooleanJsonConverter.class)
    protected Boolean terminateOnIdle;
    @JsonProperty("time_to_idle")
    protected Integer timeToIdle;
    @JsonProperty
    @JsonSerialize(using = PackageSerializer.class)
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

    public ScheduleTask withNodes(Integer nodes) {
        this.nodes = nodes;
        return this;
    }

    public ScheduleTask withTerminateOnIdle(Boolean terminateOnIdle) {
        this.terminateOnIdle = terminateOnIdle;
        return this;
    }

    public ScheduleTask withTimeToIdle(Integer timeToIdle) {
        this.timeToIdle = timeToIdle;
        return this;
    }

    public ScheduleTask withPackages(List<ScheduleTaskPackage> packages) {
        this.packages = packages;
        return this;
    }

    protected static class Number2BooleanJsonConverter extends JsonDeserializer<Boolean> {

        @Override
        public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return jsonParser.getValueAsInt() == 1;
        }
    }

    protected static class PackageSerializer extends JsonSerializer<List<ScheduleTaskPackage>> {
        @Override
        public void serialize(List<ScheduleTaskPackage> packageList, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            if (packageList == null) {
                return;
            }
            jgen.writeStartObject();
            for (int i = 0; i < packageList.size(); i++) {
                jgen.writeFieldName(String.valueOf(i));
                serializerProvider.defaultSerializeValue(packageList.get(i), jgen);
            }
            jgen.writeEndObject();
        }
    }
}
