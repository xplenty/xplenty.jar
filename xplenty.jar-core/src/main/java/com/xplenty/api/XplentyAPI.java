/**
 * 
 */
package com.xplenty.api;

import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.HttpClient;
import com.xplenty.api.http.HttpClientBuilder;
import com.xplenty.api.model.*;
import com.xplenty.api.model.Package;
import com.xplenty.api.request.*;
import com.xplenty.api.request.watching.AddClusterWatcher;
import com.xplenty.api.request.watching.AddJobWatcher;
import com.xplenty.api.request.watching.ListWatchers;
import com.xplenty.api.request.watching.WatchingStop;
import com.xplenty.api.http.Http;

import java.util.List;
import java.util.Map;
import java.util.Properties;
/**
 * A convenience class for making HTTP requests to the Xplenty API for a given user. An underlying {@link com.xplenty.api.http.HttpClient} is created
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
 * @author Yuriy Kovalek and Xardas
 */
public class XplentyAPI {
    private final HttpClient client;
		
	/**
     * Constructs a XplentyAPI with a {@link com.xplenty.api.http.HttpClient} based on API key, account name,
     * and internal configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey User's API key found at https://www.xplenty.com/settings/edit
     */
	public XplentyAPI(String accountName, String apiKey) {
        client = new HttpClientBuilder().withAccount(accountName).withApiKey(apiKey).build();
	}

    /**
     * Constructs a XplentyAPI with a {@link XplentyWebConnectivity} based on API key, account name,
     * and internal configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey User's API key found at https://www.xplenty.com/settings/edit
     * @param logHttpCommunication enables logging of requests and responses
     */
    /**
     * Constructs a XplentyAPI with a {@link com.xplenty.api.http.HttpClient} based on the given http client  builder
     * @param clientBuilder configured Http client builder. At least account name and API Key must be set!
     */
    public XplentyAPI(HttpClientBuilder clientBuilder) {
        client = clientBuilder.build();
    }

    /**
     * Constructs a XplentyAPI with the {@link com.xplenty.api.http.HttpClient} given
     * @param client Configured http client
     */
    public XplentyAPI(HttpClient client){
        this.client = client;
    }

    /**
     * Get package information
     * @param packageId of the package to get
     * @return package object
     */
    public Package getPackageInfo(long packageId) {
        return client.execute(new PackageInfo(packageId));
    }

    /**
     * List of packages associated with the account
     * @param props map of request parameters
     * @return list of packages
     */
    public List<Package> listPackages(Properties props) {
        return client.execute(new ListPackages(props));
    }

