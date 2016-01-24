package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Data model for Xplenty schedule task package
 * Author: Xardas
 * Date: 16.12.15
 * Time: 20:21
 */
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

    public ScheduleTaskPackage withPackageId(Long packageId) {
        this.packageId = packageId;
        return this;
    }

    public ScheduleTaskPackage withVariables(Map<String, String> variables) {
        this.variables = variables;
        return this;
    }
}
