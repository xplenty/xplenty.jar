/**
 * 
 */
package com.xplenty.api;

/**
 * @author Yuriy Kovalek
 *
 */
public class Xplenty {
	public static enum Resource {
		ClusterPlans("cluster_plans", "List cluster plans"),
		Clusters("clusters", "List clusters"), 
		Cluster("clusters/%s", "Get cluster information"),
		Jobs("jobs", "List jobs"),
		Job("jobs/%s", "Get job info");
		
		public final String value;
		public final String name;
		
		Resource(String val, String name) {
			this.value = val;
			this.name = name;
		}
		
		public String format(String... values) {
			return String.format(value, values);
		}
	}
}
