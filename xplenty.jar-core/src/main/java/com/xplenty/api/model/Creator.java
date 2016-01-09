package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty job creator
 * Author: Xardas
 * Date: 08.01.16
 * Time: 18:41
 */
public class Creator {
    @JsonProperty
    protected String type;
    @JsonProperty
    protected Long id;
    @JsonProperty("display_name")
    protected String displayName;
    @JsonProperty
    protected String url;
    @JsonProperty("html_url")
    protected String htmlUrl;

    protected Creator() {
    }

    /**
     *
     * @return the type of the resource (e.g. Schedule)
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return the numeric resource ID
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     *
     * @return API url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return Web url
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }
}
