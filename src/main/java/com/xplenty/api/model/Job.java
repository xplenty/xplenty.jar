/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for Xplenty job
 * 
 * @author Yuriy Kovalek
 *
 */
@XmlRootElement
@JsonInclude(Include.NON_NULL)
public class Job {
//	public static class Variables {}
	
	private Long id;
	private String status;
//	private Variables variables;
	private Map<String, String> variables;
	@JsonProperty("owner_id")
	private Long ownerId;
	private Double progress;
	@JsonProperty("outputs_count")
	private Integer outputsCount;
	@JsonProperty("started_at")
	private Date startedAt;
	@JsonProperty("created_at")
	private Date createdAt;
	@JsonProperty("updated_at")
	private Date updatedAt;
	@JsonProperty("cluster_id")
	private Long clusterId;
	@JsonProperty("job_id")
	private Long jobId;
	@JsonProperty("package_id")
	private Long packageId;
	private String errors;
	private String url;
	@JsonProperty("runtime_in_seconds")
	private Long runtimeInSeconds;
	
	public Job withId(long id) {
		this.id = id;
		return this;
	}
	
	public Job withPackage(long packageId) {
		this.jobId = packageId;
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
	
	public Long getId() {
		return id;
	}
	public String getStatus() {
		return status;
	}
	public Map<String, String> getVariables() {
		return variables;
	}
	
	public Long getOwnerId() {
		return ownerId;
	}
	public Double getProgress() {
		return progress;
	}
	public Integer getOutputsCount() {
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
	public Long getClusterId() {
		return clusterId;
	}
	public Long getPackageId() {
		return packageId == null ? jobId : packageId;
	}
	public String getErrors() {
		return errors;
	}
	public String getUrl() {
		return url;
	}
	public Long getRuntimeInSeconds() {
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
