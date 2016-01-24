/**
 * 
 */
package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.exceptions.XplentyAPIException;

import java.util.Date;
import java.util.List;

/**
 * Data model for Xplenty cluster
 * 
 * @author Yuriy Kovalek
 *
 */
public class Cluster extends XplentyObject<Cluster>{

    @JsonProperty
	protected Long id;
    @JsonProperty
	protected String name;
    @JsonProperty
	protected String description;
    @JsonProperty
	protected ClusterStatus status;
	@JsonProperty("owner_id")
	protected Long ownerId;
    @JsonProperty
	protected Integer nodes;
    @JsonProperty
	protected ClusterType type;
	@JsonProperty("created_at")
	protected Date createdAt;
	@JsonProperty("updated_at")
	protected Date updatedAt;
	@JsonProperty("available_since")
	protected Date availableSince;
	@JsonProperty("terminated_at")
	protected Date terminatedAt;
    @JsonProperty("launched_at")
    protected Date launchedAt;
	@JsonProperty("running_jobs_count")
	protected Long runningJobsCount;
    @JsonProperty
	protected String url;
	@JsonProperty("terminate_on_idle")
	protected Boolean terminateOnIdle;
	@JsonProperty("time_to_idle")
	protected Long timeToIdle;
	@JsonProperty("terminated_on_idle")
	protected Boolean terminatedOnIdle;
    @JsonProperty("plan_id")
    protected String planId;
    @JsonProperty("idle_since")
    protected Date idleSince;
    @JsonProperty
    protected String region;
    @JsonProperty("master_instance_type")
    protected String masterInstanceType;
    @JsonProperty("slave_instance_type")
    protected String slaveInstanceType;
    @JsonProperty("master_spot_price")
    protected Double masterSpotPrice;
    @JsonProperty("slave_spot_price")
    protected Double slaveSpotPrice;
    @JsonProperty("master_spot_percentage")
    protected Double masterSpotPercentage;
    @JsonProperty("slave_spot_percentage")
    protected Double slaveSpotPercentage;
    @JsonProperty("allow_fallback")
    protected Boolean allowFallback;
    @JsonProperty("html_url")
    protected String htmlUrl;
    @JsonProperty
    protected Creator creator;
    @JsonProperty
    protected String stack;
    @JsonProperty("bootstrap_actions")
    protected List<ClusterBootstrapAction> bootstrapActions;
    @JsonProperty
    protected String zone;

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

    public Cluster withRegion(String region) {
        this.region = region;
        return this;
    }

    public Cluster withZone(String zone) {
        this.zone = zone;
        return this;
    }

    public Cluster withMasterInstanceType(String masterInstanceType) {
        this.masterInstanceType = masterInstanceType;
        return this;
    }

    public Cluster withSlaveInstanceType(String slaveInstanceType) {
        this.slaveInstanceType = slaveInstanceType;
        return this;
    }

    public Cluster withMasterSpotPrice(Double masterSpotPrice) {
        this.masterSpotPrice = masterSpotPrice;
        return this;
    }

    public Cluster withSlaveSpotPrice(Double slaveSpotPrice) {
        this.slaveSpotPrice = slaveSpotPrice;
        return this;
    }

    public Cluster withMasterSpotPercentage(Double masterSpotPercentage) {
        this.masterSpotPercentage = masterSpotPercentage;
        return this;
    }

    public Cluster withSlaveSpotPercentage(Double slaveSpotPercentage) {
        this.slaveSpotPercentage = slaveSpotPercentage;
        return this;
    }

    public Cluster withAllowFallback(Boolean allowFallback) {
        this.allowFallback = allowFallback;
        return this;
    }

    public Cluster withStack(String stack) {
        this.stack = stack;
        return this;
    }

