package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;

/**
 * Data model for Xplenty account
 * An Xplenty account represents a related group (usually a company) of Xplenty users.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 18:52
 */
public class Account extends XplentyObject<Account> {
    @JsonProperty
    private Long id;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty
    private String uname;
    @JsonProperty
    private String region;
    @JsonProperty
    private String location;
    @JsonProperty("billing_email")
    private String billingEmail;
    @JsonProperty("gravatar_email")
    private String gravatarEmail;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("schedules_count")
    private Integer schedulesCount;
    @JsonProperty("connections_count")
    private Integer connectionsCount;
    @JsonProperty
    private Xplenty.AccountRole role;
    @JsonProperty("owner_id")
    private Long ownerId;
    @JsonProperty("members_count")
    private Integer membersCount;
    @JsonProperty("packages_count")
    private Integer packagesCount;
    @JsonProperty("jobs_count")
    private Integer jobsCount;
    @JsonProperty("running_jobs_count")
    private Integer runningJobsCount;
    @JsonProperty
    private String url;
    @JsonProperty("public_key")
    private String publicKey;


    private Account() {
        super(Account.class);
    }


    /**
     *
     * @return the account's numeric identifier
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the account's unique identifier
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     *
     * @return the account's numeric identifier with u_ prefix
     */
    public String getUname() {
        return uname;
    }

    /**
     *
     * @return the account's region
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @return the account's location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @return the account's billing email
     */
    public String getBillingEmail() {
        return billingEmail;
    }

    /**
     *
     * @return the account's gravatar email
     */
    public String getGravatarEmail() {
        return gravatarEmail;
    }

    /**
     *
     * @return the url for the account's avatar
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
     * @return the number of schedules for the account
     */
    public Integer getSchedulesCount() {
        return schedulesCount;
    }

    /**
     *
     * @return the number of connections for the account
     */
    public Integer getConnectionsCount() {
        return connectionsCount;
    }

    /**
     *
     * @return the member's role in the account
     */
    public Xplenty.AccountRole getRole() {
        return role;
    }

    /**
     *
     * @return the numeric identifier of the account's owner
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @return the number of members in the account
     */
    public Integer getMembersCount() {
        return membersCount;
    }

    /**
     *
     * @return the number of packages for the account
     */
    public Integer getPackagesCount() {
        return packagesCount;
    }

    /**
     *
     * @return the number of jobs for the account
     */
    public Integer getJobsCount() {
        return jobsCount;
    }

    /**
     *
     * @return the number of running jobs for the account
     */
    public Integer getRunningJobsCount() {
        return runningJobsCount;
    }

    /**
     *
     * @return the account resource URL
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the account ssh public key generated upon creation
     */
    public String getPublicKey() {
        return publicKey;
    }

}
