package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Xardas
 * Date: 09.01.16
 * Time: 15:54
 */
public class Timezone extends XplentyObject<Timezone> {
    @JsonProperty
    protected String id;
    @JsonProperty
    protected String name;

    protected Timezone() {
        super(Timezone.class);
    }

    /**
     *
     * @return id of timezone
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return name of timezone
     */
    public String getName() {
        return name;
    }
}
