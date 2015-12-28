package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Data model for Xplenty schedule task package
 * Author: Xardas
 * Date: 16.12.15
 * Time: 20:21
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleTaskPackage {
    @JsonProperty("package_id")
    protected Long packageId;
    @JsonProperty
    protected Map<String, String> variables;

    public Long getPackageId() {
        return packageId;
    }

    public Map<String, String> getVariables() {
        return variables;
    }


    @SuppressWarnings("unused")
    private void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    @SuppressWarnings("unused")
    private void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }
}
