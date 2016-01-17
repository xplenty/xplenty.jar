package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Data model for Xplenty product update
 * Author: Xardas
 * Date: 11.01.16
 * Time: 17:51
 */
public class ProductUpdate extends XplentyObject<ProductUpdate> {
    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String title;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty
    protected String body;
    @JsonProperty("body_html")
    protected String bodyHtml;
    @JsonProperty("body_text")
    protected String bodyText;
    @JsonProperty("likes_count")
    protected Long likes;
    @JsonProperty("liked")
    protected Boolean liked;

    protected ProductUpdate() {
        super(ProductUpdate.class);
    }

    /**
     *
     * @return product update id. It can be used to like it.
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return product update title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return product update creation date
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return product update body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @return product update body in html format
     */
    public String getBodyHtml() {
        return bodyHtml;
    }

    /**
     *
     * @return product update body in plain text
     */
    public String getBodyText() {
        return bodyText;
    }

    /**
     *
     * @return current number of likes
     */
    public Long getLikes() {
        return likes;
    }

    /**
     *
     * @return have you liked it or not
     */
    public Boolean getLiked() {
        return liked;
    }
}
