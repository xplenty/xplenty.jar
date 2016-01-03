package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;

/**
 * Data model for Xplenty Member
 * A member represents a user that has been given access to an Xplenty account.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 20:12
 */
public class Member extends XplentyObject<Member> {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String email;
    @JsonProperty("gravatar_email")
    private String gravatarEmail;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty
    private String location;
    @JsonProperty
    private Boolean confirmed;
    @JsonProperty("confirmed_at")
    private Date confirmedAt;
    @JsonProperty
    private Xplenty.AccountRole role;
    @JsonProperty
    private Boolean owner;
    @JsonProperty
    private String url;
    @JsonProperty("html_url")
    private String htmlUrl;

    protected Member() {
        super(Member.class);
    }

    /**
     *
     * @return the users's numeric identifier
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the full name of the user
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the email of the user (also used to login)
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return the user's gravatar email
     */
    public String getGravatarEmail() {
        return gravatarEmail;
    }

    /**
     *
     * @return the url for the user's avatar
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     *
     * @return the date and time the account was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return the date and time the account was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @return the user's location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return indicates if the user is confirmed
     */
    public Boolean getConfirmed() {
        return confirmed;
    }

    /**
     *
     * @return confirmation date and time
     */
    public Date getConfirmedAt() {
        return confirmedAt;
    }

    /**
     *
     * @return the user's role in the sepcified account
     */
    public Xplenty.AccountRole getRole() {
        return role;
    }

    /**
     *
     * @return indicator if the user is the owner of the specified account
     */
    public Boolean getOwner() {
        return owner;
    }

    /**
     *
     * @return the member resource url (API)
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the member resource url (Web UI)
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

}
