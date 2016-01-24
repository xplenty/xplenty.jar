package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty job log
 * Author: Xardas
 * Date: 08.01.16
 * Time: 19:52
 */
public class JobLog extends XplentyObject<JobLog> {
    @JsonProperty("body")
    protected String log;
    @JsonProperty
    protected String url;

    protected JobLog() {
        super(JobLog.class);
    }

    /**
     *
     * @return log itself
     */
    public String getLog() {
        return log;
    }

    /**
     *
     * @return log resource URL
     */
    public String getUrl() {
        return url;
    }
}
