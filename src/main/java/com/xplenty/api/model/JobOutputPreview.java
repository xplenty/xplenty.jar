package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty job output preview
 * Author: Xardas
 * Date: 11.01.16
 * Time: 18:24
 */
public class JobOutputPreview extends XplentyObject<JobOutputPreview> {
    @JsonProperty
    protected String preview;
    @JsonProperty
    protected String url;

    protected JobOutputPreview() {
        super(JobOutputPreview.class);
    }

    /**
     *
     * @return output preview
     */
    public String getPreview() {
        return preview;
    }

    /**
     *
     * @return the job preview URL
     */
    public String getUrl() {
        return url;
    }
}
