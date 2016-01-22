package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClusterWatchingLogEntry extends XplentyObject<ClusterWatchingLogEntry>{

    @JsonProperty("created_at")
    private Date creationTimeStamp;
    @JsonProperty("cluster_url")
    private String url;

    public ClusterWatchingLogEntry() {
        super(ClusterWatchingLogEntry.class);
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
