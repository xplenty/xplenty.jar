/**
 * 
 */
package com.xplenty.api;

import java.util.List;

import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterPlan;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.ClusterInfo;
import com.xplenty.api.request.JobInfo;
import com.xplenty.api.request.ListClusterPlans;
import com.xplenty.api.request.ListClusters;
import com.xplenty.api.request.ListJobs;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyAPI {
		
	private final XplentyWebConnector connector;
	
	public XplentyAPI(String accountName, String apiKey) {
		connector = new XplentyWebConnector(accountName, apiKey);
	}

	public List<ClusterPlan> listClusterPlans() {
		return connector.execute(new ListClusterPlans());
	}

	public List<Cluster> listClusters() {
		return connector.execute(new ListClusters());
	}
	
	public Cluster clusterInformation(long clusterId) {
		return connector.execute(new ClusterInfo(clusterId));
	}
	
	public List<Job> listJobs() {
		return connector.execute(new ListJobs());
	}
	
	public Job jobInformation(long jobId) {
		return connector.execute(new JobInfo(jobId));
	}

	public String getAccountName() {
		return connector.getAccountName();
	}

	public String getApiKey() {
		return connector.getApiKey();
	}
	
	public static void main (String[] args) {
		XplentyAPI api = new XplentyAPI("javasdk", "V4eyfgNqYcSasXGhzNxS");
		List<Job> jobs = api.listJobs();
		System.out.println(jobs);
	}
}
