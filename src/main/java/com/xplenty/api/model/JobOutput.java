/**
 * 
 */
package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.exceptions.XplentyAPIException;

import java.util.Date;
import java.util.List;

/**
 * Data model for Xplenty job
 * 
 * @author Yuriy Kovalek
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOutput extends XplentyObject<JobOutput> {
    @JsonProperty
	protected Long id;
    @JsonProperty
	protected String name;
	@JsonProperty("records_count")
	protected Long recordsCount;
    @JsonProperty
	protected Double progress;
	@JsonProperty
	protected Component component;
	@JsonProperty("created_at")
	protected Date createdAt;
	@JsonProperty("updated_at")
	protected Date updatedAt;
	@JsonProperty("preview_url")
	protected String previewUrl;
    @JsonProperty("preview_type")
    protected String previewType;
    @JsonProperty
    protected String url;
    @JsonIgnore
    protected Long jobId;

    public JobOutput() {
        super(JobOutput.class);
    }

    public JobOutputPreview getPreview() {
        if (this.getParentApiInstance() == null) {
            throw new XplentyAPIException("The parent API instance is not set");
        }
        return getParentApiInstance().previewJobOutput(jobId, id);
    }
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Long getRecordsCount() {
		return recordsCount;
	}
	public Double getProgress() {
		return progress;
	}
    public Component getComponent() {
        return component;
    }
    public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public String getPreviewUrl() {
		return previewUrl;
	}
    public String getPreviewType() {
        return previewType;
    }
    public String getUrl() {
        return url;
    }

    public static class Component {
        @JsonProperty
        protected String name;
        @JsonProperty
        protected String type;
        @JsonProperty
        protected List<String> fields;

        protected Component() {
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public List<String> getFields() {
            return fields;
        }
    }

    protected void setJobId(Long jobId) {
        this.jobId = jobId;
    }

}
