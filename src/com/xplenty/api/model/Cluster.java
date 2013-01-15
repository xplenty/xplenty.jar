/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yuriy Kovalek
 *
 */
@XmlRootElement
public class Cluster {
	private long id;
	private String name;
	private String description;
	private String status;
	@JsonProperty("owner_id")
	private long ownerId;
	@JsonProperty("plan_id")
	private long planId;
	@JsonProperty("created_at")
	private Date createdAt;
	@JsonProperty("updated_at")
	private Date updatedAt;
	@JsonProperty("running_jobs_count")
	private long runningJobsCount;
	private String url;
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getStatus() {
		return status;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public long getPlanId() {
		return planId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public long getRunningJobsCount() {
		return runningJobsCount;
	}
	public String getUrl() {
		return url;
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
	private void setStatus(String status) {
		this.status = status;
	}
	@SuppressWarnings("unused")
	private void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	@SuppressWarnings("unused")
	private void setPlanId(long planId) {
		this.planId = planId;
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
	private void setRunningJobsCount(long runningJobsCount) {
		this.runningJobsCount = runningJobsCount;
	}
	@SuppressWarnings("unused")
	private void setUrl(String url) {
		this.url = url;
	}
}
