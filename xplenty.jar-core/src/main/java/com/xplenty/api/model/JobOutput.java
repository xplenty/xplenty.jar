/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty.JobStatus;
import com.xplenty.api.exceptions.XplentyAPIException;

/**
 * Data model for Xplenty job
 * 
 * @author Yuriy Kovalek
 *
 */
@XmlRootElement
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobOutput extends XplentyObject<JobOutput> {
	
	public JobOutput() {
		super(JobOutput.class);
	}
	protected Long id;
	protected String name;
	@JsonProperty("records_count")
	protected Long recordsCount;
	protected Double progress;
	@JsonProperty("component_name")
	protected String componentName;
	@JsonProperty("created_at")
	protected Date createdAt;
	@JsonProperty("updated_at")
	protected Date updatedAt;
	@JsonProperty("preview_url")
	protected String previewUrl;
	
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
	public String getComponentName() {
		return componentName;
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
	
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	@SuppressWarnings("unused")
	private void setName(String name) {
		this.name = name;
	}
	@SuppressWarnings("unused")
	private void setRecordsCount(Long recordsCount) {
		this.recordsCount = recordsCount;
	}
	@SuppressWarnings("unused")
	private void setProgress(Double progress) {
		this.progress = progress;
	}
	@SuppressWarnings("unused")
	private void setComponentName(String componentName) {
		this.componentName = componentName;
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
	private void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

}
