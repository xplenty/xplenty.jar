package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

/**
 * Data model for Xplenty package
 * Author: Xardas
 * Date: 16.12.15
 * Time: 18:08
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    protected Package() {
        super(Package.class);
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
     * @return the package resource URL
     */
    public String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    private void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unused")
    private void setDescription(String description) {
        this.description = description;
    }

    @SuppressWarnings("unused")
    private void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    @SuppressWarnings("unused")
    private void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @SuppressWarnings("unused")
    private void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @SuppressWarnings("unused")
    private void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @SuppressWarnings("unused")
    private void setUrl(String url) {
        this.url = url;
    }
}
