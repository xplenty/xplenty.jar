package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

/**
 * Settings specific for the Slack hook.
 * Author: Xardas
 * Date: 24.01.16
 * Time: 19:07
 */
public class SlackHookSettings implements HookSettings {
    @JsonProperty
    private String url;
    @JsonProperty
    private String team;
    @JsonProperty
    private String channel;
    @JsonProperty
    private String username;

    public SlackHookSettings() {}

    public SlackHookSettings(String url, String team, String channel, String username) {
        this.url = url;
        this.team = team;
        this.channel = channel;
        this.username = username;
    }

    /**
     *
     * @param url URL containing unique token which allows to send messages on the Slack channel.
     * @return this instance
     */
    public SlackHookSettings withUrl(String url) {
        this.url = url;
    	return this;
	}

    /**
     *
     * @param team Slack team
     * @return this instance
     */
    public SlackHookSettings withTeam(String team) {
        this.team = team;
    	return this;
	}

    /**
     *
     * @param channel Slack channel
     * @return this instance
     */
    public SlackHookSettings withChannel(String channel) {
        this.channel = channel;
    	return this;
	}

    /**
     *
     * @param username Slack username
     * @return this instance
     */
    public SlackHookSettings withUsername(String username) {
        this.username = username;
    	return this;
	}

    /**
     *
     * @return URL containing unique token which allows to send messages on the Slack channel.
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return Slack team
     */
    public String getTeam() {
        return team;
    }

    /**
     *
     * @return Slack channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     *
     * @return Slack username
     */
    public String getUsername() {
        return username;
    }

    @Override
    public Xplenty.HookType getType() {
        return Xplenty.HookType.slack;
    }
}
