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
    protected Schedule() {
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
    @JsonProperty
    protected ScheduleTask task;

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
     * @return the schedule resource ID
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return task that will be executed on schedule
     */
    public ScheduleTask getTask() {
        return task;
    }


	@SuppressWarnings("unused")
    private void setId(Long id) {
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
    private void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

	@SuppressWarnings("unused")
    private void setStatus(Xplenty.ScheduleStatus status) {
        this.status = status;
    }

	@SuppressWarnings("unused")
    private void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

	@SuppressWarnings("unused")
    private void setNextRunAt(Date nextRunAt) {
        this.nextRunAt = nextRunAt;
    }

	@SuppressWarnings("unused")
    private void setIntervalAmount(Long intervalAmount) {
        this.intervalAmount = intervalAmount;
    }

	@SuppressWarnings("unused")
    private void setIntervalUnit(Xplenty.ScheduleIntervalUnit intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

	@SuppressWarnings("unused")
    private void setLastRunAt(Date lastRunAt) {
        this.lastRunAt = lastRunAt;
    }

	@SuppressWarnings("unused")
    private void setLastRunStatus(String lastRunStatus) {
        this.lastRunStatus = lastRunStatus;
    }

	@SuppressWarnings("unused")
    private void setExecutionCount(Long executionCount) {
        this.executionCount = executionCount;
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
    private void setUrl(String url) {
        this.url = url;
    }

	@SuppressWarnings("unused")
    private void setTask(ScheduleTask task) {
        this.task = task;
    }
}
