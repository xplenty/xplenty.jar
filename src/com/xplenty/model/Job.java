/**
 * 
 */
package com.xplenty.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

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
	private long owner_id;
	private double progress;
	private int outputs_count;
	private Date started_at;
	private Date created_at;
	private Date updated_at;
	private long cluster_id;
	private long package_id;
	private String errors;
	private String url;
	private long runtime_in_seconds;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Variables getVariables() {
		return variables;
	}
	public void setVariables(Variables variables) {
		this.variables = variables;
	}
	public long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	public int getOutputs_count() {
		return outputs_count;
	}
	public void setOutputs_count(int output_count) {
		this.outputs_count = output_count;
	}
	public Date getStarted_at() {
		return started_at;
	}
	public void setStarted_at(Date started_at) {
		this.started_at = started_at;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public long getCluster_id() {
		return cluster_id;
	}
	public void setCluster_id(long cluster_id) {
		this.cluster_id = cluster_id;
	}
	public long getPackage_id() {
		return package_id;
	}
	public void setPackage_id(long package_id) {
		this.package_id = package_id;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getRuntime_in_seconds() {
		return runtime_in_seconds;
	}
	public void setRuntime_in_seconds(long runtime_in_seconds) {
		this.runtime_in_seconds = runtime_in_seconds;
	}
}
