/**
 * 
 */
package com.xplenty.api;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterPlan;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.ClusterInfo;
import com.xplenty.api.request.CreateCluster;
import com.xplenty.api.request.JobInfo;
import com.xplenty.api.request.ListClusterPlans;
import com.xplenty.api.request.ListClusters;
import com.xplenty.api.request.ListJobs;
import com.xplenty.api.request.RunJob;
import com.xplenty.api.request.StopJob;
import com.xplenty.api.request.TerminateCluster;
import com.xplenty.api.util.Http;
/**
 * A convenience class for making HTTP requests to the Xplenty API for a given user. An underlying {@link XplentyWebConnectivity} is created
 * for each instance of XplentyAPI.
 * <p/>
 * Example usage:
 * <pre>{@code
 *		XplentyAPI api = new XplentyAPI("accountName", "apiKey");
 *List<ClusterPlan> plans = api.listClusterPlans();
 * }
 * </pre>
 * 
 * {@link RuntimeException} will be thrown for any request failures.
 * 
 * @author Yuriy Kovalek
 */
public class XplentyAPI extends XplentyWebConnectivity {
		
	/**
     * Constructs a XplentyAPI with a {@link XplentyWebConnectivity} based on API key, account name,
     * and internal configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey User's API key found at //TODO add link to API key source
     */
	public XplentyAPI(String accountName, String apiKey) {
		super(accountName, apiKey);
	}
	
	public XplentyAPI withVersion(Version ver) {
		this.setVersion(ver);
		return this;
	}
	
	public XplentyAPI withHost(String host) {
		this.setHost(host);
		return this;
	}
	
	public XplentyAPI withProtocol(Http.Protocol proto) {
		this.setProtocol(proto);
		return this;
	}

	/**
	 * Information about available cluster plans
	 * @return
	 */
	public List<ClusterPlan> listClusterPlans() {
		return this.execute(new ListClusterPlans());
	}

	/**
	 * List of clusters associated with the account
	 * @return
	 */
	public List<Cluster> listClusters() {
		return listClusters(new Properties());
	}
	
	/**
	 * List of clusters associated with the account
	 * @param props map of request parameters, see {@link Xplenty.ClusterStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link ListClusters}
	 * @return
	 */
	public List<Cluster> listClusters(Properties props) {
		return this.execute(new ListClusters(props));
	}
	/**
	 * Information about a particular cluster
	 * @param clusterId id of the cluster, see {@link #listClusters()} to get a list of clusters with id's
	 * @return
	 */
	public Cluster clusterInformation(long clusterId) {
		return this.execute(new ClusterInfo(clusterId)).withParentApiInstance(this);
	}
	
	/**
	 * Create cluster with specified properties
	 * @param planId cluster plan id
	 * @param name cluster name
	 * @param description cluster description
	 * @return
	 */
	public Cluster createCluster(long planId, String name, String description) {
		return this.execute(new CreateCluster(new Cluster().onPlan(planId).named(name).withDescription(description))).withParentApiInstance(this);
	}
	
	/**
	 * Terminate cluster with given id
	 * @param clusterId
	 * @return
	 */
	public Cluster terminateCluster(long clusterId) {
		return this.execute(new TerminateCluster(clusterId)).withParentApiInstance(this);
	}
	
	/**
	 * List of jobs associated with the account
	 * @return
	 */
	public List<Job> listJobs() {
		return listJobs(new Properties());
	}
	
	/**
	 * List of jobs associated with the account
	 * @param props map of request parameters, see {@link Xplenty.JobStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link ListJobs}
	 * @return
	 */
	public List<Job> listJobs(Properties params) {
		return this.execute(new ListJobs(params));
	}
	
	/**
	 * Information about a particular job
	 * @param jobId id of the job, see {@link #listJobs()} to get a list of jobs with id's
	 * @return
	 */
	public Job jobInformation(long jobId) {
		return this.execute(new JobInfo(jobId)).withParentApiInstance(this);
	}
	
	/**
	 * Execute a job on a cluster
	 * @param clusterId cluster to execute on, see {@link #listClusters()} to get a list of available clusters
	 * @param packageId package id, obtained from account web page
	 * @param variables map of variables to be passed to the job
	 * @return
	 */
	public Job runJob(long clusterId, long packageId, Map<String, String> variables) {
		return this.execute(new RunJob(new Job().onCluster(clusterId).withPackage(packageId).withVariables(variables))).withParentApiInstance(this);
	}

	/**
	 * Stop a particular job
	 * @param jobId id of job to stop, see {@link #listJobs()} to get a list of jobs
	 * @return
	 */
	public Job stopJob(long jobId) {
		return this.execute(new StopJob(jobId)).withParentApiInstance(this);
	}
	
	/**
	 * Account name this XplentyAPI instance is associated with
	 * @return
	 */
	public String getAccountName() {
		return super.getAccountName();
	}

	/**
	 * API key used by this XplentyAPI instance
	 * @return
	 */
	public String getApiKey() {
		return super.getApiKey();
	}

	/**
	 * API host this instance uses
	 * @return
	 */
	public String getHost() {
		return super.getHost();
	}

	/**
	 * Protocol this API instance uses
	 * @return
	 */
	public Http.Protocol getProtocol() {
		return super.getProtocol();
	}

	/**
	 * API version
	 * @return
	 */
	public Version getVersion() {
		return super.getVersion();
	}
}
