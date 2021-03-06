/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.exceptions.XplentyAPIException;

/**
 * Data model for Xplenty cluster
 * 
 * @author Yuriy Kovalek
 *
 */
@XmlRootElement
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cluster extends XplentyObject<Cluster>{
	protected Long id;
	protected String name;
	protected String description;
	protected ClusterStatus status;
	@JsonProperty("owner_id")
	protected Long ownerId;
	protected Integer nodes;
	protected ClusterType type;
	@JsonProperty("created_at")
	protected Date createdAt;
	@JsonProperty("updated_at")
	protected Date updatedAt;
	@JsonProperty("available_since")
	protected Date availableSince;
	@JsonProperty("terminated_at")
	protected Date terminatedAt;
	@JsonProperty("running_jobs_count")
	protected Long runningJobsCount;
	protected String url;
	@JsonProperty("terminate_on_idle")
	protected Boolean terminateOnIdle;
	@JsonProperty("time_to_idle")
	protected Long timeToIdle;
	@JsonProperty("terminated_on_idle")
	protected Boolean terminatedOnIdle;

	public Cluster() {
		super(Cluster.class);
	}
	
	public Cluster withNodes(Integer nodes) {
		this.nodes = nodes;
		return this;
	}
	
	public Cluster withId(Long id) {
		this.id = id;
		return this;
	}
	
	public Cluster named(String name) {
		this.name = name;
		return this;
	}
	
	public Cluster withDescription(String description) {
		this.description = description;
		return this;
	}
	
	public Cluster ofType(ClusterType type) {
		this.type = type;
		return this;
	}
	
	public Cluster withTerminateOnIdle(Boolean terminateOnIdle) {
		this.terminateOnIdle = terminateOnIdle;
		return this;
	}
	
	public Cluster withTimeToIdle(Long timeToIdle) {
		this.timeToIdle = timeToIdle;
		return this;
	}
	
	/**
	 * Shorthand method for {@code waitForStatus(null, ClusterStatus...)} Will wait forever until the required status is received.
	 * @param statuses see {@link #waitForStatus(Long, ClusterStatus...)}
	 */
	public void waitForStatus(ClusterStatus... statuses) {
		waitForStatus(null, statuses);
	}
	
	/**
	 * Blocks execution until required status is received from the Xplenty server, or until timeout occurs.
	 * @param timeout time in seconds before terminating the wait, {@code null} to wait forever
	 * @param statuses list of statuses to wait for, see {@link ClusterStatus} for the list of supported statuses
	 */
	public void waitForStatus(Long timeout, ClusterStatus... statuses) {
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
			Cluster c = getParentApiInstance().clusterInformation(id);
			for (ClusterStatus status: statuses) {
				if (c.getStatus() == status)
					break statusWait;
			}
			if (timeout != null && System.currentTimeMillis() - timeout*1000 > start)
				throw new XplentyAPIException("Timeout occurred while waiting for required cluster status");
		}
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public ClusterStatus getStatus() {
		return status;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public Integer getNodes() {
		return nodes;
	}
	public ClusterType getType() {
		return type;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
    public Date getAvailableSince() {
        return availableSince;
    }
    public Date getTerminatedAt() {
        return terminatedAt;
    }
	public Long getRunningJobsCount() {
		return runningJobsCount;
	}
	public String getUrl() {
		return url;
	}
	public Boolean getTerminateOnIdle() {
		return terminateOnIdle;
	}
	public Long getTimeToIdle() {
		return timeToIdle;
	}
	public Boolean getTerminatedOnIdle() {
		return terminatedOnIdle;
	}

	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}
	@SuppressWarnings("unused")
	private void setName(String name) {
		this.name = name;
	}
	@SuppressWarnings("unused")
	private void setDescription(String description) {
		this.description = description;
	}
	@SuppressWarnings("unused")
	private void setStatus(ClusterStatus status) {
		this.status = status;
	}
	@SuppressWarnings("unused")
	private void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
    @SuppressWarnings("unused")
    private void setNodes(Integer nodes) {
        this.nodes = nodes;
    }
    @SuppressWarnings("unused")
    private void setType(ClusterType type) {
        this.type = type;
    }
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@SuppressWarnings("unused")
	private void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    @SuppressWarnings("unused")
    private void setAvailableSince(Date as) {
        this.availableSince = as;
    }
    @SuppressWarnings("unused")
    private void setTerminatedAt(Date terminatedAt) {
        this.terminatedAt  = terminatedAt;
    }
	@SuppressWarnings("unused")
	private void setRunningJobsCount(long runningJobsCount) {
		this.runningJobsCount = runningJobsCount;
	}
	@SuppressWarnings("unused")
	private void setUrl(String url) {
		this.url = url;
	}
	@SuppressWarnings("unused")
	private void setTerminateOnIdle(Boolean terminateOnIdle) {
		this.terminateOnIdle = terminateOnIdle;
	}
	@SuppressWarnings("unused")
	private void setTimeToIdle(Long timeToIdle) {
		this.timeToIdle= timeToIdle ;
	}
	@SuppressWarnings("unused")
	private void setTerminatedOnIdle(Boolean terminatedOnIdle) {
		this.terminatedOnIdle = terminatedOnIdle;
	}
}
