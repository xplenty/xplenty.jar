/**
 * 
 */
package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty.JobStatus;
import com.xplenty.api.exceptions.XplentyAPIException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Data model for Xplenty job
 * 
 * @author Yuriy Kovalek
 *
 */
public class Job extends XplentyObject<Job> {

    @JsonProperty
	protected Long id;
    @JsonProperty
	protected JobStatus status;
    @JsonProperty
	protected Map<String, String> variables;	//for backwords compatibility
	@JsonProperty("dynamic_variables")
	protected Map<String, String> dynamicVariables;
	@JsonProperty("owner_id")
	protected Long ownerId;
    @JsonProperty
	protected Double progress;
	@JsonProperty("outputs_count")
	protected Integer outputsCount;
    @JsonProperty
	protected List<JobOutput> outputs;
	@JsonProperty("started_at")
	protected Date startedAt;
	@JsonProperty("created_at")
	protected Date createdAt;
    @JsonProperty("completed_at")
    protected Date completedAt;
    @JsonProperty("failed_at")
    protected Date failedAt;
	@JsonProperty("updated_at")
	protected Date updatedAt;
	@JsonProperty("cluster_id")
	protected Long clusterId;
    @JsonProperty
    protected Cluster cluster;
	@JsonProperty("package_id")
	protected Long packageId;
    @JsonProperty("package")
    protected Package xpackage;
    @JsonProperty
	protected String errors;
    @JsonProperty
	protected String url;
	@JsonProperty("runtime_in_seconds")
	protected Long runtimeInSeconds;
    @JsonProperty("html_url")
    protected String htmlUrl;
    @JsonProperty("log_url")
    protected String logUrl;
    @JsonProperty
    protected Creator creator;

    public Job() {
        super(Job.class);
    }

    /**
	 * Shorthand method for {@code waitForStatus(null, JobStatus...)} Will wait forever until the required status is received.
	 * @param statuses see {@link #waitForStatus(Long, JobStatus...)}
	 */
	public void waitForStatus(JobStatus... statuses) {
		waitForStatus(null, statuses);
	}
	
	/**
	 * Blocks execution until required status is received from the Xplenty server, or until timeout occurs.
	 * @param timeout time in seconds before terminating the wait, {@code null} to wait forever
	 * @param statuses list of statuses to wait for, see {@link JobStatus} for the list of supported statuses
	 */
	public void waitForStatus(Long timeout, JobStatus... statuses) {
		if (getParentApiInstance() == null)
			throw new XplentyAPIException("The parent API instance is not set");
		long start = System.currentTimeMillis();
		statusWait:
		while (true) {
			try {
				Thread.sleep(XplentyObject.StatusRefreshInterval);
			} catch (InterruptedException e) {
				throw new XplentyAPIException("Error sleeping", e);
			}
			Job c = getParentApiInstance().jobInformation(id);
			for (JobStatus status: statuses) {
				if (c.getStatus() == status)
					break statusWait;
			}
			if (System.currentTimeMillis() - timeout * 1000 > start)
				throw new XplentyAPIException("Timeout occurred while waiting for required job status");
		}
	}

    /**
     * Download job output log
     * Be aware that this method doesn't store call result anywhere.
     * @return log contents
     */
    @JsonIgnore
    public JobLog getJobLog() {
        return getParentApiInstance().getJobLog(id);
    }
	
	public Job withId(long id) {
		this.id = id;
		return this;
	}
	
	public Job withPackage(long packageId) {
		this.packageId = packageId;
		return this;
	}
	
	public Job onCluster(long clusterId) {
		this.clusterId = clusterId;
		return this;
	}
	
	public Job withVariables(Map<String, String> vars) {
		this.variables = vars;
		return this;
	}
	
	public Job withDynamicVariables(Map<String, String> dynVars) {
		this.dynamicVariables = dynVars;
		return this;
	}


    /**
     *
     * @return the numeric job ID
     */
	public Long getId() {
		return id;
	}

    /**
     *
     * @return the job status.
     */
	public JobStatus getStatus() {
		return status;
	}

