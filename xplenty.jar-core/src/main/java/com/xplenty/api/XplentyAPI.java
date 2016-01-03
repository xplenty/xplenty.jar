/**
 * 
 */
package com.xplenty.api;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterWatchingLogEntry;
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobWatchingLogEntry;
import com.xplenty.api.model.Package;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.model.Watcher;
import com.xplenty.api.request.AbstractParametrizedRequest;
import com.xplenty.api.request.CloneSchedule;
import com.xplenty.api.request.ClusterInfo;
import com.xplenty.api.request.CreateCluster;
import com.xplenty.api.request.CreateSchedule;
import com.xplenty.api.request.DeleteSchedule;
import com.xplenty.api.request.JobInfo;
import com.xplenty.api.request.ListClusters;
import com.xplenty.api.request.ListJobs;
import com.xplenty.api.request.ListPackages;
import com.xplenty.api.request.ListSchedules;
import com.xplenty.api.request.PackageInfo;
import com.xplenty.api.request.RunJob;
import com.xplenty.api.request.ScheduleInfo;
import com.xplenty.api.request.StopJob;
import com.xplenty.api.request.TerminateCluster;
import com.xplenty.api.request.UpdateCluster;
import com.xplenty.api.request.UpdateSchedule;
import com.xplenty.api.request.watching.AddClusterWatcher;
import com.xplenty.api.request.watching.AddJobWatcher;
import com.xplenty.api.request.watching.ListWatchers;
import com.xplenty.api.request.watching.WatchingStop;
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
     * @param apiKey User's API key found at https://www.xplenty.com/settings/edit
     */
	public XplentyAPI(String accountName, String apiKey) {
		super(accountName, apiKey);
	}

    /**
     * Constructs a XplentyAPI with a {@link XplentyWebConnectivity} based on API key, account name,
     * and internal configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey User's API key found at https://www.xplenty.com/settings/edit
     * @param logHttpCommunication enables logging of requests and responses
     */
    public XplentyAPI(String accountName, String apiKey, boolean logHttpCommunication) {
        super(accountName, apiKey, logHttpCommunication);
    }

    /**
     * Constructs a XplentyAPI with a {@link XplentyWebConnectivity} based on API key, account name,
     * and manual configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey  User's API key found at https://www.xplenty.com/settings/edit
     * @param host Base API host
     * @param proto API protocol (plain or encrypted)
     */
    public XplentyAPI(String accountName, String apiKey, String host, Http.Protocol proto){
        this(accountName, apiKey, host, proto, false);
    }

    /**
     * Constructs a XplentyAPI with a {@link XplentyWebConnectivity} based on API key, account name,
     * and manual configuration.
     * @param accountName account name used for Xplenty sign-up
     * @param apiKey  User's API key found at https://www.xplenty.com/settings/edit
     * @param host Base API host
     * @param proto API protocol (plain or encrypted)
     * @param logHttpCommunication enables logging of requests and responses
     */
    public XplentyAPI(String accountName, String apiKey, String host, Http.Protocol proto, boolean logHttpCommunication){
        this(accountName, apiKey, logHttpCommunication);
        this.setHost(host);
        this.setProtocol(proto);
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
     * Get package information
     * @param packageId of the package to get
     * @return package object
     */
    public Package getPackageInfo(long packageId) {
        return this.execute(new PackageInfo(packageId));
    }

    /**
     * List of packages associated with the account
     * @param props map of request parameters
     * @return list of packages
     */
    public List<Package> listPackages(Properties props) {
        return this.execute(new ListPackages(props));
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
        return this.execute(new ListSchedules(props));
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
	 * @param nodes number of nodes
	 * @param type of cluster - 'sandbox' if sandbox
	 * @param name cluster name
	 * @param description cluster description
	 * @param terminateOnIdle should the cluster terminate on idle status
	 * @param timeToIdle time in seconds before cluster is considered idle
	 * @return
	 */
	public Cluster createCluster(int nodes, ClusterType type, String name, String description, Boolean terminateOnIdle, Long timeToIdle) {
		return this.execute(new CreateCluster(
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
		return this.execute(new UpdateCluster(
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
		return this.execute(new TerminateCluster(clusterId)).withParentApiInstance(this);
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
	 * @param variables map of static variables to be passed to the job
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
		return this.execute(new RunJob(job)).withParentApiInstance(this);
	}

	/**
	 * Stop a particular job
	 * @param jobId id of job to stop, see {@link #listJobs()} to get a list of jobs
	 * @return
	 */
	public Job stopJob(long jobId) {
		return this.execute(new StopJob(jobId)).withParentApiInstance(this);
	}

    public List<Watcher> listClusterWatchers(long clusterId) {
        return this.execute(new ListWatchers(Xplenty.SubjectType.CLUSTER, clusterId));
    }

    public List<Watcher> listJobWatchers(long clusterId) {
        return this.execute(new ListWatchers(Xplenty.SubjectType.JOB, clusterId));
    }

    public ClusterWatchingLogEntry addClusterWatchers(long clusterId) {
        return this.execute(new AddClusterWatcher(clusterId)).withParentApiInstance(this);
    }

    public JobWatchingLogEntry addJobWatchers(long jobId) {
        return this.execute(new AddJobWatcher(jobId)).withParentApiInstance(this);
    }


    public Boolean removeClusterWatchers(long clusterId) {
        return this.execute(new WatchingStop(Xplenty.SubjectType.CLUSTER, clusterId));
    }

    public Boolean removeJobWatchers(long jobId) {
        return this.execute(new WatchingStop(Xplenty.SubjectType.JOB, jobId));
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
        return this.execute(new CreateSchedule(schedule));
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
        return this.execute(new UpdateSchedule(schedule));
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
        return this.execute(new DeleteSchedule(scheduleId));
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
        return this.execute(new CloneSchedule(scheduleId));
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
        return this.execute(new ScheduleInfo(scheduleId));
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
