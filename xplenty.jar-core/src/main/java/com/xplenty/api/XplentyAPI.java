/**
 * 
 */
package com.xplenty.api;

import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.HttpClient;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.model.*;
import com.xplenty.api.model.Package;
import com.xplenty.api.request.*;
import com.xplenty.api.request.cluster.*;
import com.xplenty.api.request.job.JobInfo;
import com.xplenty.api.request.job.ListJobs;
import com.xplenty.api.request.job.RunJob;
import com.xplenty.api.request.job.StopJob;
import com.xplenty.api.request.public_key.CreatePublicKey;
import com.xplenty.api.request.public_key.DeletePublicKey;
import com.xplenty.api.request.public_key.ListPublicKeys;
import com.xplenty.api.request.public_key.PublicKeyInfo;
import com.xplenty.api.request.schedule.*;
import com.xplenty.api.request.user.CurrentUserInfo;
import com.xplenty.api.request.user.UpdateCurrentUser;
import com.xplenty.api.request.watching.AddClusterWatcher;
import com.xplenty.api.request.watching.AddJobWatcher;
import com.xplenty.api.request.watching.ListWatchers;
import com.xplenty.api.request.watching.WatchingStop;
import com.xplenty.api.http.Http;
import com.xplenty.api.request.webhook.*;
import com.xplenty.api.request.xpackage.ListPackages;
import com.xplenty.api.request.xpackage.PackageInfo;

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
        client = new ClientBuilder().withAccount(accountName).withApiKey(apiKey).build();
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
    public XplentyAPI(ClientBuilder clientBuilder) {
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
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
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
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listSchedules(props);
    }

    /**
     * List of schedules associated with the account
     * @param props map of request parameters, see {@link com.xplenty.api.Xplenty.ScheduleStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link com.xplenty.api.request.schedule.ListSchedules}
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
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listClusters(props);
    }


    /**
	 * List of clusters associated with the account
	 * @param props map of request parameters, see {@link Xplenty.ClusterStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link com.xplenty.api.request.cluster.ListClusters}
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
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listJobs(props);
    }
	
	/**
	 * List of jobs associated with the account
	 * @param params map of request parameters, see {@link Xplenty.JobStatus}, {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, for keys see constants in {@link com.xplenty.api.request.job.ListJobs}
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
        checkId(schedule.getId());
        return client.execute(new UpdateSchedule(schedule));
    }

    /**
     * Delete schedule
     * @param scheduleId Id of schedule to delete
     * @return deleted schedule object
     */
    public Schedule deleteSchedule(long scheduleId) {
        checkId(scheduleId);
        return client.execute(new DeleteSchedule(scheduleId));
    }

    /**
     * Clone schedule
     * @param scheduleId Id of schedule to clone
     * @return cloned schedule object
     */
    public Schedule cloneSchedule(long scheduleId) {
        checkId(scheduleId);
        return client.execute(new CloneSchedule(scheduleId));
    }

    /**
     * Get schedule info
     * @param scheduleId Id of schedule to get
     * @return schedule object
     */
    public Schedule getScheduleInfo(long scheduleId) {
        checkId(scheduleId);
        return client.execute(new ScheduleInfo(scheduleId));
    }

    /**
     * Get current user information.
     * @return user object
     */
    public User getCurrentUserInfo() {
        return getCurrentUserInfo(null);
    }

    /**
     * Get current user information including API Key.
     * @param currentPassword current user password. If you pass null, API KEy won't be retrieved
     * @return user object
     */
    public User getCurrentUserInfo(String currentPassword) {
        return client.execute(new CurrentUserInfo(currentPassword));
    }

    /**
     * Update current user information like notifications settings, timezone, etc
     * @param user user object containing required changes
     * @return updated user object
     */
    public User updateCurrentUser(User user) {
        return client.execute(new UpdateCurrentUser(user));
    }

    /**
     * Get all supported hook events to use when creating/updating web hooks
     * @return list of hook events
     */
    public List<HookEvent> getHookEvents() {
        return client.execute(new ListHookEvents());
    }

    /**
     * Create new Web hook to recieve notifications for events subscribed
     * @param settings settings used to connect to your server
     * @param events list of events, retrieved using {@link #getHookEvents() getHookEvents}, you want to subscribe to
     * @return created web hook object
     */
    public WebHook createWebHook(WebHookSettings settings, List<String> events) {
        return client.execute(new CreateWebHook(settings, events));
    }

    /**
     * Create new Web hook to recieve notifications for events subscribed
     * @param events list of predefined events you want to subscribe to
     * @param settings settings used to connect to your server
     * @return created web hook object
     */
    public WebHook createWebHook(List<Xplenty.WebHookEvent> events, WebHookSettings settings) {
        return client.execute(new CreateWebHook(events, settings));
    }

    /**
     * Update web hook
     * @param webHookId id of the web hook
     * @param settings settings used to connect to your server, pass null if no change required
     * @param addEvents subscribe to these events. Leave null if no change required
     * @param removeEvents unsubscribe from these events. Leave null if no change required.
     * @return updated web hook object
     */
    public WebHook updateWebHook(long webHookId, WebHookSettings settings, List<String> addEvents, List<String> removeEvents) {
        checkId(webHookId);
        return client.execute(new UpdateWebHook(webHookId, settings, addEvents, removeEvents));
    }

    /**
     * Update web hook
     * @param webHookId id of the web hook
     * @param addEvents subscribe to these events. Leave null if no change required
     * @param removeEvents unsubscribe from these events. Leave null if no change required.
     * @param settings settings used to connect to your server, pass null if no change required
     * @return updated web hook object
     */
    public WebHook updateWebHook(long webHookId, List<Xplenty.WebHookEvent> addEvents, List<Xplenty.WebHookEvent> removeEvents, WebHookSettings settings) {
        checkId(webHookId);
        return client.execute(new UpdateWebHook(webHookId, addEvents, removeEvents, settings));
    }

    /**
     * Enable/disable Web hook
     * @param webHookId id of the web hook
     * @param active true to enable web hook, false otherwise
     * @return updated web hook object
     */
    public WebHook toggleWebHook(long webHookId, boolean active) {
        checkId(webHookId);
        return client.execute(new ToggleWebHook(webHookId, active));
    }

    /**
     * List web hooks associated with the account
     * @return list of web hooks
     */
    public List<WebHook> listWebHooks() {
        return listWebHooks(new Properties());
    }

    /**
     * List web hooks associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of web hooks
     */
    public List<WebHook> listWebHooks(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listWebHooks(props);
    }

    /**
     * List web hooks associated with the account
     * @param params map of request parameters, see {@link Xplenty.Sort}, {@link Xplenty.SortDirection}.
     * @return list of web hooks
     */
    public List<WebHook> listWebHooks(Properties params) {
        return client.execute(new ListWebHooks(params));
    }

    /**
     * Delete webhook with specified id
     * @param webHookId id of the webhook to delete
     * @return deleted web hook object
     */
    public WebHook deleteWebHook(long webHookId) {
        checkId(webHookId);
        return client.execute(new DeleteWebHook(webHookId));
    }

    /**
     * Reset salt for webhook with specified id
     * @param webHookId id of the webhook to reset salt for
     * @return newly generated salt
     */
    public String webHookResetSalt(long webHookId) {
        checkId(webHookId);
        return client.execute(new WebHookResetSalt(webHookId));
    }

    /**
     * Ping (fire test notification) to url configured in specified web hook
     * @param webHookId id of the webhook to ping
     * @return web hook object
     */
    public WebHook pingWebHook(long webHookId) {
        checkId(webHookId);
        return client.execute(new PingWebHook(webHookId));
    }

    /**
     * Get web hook information
     * @param webHookId id of the webhook to get info for
     * @return web hook object
     */
    public WebHook getWebHookInfo(long webHookId) {
        checkId(webHookId);
        return client.execute(new WebHookInfo(webHookId));
    }

    /**
     * Create (add) new public key
     * @param name name to distinguish public keys from each other
     * @param publicKey SSH Public key which contains information about type of encryption at the beginning of string, like: 'ssh-rsa'.
     * @return newly created public key object
     */
    public PublicKey createPublicKey(String name, String publicKey) {
        return client.execute(new CreatePublicKey(name, publicKey));
    }

    /**
     * List public keys associated with the account
     * @return list of public keys
     */
    public List<PublicKey> listPublicKeys() {
        return listPublicKeys(new Properties());
    }

    /**
     * List public keys associated with the account
     * @param offset number of record to start results from
     * @param limit number of results
     * @return list of public keys
     */
    public List<PublicKey> listPublicKeys(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listPublicKeys(props);
    }

    /**
     * List public keys associated with the account
     * @param params map of request parameters, see {@link Xplenty.Sort}, {@link Xplenty.SortDirection}.
     * @return list of public keys
     */
    public List<PublicKey> listPublicKeys(Properties params) {
        return client.execute(new ListPublicKeys(params));
    }

    /**
     * Deletes public key with specified id
     * @param publicKeyId id of public key to delete
     * @return deleted public key object
     */
    public PublicKey deletePublicKey(long publicKeyId) {
        return client.execute(new DeletePublicKey(publicKeyId));
    }

    /**
     * Get public key information
     * @param publicKeyId id of public key to get
     * @return public key object
     */
    public PublicKey getPublicKeyInfo(long publicKeyId) {
        return client.execute(new PublicKeyInfo(publicKeyId));
    }


    private void checkId(long id) {
        if (id == 0) {
            throw new XplentyAPIException("No Id specified!");
        }
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
