package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Xardas
 * Date: 04.01.16
 * Time: 19:00
 */
public class UserNotificationSettings {
    @JsonProperty
    private Boolean email;
    @JsonProperty
    private Boolean web;

    protected UserNotificationSettings() {
    }

    public UserNotificationSettings(Boolean email, Boolean web) {
        this.email = email;
        this.web = web;
    }

    /**
     *
     * @return whether email notifications enabled
     */
    public Boolean getEmail() {
        return email;
    }

    /**
     *
     * @return whether web notifications enabled
     */
    public Boolean getWeb() {
        return web;
    }
}
