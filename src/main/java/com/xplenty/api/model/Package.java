package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;
import java.util.Map;

/**
 * Data model for Xplenty package
 * Author: Xardas
 * Date: 16.12.15
 * Time: 18:08
 */
public class Package extends XplentyObject<Package> {
    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String description;
    @JsonProperty
    protected Map<String, String> variables;
    @JsonProperty("owner_id")
    protected Long ownerId;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty
    protected String url;
    @JsonProperty("html_url")
    protected String htmlUrl;
    @JsonProperty
    protected Xplenty.PackageStatus status;
    @JsonProperty("source_package_id")
    protected Long sourcePackageId;
    @JsonProperty("package_template_id")
    protected Long packageTemplateId;
    @JsonProperty("data_flow_json")
    protected String dataFlowJson;
    @JsonProperty("flow_type")
    protected Xplenty.PackageFlowType flowType;

    public Package() {
        super(Package.class);
    }

    /**
     * Set package id. Used for updates.
     * @param id id of the package
     * @return this instance
     */
    public Package withId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * If it is provided, the new package will be a copy of package with this ID
     * @param sourcePackageId source package id
     * @return this instance
     */
    public Package withSourcePackageId(Long sourcePackageId) {
        this.sourcePackageId = sourcePackageId;
        return this;
    }


    /**
     * If it is provided, the new package will be created based on the template that is associated with this ID
     * @param packageTemplateId template id
     * @return this instance
     */
    public Package fromTemplate(Long packageTemplateId) {
        this.packageTemplateId = packageTemplateId;
        return this;
    }


    /**
     * Data flow prepared in JSON format
     * @param dataFlowJson json string with data flow
     * @return this instance
     */
    public Package withDataFlow(String dataFlowJson) {
        this.dataFlowJson = dataFlowJson;
        return this;
    }

    /**
     * Set package name
     * @param name package name
     * @return this instance
     */
    public Package withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set package description
     * @param description package description
     * @return this instance
     */
    public Package withDescription(String description) {
        this.description = description;
        return this;
    }


    /**
     * Set package variables
     * @param variables variables for this package
     * @return this instance
     */
    public Package withVariables(Map<String, String> variables) {
        this.variables = variables;
        return this;
    }

    /**
     * Set flow type of the package
     * @param flowType flow type of this package
     * @return this instance
     */
    public Package withFlowType(Xplenty.PackageFlowType flowType) {
        this.flowType = flowType;
        return this;
    }


    /**
     *
     * @return the numeric package ID
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the name given to the package upon creation
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the description given to the package upon creation
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the list of package variables
     */
    public Map<String, String> getVariables() {
        return variables;
    }

    /**
     *
     * @return the numeric user id of the package owner
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @return the date and time the package was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return the date and time the package was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @return the package resource URL (API)
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the package resource URL (Web UI)
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @return the package status.
     */
    public Xplenty.PackageStatus getStatus() {
        return status;
    }

    /**
     * Flow type of the package.
     */
    public Xplenty.PackageFlowType getFlowType() {
        return flowType;
    }
}
