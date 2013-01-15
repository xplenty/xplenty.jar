/**
 * 
 */
package com.xplenty.api.model;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Yuriy Kovalek
 *
 */
@XmlRootElement
public class ClusterPlan {
	private long id;
	private String name;
	
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}
	@SuppressWarnings("unused")
	private void setName(String name) {
		this.name = name;
	}
}
