package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty package template
 * An Xplenty package template a data flow definition that is predefined in application.
 * It describes the data to process (location, schema, fields), data manipulation to perform,
 * and the output destinations (location, schema). The package's workflow is implemented by jobs.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 20:45
 */
public class PackageTemplate extends XplentyObject<PackageTemplate> {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty
    private Integer position;
    @JsonProperty
    private PackageTemplateAuthor author;

    protected PackageTemplate() {
        super(PackageTemplate.class);
    }

    /**
     *
     * @return the numeric package template ID
     */
    public Long getId() {
        return id;
    }


    /**
     *
     * @return  the name given to the package template upon creation
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the description given to the package template upon creation
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the index for ordering the template
     */
    public Integer getPosition() {
        return position;
    }

    /**
     *
     * @return information about the template's owner
     */
    public PackageTemplateAuthor getAuthor() {
        return author;
    }
}
