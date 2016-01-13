package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for error, that occured while validating package
 * Author: Xardas
 * Date: 10.01.16
 * Time: 19:26
 */
public class PackageValidationError {
    @JsonProperty
    protected String message;
    @JsonProperty("component_id")
    protected String componentId;

    protected PackageValidationError() {
    }

    /**
     *
     * @return error message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return id of component which this error occured at
     */
    public String getComponentId() {
        return componentId;
    }
}