    /**
     * List of schedules associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of schedules
     */
    public List<Package> listPackages(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractParametrizedRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractParametrizedRequest.PARAMETER_OFFSET, offset);
        return listPackages(props);
    }

    /**
     * List of schedules associated with the account
     * @return list of schedules
     */
    public List<Schedule> listSchedules() {
        return listSchedules(new Properties());
    }

    /**
     * List of schedules associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of schedules
     */
    public List<Schedule> listSchedules(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractParametrizedRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractParametrizedRequest.PARAMETER_OFFSET, offset);
        return listSchedules(props);
    }

    /**
     * List of schedules associated with the account
     * @param props map of request parameters, see {@link com.xplenty.api.Xplenty.ScheduleStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link com.xplenty.api.request.ListSchedules}
     * @return list of schedules
     */
    public List<Schedule> listSchedules(Properties props) {
        return client.execute(new ListSchedules(props));
    }

	/**
	 * List of clusters associated with the account
	 * @return list of clusters
	 */
	public List<Cluster> listClusters() {
		return listClusters(new Properties());
	}

    /**
     * List of clusters associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of clusters
     */
    public List<Cluster> listClusters(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractParametrizedRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractParametrizedRequest.PARAMETER_OFFSET, offset);
        return listClusters(props);
    }


    /**
	 * List of clusters associated with the account
	 * @param props map of request parameters, see {@link Xplenty.ClusterStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link ListClusters}
	 * @return list of clusters
	 */
	public List<Cluster> listClusters(Properties props) {
		return client.execute(new ListClusters(props));
	}
	/**
	 * Information about a particular cluster
	 * @param clusterId id of the cluster, see {@link #listClusters()} to get a list of clusters with id's
	 * @return
	 */
	public Cluster clusterInformation(long clusterId) {
		return client.execute(new ClusterInfo(clusterId)).withParentApiInstance(this);
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
	public Cluster createCluster(int nodes, ClusterType type, String name, String description, Boolean terminateOnIdle, Long timeToIdle) {
		return client.execute(new CreateCluster(
                new Cluster().withNodes(nodes)
                        .ofType(type)
                        .named(name)
                        .withDescription(description)
                        .withTerminateOnIdle(terminateOnIdle)
                        .withTimeToIdle(timeToIdle)
        )).withParentApiInstance(this);
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
		return client.execute(new UpdateCluster(
                new Cluster().withId(id).withNodes(nodes).named(name).withDescription(description)
                        .withTerminateOnIdle(terminateOnIdle).withTimeToIdle(timeToIdle)
        )).withParentApiInstance(this);
	}
	
	/**
	 * Terminate cluster with given id
	 * @param clusterId  is clusterId
	 * @return
	 */
	public Cluster terminateCluster(long clusterId) {
		return client.execute(new TerminateCluster(clusterId)).withParentApiInstance(this);
	}
	
	/**
	 * List of jobs associated with the account
	 * @return list of jobs
	 */
	public List<Job> listJobs() {
		return listJobs(new Properties());
	}

    /**
     * List of jobs associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of jobs
     */
    public List<Job> listJobs(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractParametrizedRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractParametrizedRequest.PARAMETER_OFFSET, offset);
        return listJobs(props);
    }
	
	/**
	 * List of jobs associated with the account
	 * @param params map of request parameters, see {@link Xplenty.JobStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link ListJobs}
	 * @return list of jobs
	 */
	public List<Job> listJobs(Properties params) {
		return client.execute(new ListJobs(params));
	}
	
	/**
	 * Information about a particular job
	 * @param jobId id of the job, see {@link #listJobs()} to get a list of jobs with id's
	 * @return
	 */
	public Job jobInformation(long jobId) {
		return client.execute(new JobInfo(jobId)).withParentApiInstance(this);
	}
	
	/**
	 * Execute a job on a cluster
	 * @param clusterId cluster to execute on, see {@link #listClusters()} to get a list of available clusters
	 * @param packageId package id, obtained from account web page
	 * @param variables map of variables to be passed to the job
	 * @return
	 */
	public Job runJob(long clusterId, long packageId, Map<String, String> variables) {
		return runJob(clusterId, packageId, variables, false);
	}
	
	/**
	 * Execute a job on a cluster
	 * @param clusterId cluster to execute on, see {@link #listClusters()} to get a list of available clusters
	 * @param packageId package id, obtained from account web page
	 * @param variables map of variables to be passed to the job
	 * @param dynamicVariables indication whether the variables should be treated as dynamic
	 * @return
	 */
	public Job runJob(long clusterId, long packageId, Map<String, String> variables, boolean dynamicVariables) {
		Job job = new Job().onCluster(clusterId).withPackage(packageId);
		if (dynamicVariables)
			job = job.withDynamicVariables(variables);
		else
			job = job.withVariables(variables);
		return client.execute(new RunJob(job)).withParentApiInstance(this);
	}

	/**
	 * Stop a particular job
	 * @param jobId id of job to stop, see {@link #listJobs()} to get a list of jobs
	 * @return
	 */
	public Job stopJob(long jobId) {
		return client.execute(new StopJob(jobId)).withParentApiInstance(this);
	}

    public List<Watcher> listClusterWatchers(long clusterId) {
        return client.execute(new ListWatchers(Xplenty.SubjectType.CLUSTER, clusterId));
    }

    public List<Watcher> listJobWatchers(long clusterId) {
        return client.execute(new ListWatchers(Xplenty.SubjectType.JOB, clusterId));
    }

    public ClusterWatchingLogEntry addClusterWatchers(long clusterId) {
        return client.execute(new AddClusterWatcher(clusterId)).withParentApiInstance(this);
    }

    public JobWatchingLogEntry addJobWatchers(long jobId) {
        return client.execute(new AddJobWatcher(jobId)).withParentApiInstance(this);
    }


    public Boolean removeClusterWatchers(long clusterId) {
        return client.execute(new WatchingStop(Xplenty.SubjectType.CLUSTER, clusterId));
    }

    public Boolean removeJobWatchers(long jobId) {
        return client.execute(new WatchingStop(Xplenty.SubjectType.JOB, jobId));
    }

    /**
     * Creates new schedule
     * @param schedule Schedule to create. All fields that have public setters (except for id) should be set
     * @return newly created schedule object
     */
    public Schedule createSchedule(Schedule schedule) {
        if (schedule.getId() != null) {
            schedule.setId(null);
        }
        return client.execute(new CreateSchedule(schedule));
    }

    /**
     * Update schedule
     * @param schedule Schedule to update. Any fields that have public setters can be set. Id must be set!
     * @return updated schedule object
     */
    public Schedule updateSchedule(Schedule schedule) {
        if (schedule.getId() == null) {
            throw new XplentyAPIException("No id specified!");
        }
        return client.execute(new UpdateSchedule(schedule));
    }

    /**
     * Delete schedule
     * @param scheduleId Id of schedule to delete
     * @return deleted schedule object
     */
    public Schedule deleteSchedule(long scheduleId) {
        if (scheduleId == 0) {
            throw new XplentyAPIException("No id specified!");
        }
        return client.execute(new DeleteSchedule(scheduleId));
    }

    /**
     * Clone schedule
     * @param scheduleId Id of schedule to clone
     * @return cloned schedule object
     */
    public Schedule cloneSchedule(long scheduleId) {
        if (scheduleId == 0) {
            throw new XplentyAPIException("No id specified!");
        }
        return client.execute(new CloneSchedule(scheduleId));
    }

    /**
     * Get schedule info
     * @param scheduleId Id of schedule to get
     * @return schedule object
     */
    public Schedule getScheduleInfo(long scheduleId) {
        if (scheduleId == 0) {
            throw new XplentyAPIException("No id specified!");
        }
        return client.execute(new ScheduleInfo(scheduleId));
    }

    /**
	 * Account name this XplentyAPI instance is associated with
	 * @return Account name
	 */
	public String getAccountName() {
		return client.getAccountName();
	}

	/**
	 * API key used by this XplentyAPI instance
	 * @return API Key
	 */
	public String getApiKey() {
		return client.getApiKey();
	}

	/**
	 * API host this instance uses
	 * @return API host
	 */
	public String getHost() {
		return client.getHost();
	}

	/**
	 * Protocol this API instance uses
	 * @return protocol
	 */
	public Http.Protocol getProtocol() {
		return client.getProtocol();
	}

	/**
	 * API version
	 * @return version
	 */
	public Version getVersion() {
		return client.getVersion();
	}

    public int getTimeout() {
        return client.getTimeout();
    }
}
