package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Author: Xardas
 * Date: 03.01.16
 * Time: 20:50
 */
public class PackageTemplateAuthor {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    protected PackageTemplateAuthor() {
    }

    /**
     *
     * @return the numeric ID of the author
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the full name of the author
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the url for the author's avatar
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }
}
