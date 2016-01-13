package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Data model for Xplenty user
 * An Xplenty user represents an individual signed up to use the Xplenty platform.
 * Author: Xardas
 * Date: 04.01.16
 * Time: 17:41
 */
public class User extends XplentyObject<User> {

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
    @JsonProperty("time_zone")
    protected String timeZone;
    @JsonProperty
    protected String location;
    @JsonProperty
    protected Boolean confirmed;
    @JsonProperty("confirmed_at")
    protected Date confirmedAt;
    @JsonProperty("notifications_count")
    protected Integer notificationsCount;
    @JsonProperty("unread_notifications_count")
    protected Integer unreadNotificationsCount;
    @JsonProperty("notification_settings")
    protected UserNotificationSettings notificationSettings;
    @JsonProperty("receive_newsletter")
    protected Boolean receiveNewsLetter;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty("api_key")
    protected String apiKey;
    @JsonProperty
    protected String url;
    @JsonProperty("last_login")
    protected Date lastLogin;

    @JsonProperty("current_password")
    private String currentPassword;
    @JsonProperty("new_password")
    private String newPassword;

    public User() {
        super(User.class);
    }

    public User(String currentPassword) {
        super(User.class);
        this.currentPassword = currentPassword;
    }


    /**
     * @return the user's numeric identifier
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the full name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @return the email of the user (also used to login)
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the user's gravatar email
     */
    public String getGravatarEmail() {
        return gravatarEmail;
    }

    /**
     * @return the url for the user's avatar
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @return the user's time zone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @return the user's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return indicates if the user is confirmed
     */
    public Boolean isConfirmed() {
        return confirmed;
    }

    /**
     * @return confirmation date and time
     */
    public Date getConfirmedAt() {
        return confirmedAt;
    }

    /**
     * @return user's notifications count
     */
    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    /**
     * @return user's unread notifications count
     */
    public Integer getUnreadNotificationsCount() {
        return unreadNotificationsCount;
    }

    /**
     * @return user's notification settings
     */
    public UserNotificationSettings getNotificationSettings() {
        return notificationSettings;
    }

    /**
     * @return indicates if user subscribed to recieve newsletter
     */
    public Boolean isReceiveNewsLetter() {
        return receiveNewsLetter;
    }

    /**
     * @return the date and time the user was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the date and time the user was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the user's api authentication key. Returned in response only if user provided password in input parameters.
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @return the user resource URL
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return date and time of the last user login
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     *
     * @param name the full name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param email the email of the user (also used to login)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @param gravatarEmail the user's gravatar email
     */
    public void setGravatarEmail(String gravatarEmail) {
        this.gravatarEmail = gravatarEmail;
    }

    /**
     *
     * @param timeZone the user's time zone
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     *
     * @param location the user's location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @param currentPassword The user's current password, which is required to set new password.
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     *
     * @param newPassword The user's new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     *
     * @param receiveNewsLetter indicates if user subscribed to recieve newsletter
     */
    public void setReceiveNewsLetter(Boolean receiveNewsLetter) {
        this.receiveNewsLetter = receiveNewsLetter;
    }
}
