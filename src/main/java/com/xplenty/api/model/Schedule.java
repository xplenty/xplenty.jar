package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;

/**
 * Data model for Xplenty schedule
 * Author: Xardas
 * Date: 16.12.15
 * Time: 19:36
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schedule extends XplentyObject<Schedule> {
    public Schedule() {
        super(Schedule.class);
    }
    
    @JsonProperty
    protected Long id;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String description;
    @JsonProperty("owner_id")
    protected Long ownerId;
    @JsonProperty
    protected Xplenty.ScheduleStatus status;
    @JsonProperty("start_at")
    protected Date startAt;
    @JsonProperty("next_run_at")
    protected Date nextRunAt;
    @JsonProperty("interval_amount")
    protected Long intervalAmount;
    @JsonProperty("interval_unit")
    protected Xplenty.ScheduleIntervalUnit intervalUnit;
    @JsonProperty("last_run_at")
    protected Date lastRunAt;
    @JsonProperty("last_run_status")
    protected String lastRunStatus;
    @JsonProperty("execution_count")
    protected Long executionCount;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;
    @JsonProperty
    protected String url;
    @JsonProperty("html_url")
    protected String htmlUrl;
    @JsonProperty
    protected ScheduleTask task;
    @JsonProperty
    protected Boolean overlap;
    @JsonProperty("reuse_cluster_strategy")
    protected Xplenty.ReuseClusterStrategy reuseClusterStrategy;

    /**
     *
     * @return the numeric schedule ID
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @return the name given to the schedule upon creation
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the numeric user id of the package owner
     */
    public Long getOwnerId() {
        return ownerId;
    }

    /**
     *
     * @return the description given to the schedule upon creation
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return the schedule's status
     */
    public Xplenty.ScheduleStatus getStatus() {
        return status;
    }

    /**
     *
     * @return the date and time when the schedule should start executing
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     *
     * @return the date and time the schedule's task will run next
     */
    public Date getNextRunAt() {
        return nextRunAt;
    }

    /**
     *
     * @return number of interval units between schedule's task executions
     */
    public Long getIntervalAmount() {
        return intervalAmount;
    }

    /**
     *
     * @return time unit used for periodic execution
     */
    public Xplenty.ScheduleIntervalUnit getIntervalUnit() {
        return intervalUnit;
    }

    /**
     *
     * @return  the date and time that schedule's task ran last
     */
    public Date getLastRunAt() {
        return lastRunAt;
    }

    /**
     *
     * @return status of the last execution of the schedule's task
     */
    public String getLastRunStatus() {
        return lastRunStatus;
    }

    /**
     *
     * @return number of times the schedule has run
     */
    public Long getExecutionCount() {
        return executionCount;
    }

    /**
     *
     * @return the date and time the schedule was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @return the date and time the schedule was updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @return the schedule resource URL (API)
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return the schedule resource URL (Web UI)
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @return task that will be executed on schedule
     */
    public ScheduleTask getTask() {
        return task;
    }

    /**
     *
     * @return if execution overlapping is allowed
     */
    public Boolean getOverlap() {
        return overlap;
    }

    /**
     *
     * @return the strategy of re-using cluster
     */
    public Xplenty.ReuseClusterStrategy getReuseClusterStrategy() {
        return reuseClusterStrategy;
    }
    
    public Schedule withId(Long id) {
        this.id = id;
        return this;
    }
    
    public Schedule withName(String name) {
        this.name = name;
        return this;
    }


    public Schedule withDescription(String description) {
        this.description = description;
        return this;
    }


    public Schedule withStatus(Xplenty.ScheduleStatus status) {
        this.status = status;
        return this;
    }


    public Schedule withStartAt(Date startAt) {
        this.startAt = startAt;
        return this;
    }


    public Schedule withIntervalAmount(Long intervalAmount) {
        this.intervalAmount = intervalAmount;
        return this;
    }


    public Schedule withIntervalUnit(Xplenty.ScheduleIntervalUnit intervalUnit) {
        this.intervalUnit = intervalUnit;
        return this;
    }


    public Schedule withTask(ScheduleTask task) {
        this.task = task;
        return this;
    }

    public Schedule withOverlap(Boolean overlap) {
        this.overlap = overlap;
        return this;
    }

    public Schedule withReuseClusterStrategy(Xplenty.ReuseClusterStrategy reuseClusterStrategy) {
        this.reuseClusterStrategy = reuseClusterStrategy;
        return this;
    }
}
