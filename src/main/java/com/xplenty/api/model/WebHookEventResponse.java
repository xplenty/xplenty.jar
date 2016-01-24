package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Xardas
 * Date: 19.01.16
 * Time: 20:31
 */
public class WebHookEventResponse {
    @JsonProperty
    private String code;
    @JsonProperty
    private String body;

    protected WebHookEventResponse() {}

    public String getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }
}
