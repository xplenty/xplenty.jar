package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
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


    @SuppressWarnings("unused")
    public void setNodes(Integer nodes) {
        this.nodes = nodes;
    }

    @SuppressWarnings("unused")
    public void setTerminateOnIdle(Boolean terminateOnIdle) {
        this.terminateOnIdle = terminateOnIdle;
    }

    @SuppressWarnings("unused")
    public void setTimeToIdle(Integer timeToIdle) {
        this.timeToIdle = timeToIdle;
    }

    @SuppressWarnings("unused")
    public void setPackages(List<ScheduleTaskPackage> packages) {
        this.packages = packages;
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
