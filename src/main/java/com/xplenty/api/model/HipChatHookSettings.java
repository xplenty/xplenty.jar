package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

/**
 * Settings specific for the Hip Chat hook
 * Author: Xardas
 * Date: 24.01.16
 * Time: 20:00
 */
public class HipChatHookSettings implements HookSettings {
    @JsonProperty
    private String room;
    @JsonProperty("auth_token")
    private String authToken;

    public HipChatHookSettings() {
    }

    public HipChatHookSettings(String room, String authToken) {
        this.room = room;
        this.authToken = authToken;
    }

    /**
     *
     * @param room ID of the Hip Chat room
     * @return this instance
     */
    public HipChatHookSettings withRoom(String room) {
        this.room = room;
        return this;
    }

    /**
     *
     * @param authToken Hip Chat API token
     * @return this instance
     */
    public HipChatHookSettings withAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    /**
     *
     * @return ID of the Hip Chat room
     */
    public String getRoom() {
        return room;
    }

    /**
     *
     * @return Hip Chat API token
     */
    public String getAuthToken() {
        return authToken;
    }

    @Override
    public Xplenty.HookType getType() {
        return Xplenty.HookType.hipchat;
    }
}