    public Cluster withBootstrapActions(List<ClusterBootstrapAction> bootstrapActions) {
        this.bootstrapActions = bootstrapActions;
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

    /**
     *
     * @return the cluster's numeric identifier
     */
	public Long getId() {
		return id;
	}

    /**
     *
     * @return the name given to the cluster upon creation
     */
	public String getName() {
		return name;
	}

    /**
     *
     * @return the description given to the cluster upon creation
     */
	public String getDescription() {
		return description;
	}

    /**
     *
     * @return the cluster's status
     */
	public ClusterStatus getStatus() {
		return status;
	}

    /**
     *
     * @return the numeric user ID of the cluster's owner
     */
	public Long getOwnerId() {
		return ownerId;
	}

    /**
     *
     * @return the number of compute nodes for the cluster
     */
	public Integer getNodes() {
		return nodes;
	}

    /**
     *
     * @return the type of the cluster
     */
	public ClusterType getType() {
		return type;
	}

    /**
     *
     * @return the date and time the cluster was created
     */
	public Date getCreatedAt() {
		return createdAt;
	}

    /**
     *
     * @return the date and time the cluster was last updated
     */
	public Date getUpdatedAt() {
		return updatedAt;
	}

    /**
     *
     * @return the date and time the cluster became available
     */
    public Date getAvailableSince() {
        return availableSince;
    }

    /**
     *
     * @return the date and time the cluster was terminated
     */
    public Date getTerminatedAt() {
        return terminatedAt;
    }

    /**
     *
     * @return the date and time the cluster was launched at
     */
    public Date getLaunchedAt() {
        return launchedAt;
    }

    /**
     *
     * @return the number of jobs currently running on the cluster
     */
	public Long getRunningJobsCount() {
		return runningJobsCount;
	}

    /**
     *
     * @return the cluster resource URL (API)
     */
	public String getUrl() {
		return url;
	}

    /**
     *
     * @return indicates whether the cluster will be terminated after it becomes idle
     */
	public Boolean getTerminateOnIdle() {
		return terminateOnIdle;
	}

    /**
     *
     * @return the time interval (in seconds) in which the cluster will become idle
     */
	public Long getTimeToIdle() {
		return timeToIdle;
	}

    /**
     *
     * @return indicates whether the cluster terminated because it became idle
     */
	public Boolean getTerminatedOnIdle() {
		return terminatedOnIdle;
	}

    /**
     *
     * @return the ID of the cluster's plan
     */
    public String getPlanId() {
        return planId;
    }

    /**
     *
     * @return the time since cluster changed status to idle
     */
    public Date getIdleSince() {
        return idleSince;
    }

    /**
     *
     * @return the region in which the cluster was created
     */
    public String getRegion() {
        return region;
    }

    /**
     *
     * @return the type of the master instance
     */
    public String getMasterInstanceType() {
        return masterInstanceType;
    }

    /**
     *
     * @return the type of the slave instance
     */
    public String getSlaveInstanceType() {
        return slaveInstanceType;
    }

    /**
     *
     * @return the maximum bid price (in USD) requested for master spot instance
     */
    public Double getMasterSpotPrice() {
        return masterSpotPrice;
    }

    /**
     *
     * @return the maximum bid price (in USD) requested for slave spot instance
     */
    public Double getSlaveSpotPrice() {
        return slaveSpotPrice;
    }

    /**
     *
     * @return the percentage of master instances requested as spot (value between 0 and 1)
     */
    public Double getMasterSpotPercentage() {
        return masterSpotPercentage;
    }

    /**
     *
     * @return the percentage of slave instances requested as spot (value between 0 and 1)
     */
    public Double getSlaveSpotPercentage() {
        return slaveSpotPercentage;
    }

    /**
     *
     * @return indicates whether instances will be created as on-demand instances if spot requests are not fulfilled
     */
    public Boolean getAllowFallback() {
        return allowFallback;
    }

    /**
     *
     * @return the cluster resource URL (Web UI)
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @return information about resource which created the job.
     */
    public Creator getCreator() {
        return creator;
    }

    /**
     *
     * @return the stack of the cluster.
     */
    public String getStack() {
        return stack;
    }

    /**
     *
     * @return the array of the custom bootstrap actions.
     */
    public List<ClusterBootstrapAction> getBootstrapActions() {
        return bootstrapActions;
    }

    /**
     *
     * @return The zone in which the cluster was created (for availability zone supported regions)
     */
    public String getZone() {
        return zone;
    }
}
