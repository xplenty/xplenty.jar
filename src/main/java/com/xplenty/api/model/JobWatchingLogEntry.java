package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobWatchingLogEntry extends XplentyObject<JobWatchingLogEntry>{

    @JsonProperty("created_at")
    private Date creationTimeStamp;
    @JsonProperty("job_url")
    private String url;

    public JobWatchingLogEntry() {
        super(JobWatchingLogEntry.class);
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }
    @SuppressWarnings("unused")
    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public String getUrl() {
        return url;
    }
    @SuppressWarnings("unused")
    public void setUrl(String url) {
        this.url = url;
    }
}
