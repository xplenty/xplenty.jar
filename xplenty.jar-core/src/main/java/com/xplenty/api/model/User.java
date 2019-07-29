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
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String email;
    @JsonProperty("gravatar_email")
    private String gravatarEmail;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("time_zone")
    private String timeZone;
    @JsonProperty
    private String location;
    @JsonProperty
    private Boolean confirmed;
    @JsonProperty("confirmed_at")
    private Date confirmedAt;
    @JsonProperty("notifications_count")
    private Integer notificationsCount;
    @JsonProperty("unread_notifications_count")
    private Integer unreadNotificationsCount;
    @JsonProperty("notification_settings")
    private UserNotificationSettings notificationSettings;
    @JsonProperty("receive_newsletter")
    private Boolean receiveNewsLetter;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty
    private String url;

    @JsonProperty("current_password")
    private String currentPassword;
    @JsonProperty("new_password")
    private String newPassword;

    protected User() {
        super(User.class);
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
    public Boolean getConfirmed() {
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
    public Boolean getReceiveNewsLetter() {
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
