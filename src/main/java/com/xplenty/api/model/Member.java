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
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String email;
    @JsonProperty("gravatar_email")
    protected String gravatarEmail;
    @JsonProperty("avatar_url")
    protected String avatarUrl;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty
    protected String location;
    @JsonProperty
    protected Boolean confirmed;
    @JsonProperty("confirmed_at")
    protected Date confirmedAt;
    @JsonProperty
    protected Xplenty.AccountRole role;
    @JsonProperty
    protected Boolean owner;
    @JsonProperty
    protected String url;
    @JsonProperty("html_url")
    protected String htmlUrl;

    protected Member() {
        super(Member.class);
    }

    public Member(String email, Xplenty.AccountRole role, String name) {
        super(Member.class);
        this.email = email;
        this.role = role;
        this.name = name;
    }

    public Member(Long id, Xplenty.AccountRole role) {
        super(Member.class);
        this.id = id;
        this.role = role;
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
