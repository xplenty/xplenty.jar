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

    /**
     *
     * @return Preview (up to 100 rows) of this job output
     */
    public JobOutputPreview getPreview() {
        if (this.getParentApiInstance() == null) {
            throw new XplentyAPIException("The parent API instance is not set");
        }
        return getParentApiInstance().previewJobOutput(jobId, id);
    }

    /**
     *
     * @return job output id
     */
	public Long getId() {
		return id;
	}

    /**
     *
     * @return job output name (filename, link, etc)
     */
	public String getName() {
		return name;
	}

    /**
     *
     * @return number of records in this job output
     */
	public Long getRecordsCount() {
		return recordsCount;
	}

    /**
     *
     * @return job output progress
     */
	public Double getProgress() {
		return progress;
	}

    /**
     *
     * @return component object (related to package destination component)
     */
    public Component getComponent() {
        return component;
    }

    /**
     *
     * @return date of creation
     */
    public Date getCreatedAt() {
		return createdAt;
	}

    /**
     *
     * @return date of update
     */
	public Date getUpdatedAt() {
		return updatedAt;
	}

    /**
     *
     * @return Resource URL for job output preview
     */
	public String getPreviewUrl() {
		return previewUrl;
	}

    /**
     *
     * @return type of preview (json, csv etc)
     */
    public String getPreviewType() {
        return previewType;
    }

    /**
     *
     * @return Resource URL for this job output
     */
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

        /**
         *
         * @return component name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @return component type
         */
        public String getType() {
            return type;
        }

        /**
         *
         * @return output fields
         */
        public List<String> getFields() {
            return fields;
        }
    }

    protected void setJobId(Long jobId) {
        this.jobId = jobId;
    }

}
