package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty stack
 * Author: Xardas
 * Date: 08.01.16
 * Time: 20:35
 */
public class Stack extends XplentyObject {
    @JsonProperty
    protected String id;
    @JsonProperty
    protected String name;

    protected Stack() {
        super(Stack.class);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
