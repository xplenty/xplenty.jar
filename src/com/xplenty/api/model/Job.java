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
public class Job {
	public static class Variables {}
	
	private long id;
	private String status;
	private Variables variables;
	@JsonProperty("owner_id")
	private long ownerId;
	private double progress;
	@JsonProperty("outputs_count")
	private int outputsCount;
	@JsonProperty("started_at")
	private Date startedAt;
	@JsonProperty("created_at")
	private Date createdAt;
	@JsonProperty("updated_at")
	private Date updatedAt;
	@JsonProperty("cluster_id")
	private long clusterId;
	@JsonProperty("package_id")
	private long packageId;
	private String errors;
	private String url;
	@JsonProperty("runtime_in_seconds")
	private long runtimeInSeconds;
	
	public long getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
	public Variables getVariables() {
		return variables;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public double getProgress() {
		return progress;
	}
	public int getOutputsCount() {
		return outputsCount;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public long getClusterId() {
		return clusterId;
	}
	public long getPackageId() {
		return packageId;
	}
	public String getErrors() {
		return errors;
	}
	public String getUrl() {
		return url;
	}
	public long getRuntimeInSeconds() {
		return runtimeInSeconds;
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
	private void setStatus(String status) {
		this.status = status;
	}
	@SuppressWarnings("unused")
	private void setVariables(Variables variables) {
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
	private void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
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
