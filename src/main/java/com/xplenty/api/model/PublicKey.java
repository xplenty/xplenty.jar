package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Data model for Xplenty public key
 * A public key represent public SSH key associated with a user and is used to authorize users with cluster instances.
 * Author: Xardas
 * Date: 04.01.16
 * Time: 17:24
 */
public class PublicKey extends XplentyObject<PublicKey> {

    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String comment;
    @JsonProperty
    protected String fingerprint;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty
    protected String url;
    @JsonProperty("public_key")
    private String publicKey;

    protected PublicKey() {
        super(PublicKey.class);
    }

    public PublicKey(String name, String publicKey) {
        super(PublicKey.class);
        this.publicKey = publicKey;
        this.name = name;
    }

    /**
     *
     * @return the key's numeric identifier
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the descriptive name given to the key
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the comment part of the given key
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @return fingerprint of the key
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     *
     * @return the date and time the key was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return the date and time the key was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @return the key resource URL
     */
    public String getUrl() {
        return url;
    }
}
