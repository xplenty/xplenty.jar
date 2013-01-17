/**
 * 
 */
package com.xplenty.api.model;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Data model for Xplenty cluster plan
 * 
 * @author Yuriy Kovalek
 *
 */
@XmlRootElement
public class ClusterPlan {
	private Long id;
	private String name;
	
	public Long getId() {
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