    /**
     *
     * @return  a list of the variables supplied to the "run" request
     */
	public Map<String, String> getVariables() {
		return variables;
	}

    /**
     *
     * @return the numeric user ID
     */
	public Long getOwnerId() {
		return ownerId;
	}

    /**
     *
     * @return the job progress in percentages (a value between 0.0 and 1.0)
     */
	public Double getProgress() {
		return progress;
	}

    /**
     *
     * @return  the number of output targets defined in the job's package
     */
	public Integer getOutputsCount() {
		return outputsCount;
	}

    /**
     *
     * @return list of the output targets defined in the job's package
     */
	public List<JobOutput> getOutputs() {
		return outputs;
	}

    /**
     *
     * @return the date and time the job started running
     */
	public Date getStartedAt() {
		return startedAt;
	}

    /**
     *
     * @return the date and time the "run" request was made
     */
	public Date getCreatedAt() {
		return createdAt;
	}

    /**
     *
     * @return the date and time the job failed (if it failed)
     */
    public Date getFailedAt() {
        return failedAt;
    }

    /**
     *
     * @return the date and time at which the job completed (stopped, failed or completed)
     */
    public Date getCompletedAt() {
        return completedAt;
    }

    /**
     *
     * @return the date and time the job was last updated (occurs when package tasks are completed)
     */
	public Date getUpdatedAt() {
		return updatedAt;
	}

    /**
     *
     * @return the ID of the cluster in which the job was run
     */
	public Long getClusterId() {
		return clusterId;
	}

    /**
     *
     * @return the ID of the package that the job ran (or is running)
     */
	public Long getPackageId() {
		return packageId;
	}

    /**
     *
     * @return a textual message describing errors encountered while the job was run
     */
	public String getErrors() {
		return errors;
	}

    /**
     *
     * @return the job resource URL (API)
     */
	public String getUrl() {
		return url;
	}

    /**
     *
     * @return the time in seconds that the job has run up to the current time
     */
	public Long getRuntimeInSeconds() {
		return runtimeInSeconds;
	}

    /**
     *
     * @return the cluster in which the job was run. Includes all attributes.
     */
    public Cluster getCluster() {
        return cluster;
    }

    /**
     *
     * @return the package that the job ran (or is running). Includes all attributes.
     */
    public Package getPackage() {
        return xpackage;
    }

    /**
     *
     * @return the job resource URL (Web UI)
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @return the URL to log summary
     */
    public String getLogUrl() {
        return logUrl;
    }

    /**
     *
     * @return information about resource which created the job
     */
    public Creator getCreator() {
        return creator;
    }

    @SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}
	@SuppressWarnings("unused")
	private void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	@SuppressWarnings("unused")
	private void setStatus(JobStatus status) {
		this.status = status;
	}
	@SuppressWarnings("unused")
	private void setVariables(Map<String, String> variables) {
		this.variables = variables;
	}
	@SuppressWarnings("unused")
	private void setProgress(double progress) {
		this.progress = progress;
	}
	@SuppressWarnings("unused")
	private void setOutputsCount(int outputsCount) {
		this.outputsCount = outputsCount;
	}
	@SuppressWarnings("unused")
	private void setOutputs(List<JobOutput> outputs) {
		this.outputs = outputs;
	}
	@SuppressWarnings("unused")
	private void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    @SuppressWarnings("unused")
    private void setFailedAt(Date failedAt) {
        this.failedAt = failedAt;
    }
    @SuppressWarnings("unused")
    private void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }
	@SuppressWarnings("unused")
	private void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@SuppressWarnings("unused")
	private void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}
	@SuppressWarnings("unused")
	private void setPackageId(long packageId) {
		this.packageId = packageId;
	}
	@SuppressWarnings("unused")
	private void setErrors(String errors) {
		this.errors = errors;
	}
	@SuppressWarnings("unused")
	private void setUrl(String url) {
		this.url = url;
	}
	@SuppressWarnings("unused")
	private void setRuntimeInSeconds(long runtimeInSeconds) {
		this.runtimeInSeconds = runtimeInSeconds;
	}
}
