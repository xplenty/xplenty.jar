package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;
import java.util.List;

/**
 * Data model for Xplenty Package Validation
 * An Xplenty package validation contains information about status of the validation process for the package.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 20:31
 */
public class PackageValidation extends XplentyObject<PackageValidation> {
    @JsonProperty
    private Long id;
    @JsonProperty
    private Xplenty.PackageValidationStatus status;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonProperty
    private Long runtime;
    @JsonProperty("package_id")
    private Long packageId;
    @JsonProperty("owner_id")
    private Long ownerId;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty
    private List<PackageValidationError> errors;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty
    private String url;

    protected PackageValidation() {
        super(PackageValidation.class);
    }

    /**
     * @return the numeric package validation ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the status of the validation process.
     */
    public Xplenty.PackageValidationStatus getStatus() {
        return status;
    }

    /**
     * @return information about current status
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * @return current duration of the validation process
     */
    public Long getRuntime() {
        return runtime;
    }

    /**
     * @return the numeric package ID
     */
    public Long getPackageId() {
        return packageId;
    }

    /**
     * @return the numeric user ID of the package validation owner
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     * @return the numeric ID of the account, where package is assigned
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * @return the list of the errors which were detected in the validation process
     */
    public List<PackageValidationError> getErrors() {
        return errors;
    }

    /**
     * @return the date and time the validation was started
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the date and time the validation status was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @return the package validation resource URL
     */
    public String getUrl() {
        return url;
    }
}
