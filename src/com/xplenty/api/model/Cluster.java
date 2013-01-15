/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

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
	private long owner_id;
	private long plan_id;
	private Date created_at;
	private Date updated_at;
	private long running_jobs_count;
	private String url;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(long owner_id) {
		this.owner_id = owner_id;
	}
	public long getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(long plan_id) {
		this.plan_id = plan_id;
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
	public long getRunning_jobs_count() {
		return running_jobs_count;
	}
	public void setRunning_jobs_count(long running_jobs_count) {
		this.running_jobs_count = running_jobs_count;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
