/**
 * 
 */
package com.xplenty.api;

import java.util.List;
import java.util.Map;

import com.xplenty.api.Xplenty.Protocol;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.ClusterInfo;
import com.xplenty.api.request.CreateCluster;
import com.xplenty.api.request.JobInfo;
import com.xplenty.api.request.ListClusters;
import com.xplenty.api.request.ListJobs;
import com.xplenty.api.request.RunJob;
import com.xplenty.api.request.StopJob;
import com.xplenty.api.request.TerminateCluster;
/**
 * A convenience class for making HTTP requests to the Xplenty API for a given user. An underlying {@link XplentyWebConnector} is created
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
public class XplentyAPI {
		
	private final XplentyWebConnector connector;
	
	/**
     * Constructs a XplentyAPI with a {@link XplentyWebConnector} based on API key, account name,
     * and internal configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey User's API key found at //TODO add link to API key source
     */
	public XplentyAPI(String accountName, String apiKey) {
		connector = new XplentyWebConnector(accountName, apiKey);
	}
	
	public XplentyAPI withVersion(Version ver) {
		connector.setVersion(ver);
		return this;
	}
	
	public XplentyAPI withHost(String host) {
		connector.setHost(host);
		return this;
	}
	
	public XplentyAPI withProtocol(Protocol proto) {
		connector.setProtocol(proto);
		return this;
	}


	/**
	 * List of clusters associated with the account
	 * @return
	 */
	public List<Cluster> listClusters() {
		return connector.execute(new ListClusters());
	}
	
	/**
	 * Information about a particular cluster
	 * @param clusterId id of the cluster, see {@link #listClusters()} to get a list of clusters with id's
	 * @return
	 */
	public Cluster clusterInformation(long clusterId) {
		return connector.execute(new ClusterInfo(clusterId)).withParentApiInstance(this);
	}
	
	/**
	 * Create cluster with specified properties
	 * @param nodes number of nodes
	 * @param type of cluster - 'sandbox' if sandbox
	 * @param name cluster name
	 * @param description cluster description
	 * @param terminateOnIdle should the cluster terminate on idle status
	 * @param timeToIdle time in seconds before cluster is considered idle
	 * @return
	 */
	public Cluster createCluster(int nodes, String type, String name, String description, Boolean terminateOnIdle, Long timeToIdle) {
		return connector.execute(new CreateCluster(
				new Cluster().withNodes(nodes).ofType(type)
				.named(name).withDescription(description).withTerminateOnIdle(terminateOnIdle).withTimeToIdle(timeToIdle)
				));
	}
	
	/**
	 * Update cluster with specified properties
	 * @param id id of cluster
	 * @param nodes number of nodes
	 * @param name cluster name
	 * @param description cluster description
	 * @param terminateOnIdle should the cluster terminate on idle status
	 * @param timeToIdle time in seconds before cluster is considered idle
	 * @return
	 */
	public Cluster updateCluster(long id, Integer nodes, String name, String description, Boolean terminateOnIdle, Long timeToIdle) {
		return connector.execute(new CreateCluster(
				new Cluster().withId(id).withNodes(nodes).named(name).withDescription(description)
				.withTerminateOnIdle(terminateOnIdle).withTimeToIdle(timeToIdle)
				));
	}
	
	/**
	 * Terminate cluster with given id
	 * @param clusterId
	 * @return
	 */
	public Cluster terminateCluster(long clusterId) {
		return connector.execute(new TerminateCluster(clusterId));
	}
	
	/**
	 * List of jobs associated with the account
	 * @return
	 */
	public List<Job> listJobs() {
		return connector.execute(new ListJobs());
	}
	
	/**
	 * Information about a particular job
	 * @param jobId id of the job, see {@link #listJobs()} to get a list of jobs with id's
	 * @return
	 */
	public Job jobInformation(long jobId) {
		return connector.execute(new JobInfo(jobId));
	}
	
	/**
	 * Execute a job on a cluster
	 * @param clusterId cluster to execute on, see {@link #listClusters()} to get a list of available clusters
	 * @param packageId package id, obtained from account web page
	 * @param variables map of variables to be passed to the job
	 * @return
	 */
	public Job runJob(long clusterId, long packageId, Map<String, String> variables) {
		return connector.execute(new RunJob(new Job().onCluster(clusterId).withPackage(packageId).withVariables(variables)));
	}

	/**
	 * Stop a particular job
	 * @param jobId id of job to stop, see {@link #listJobs()} to get a list of jobs
	 * @return
	 */
	public Job stopJob(long jobId) {
		return connector.execute(new StopJob(jobId));
	}
	
	/**
	 * Account name this XplentyAPI instance is associated with
	 * @return
	 */
	public String getAccountName() {
		return connector.getAccountName();
	}

	/**
	 * API key used by this XplentyAPI instance
	 * @return
	 */
	public String getApiKey() {
		return connector.getApiKey();
	}
}
