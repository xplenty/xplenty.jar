package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    protected Long id;
    @JsonProperty("account_id")
    protected String accountId;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String uname;
    @JsonProperty
    protected String region;
    @JsonProperty
    protected String location;
    @JsonProperty("billing_email")
    protected String billingEmail;
    @JsonProperty("gravatar_email")
    protected String gravatarEmail;
    @JsonProperty("avatar_url")
    protected String avatarUrl;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty("schedules_count")
    protected Integer schedulesCount;
    @JsonProperty("connections_count")
    protected Integer connectionsCount;
    @JsonProperty
    protected Xplenty.AccountRole role;
    @JsonProperty("owner_id")
    protected Long ownerId;
    @JsonProperty("members_count")
    protected Integer membersCount;
    @JsonProperty("packages_count")
    protected Integer packagesCount;
    @JsonProperty("jobs_count")
    protected Integer jobsCount;
    @JsonProperty("running_jobs_count")
    protected Integer runningJobsCount;
    @JsonProperty("hooks_count")
    protected Integer hooksCount;
    @JsonProperty
    protected String url;
    @JsonProperty("public_key")
    protected String publicKey;
    @JsonIgnore
    protected String currentAccountId;


    protected Account() {
        super(Account.class);
    }

    public Account(String name, String region, String accountId) {
        super(Account.class);
        this.accountId = accountId;
        this.name = name;
        this.region = region;
    }

    public Account(String accountId) {
        super(Account.class);
        this.accountId = accountId;
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
     * @return the name given to the account upon creation
     */
    public String getName() {
        return name;
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
     * @return the number of service hooks for the account
     */
    public Integer getHooksCount() {
        return hooksCount;
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

    /**
     *
     * @return account current unique string identifier
     */
    public String getCurrentAccountId() {
        return currentAccountId != null ? currentAccountId : accountId;
    }

    /**
     *
     * @param accountId the account's unique identifier
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @param name  the name given to the account
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param region the account's region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     *
     * @param location the account's location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @param billingEmail the account's billing email
     */
    public void setBillingEmail(String billingEmail) {
        this.billingEmail = billingEmail;
    }

    /**
     *
     * @param gravatarEmail the account's gravatar email
     */
    public void setGravatarEmail(String gravatarEmail) {
        this.gravatarEmail = gravatarEmail;
    }
}
