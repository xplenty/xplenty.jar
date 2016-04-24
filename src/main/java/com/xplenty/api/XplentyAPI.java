/**
 * 
 */
package com.xplenty.api;

import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.HttpClient;
import com.xplenty.api.model.*;
import com.xplenty.api.model.Package;
import com.xplenty.api.request.AbstractListRequest;
import com.xplenty.api.request.account.*;
import com.xplenty.api.request.cluster.*;
import com.xplenty.api.request.connection.ConnectionInfo;
import com.xplenty.api.request.connection.DeleteConnection;
import com.xplenty.api.request.connection.ListConnectionTypes;
import com.xplenty.api.request.connection.ListConnections;
import com.xplenty.api.request.hook.*;
import com.xplenty.api.request.job.*;
import com.xplenty.api.request.member.*;
import com.xplenty.api.request.misc.*;
import com.xplenty.api.request.public_key.*;
import com.xplenty.api.request.schedule.*;
import com.xplenty.api.request.subscription.ListPlans;
import com.xplenty.api.request.subscription.PaymentMehodInfo;
import com.xplenty.api.request.subscription.SubscriptionInfo;
import com.xplenty.api.request.subscription.UpdatePaymentAndPlan;
import com.xplenty.api.request.user.*;
import com.xplenty.api.request.watching.AddClusterWatcher;
import com.xplenty.api.request.watching.AddJobWatcher;
import com.xplenty.api.request.watching.ListWatchers;
import com.xplenty.api.request.watching.WatchingStop;
import com.xplenty.api.request.xpackage.*;

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
        return getPackageInfo(packageId, false);
    }


    /**
     * Get package information
     * @param packageId of the package to get
     * @param includeDataFlow if set to true, package object will contain data flow json
     * @return package object
     */
    public Package getPackageInfo(long packageId, boolean includeDataFlow) {
        return client.execute(new PackageInfo(packageId, includeDataFlow));
    }

    /**
     * List of packages associated with the account
     * @return list of packages
     */
    public List<Package> listPackages() {
        return listPackages(new Properties());
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
     * List of packages associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of packages
     */
    public List<Package> listPackages(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listPackages(props);
    }

    /**
     * Search packages based on the specified query
     * @param searchQuery search query
     * @return list of matching packages
     */
    public List<Package> searchPackages(SearchQuery searchQuery) {
        checkSearchQuery(searchQuery);
        return client.execute(new SearchPackages(searchQuery.toProperties()));
    }

    /**
     * List package templates that are available for the authenticated user.
     * You can use template to create new package with predefined settings.
     * @return list of package templates
     */
    public List<PackageTemplate> listPackageTemplates() {
        return client.execute(new ListPackageTemplates());
    }

    /**
     * List validations for specific package.
     * Optionally, you can supply the input parameters to filter the validation list so that it contains only validations
     * with a specific status, and to determine the order by which the list will be sorted.
     * @param packageId id of package to get validation list for
     * @return list of package validations
     */
    public List<PackageValidation> listPackageValidations(long packageId) {
        return listPackageValidations(packageId, new Properties());
    }

    /**
     * List validations for specific package.
     * Optionally, you can supply the input parameters to filter the validation list so that it contains only validations
     * with a specific status, and to determine the order by which the list will be sorted.
     * @param packageId id of package to get validation list for
     * @param props map of request parameters
     * @return list of package validations
     */
    public List<PackageValidation> listPackageValidations(long packageId, Properties props) {
        checkId(packageId);
        return client.execute(new ListPackageValidations(packageId, props));
    }

    /**
     * List validations for specific package.
     * Optionally, you can supply the input parameters to filter the validation list so that it contains only validations
     * with a specific status, and to determine the order by which the list will be sorted.
     * @param packageId id of package to get validation list for
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of package validations
     */
    public List<PackageValidation> listPackageValidations(long packageId, int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listPackageValidations(packageId, props);
    }

    /**
     * Create a new package.
     * @param xpackage package object with properties properly set
     * @return newly created package object
     */
    public Package createPackage(Package xpackage) {
        xpackage.withId(null);
        return client.execute(new CreatePackage(xpackage));
    }

    /**
     * Update an existing package.
     * @param xpackage package object with properties properly set
     * @return updated package object
     */
    public Package updatePackage(Package xpackage) {
        checkId(xpackage.getId());
        return client.execute(new UpdatePackage(xpackage));
    }

    /**
     * Delete an existing package.
     * @param packageId id of package to delete
     * @return deleted package object
     */
    public Package deletePackage(long packageId) {
        checkId(packageId);
        return client.execute(new DeletePackage(packageId));
    }

    /**
     * Runs new validation process for the package and returns information about status and tracking url.
     * @param packageId id of package to validate
     * @return package validation object
     */
    public PackageValidation runPackageValidation(long packageId) {
        checkId(packageId);
        return client.execute(new RunPackageValidation(packageId));
    }

    /**
     * Returns information about progress of the package validation process.
     * @param packageId id of package
     * @param validationId id of validation for that package
     * @return package validation object
     */
    public PackageValidation getPackageValidationInfo(long packageId, long validationId) {
        checkId(packageId);
        checkId(validationId);
        return client.execute(new PackageValidationInfo(validationId, packageId));
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
     * Search schedules based on the specified query
     * @param searchQuery search query
     * @return list of matching schedules
     */
    public List<Schedule> searchSchedules(SearchQuery searchQuery) {
        checkSearchQuery(searchQuery);
        return client.execute(new SearchSchedules(searchQuery.toProperties()));
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
     * List existing cluster instances.
     * Note: This endpoint is only available for selected plans.
     * @param clusterId id of the cluster
     * @return list of instances
     */
    public List<ClusterInstance> listClusterInstances(long clusterId) {
        checkId(clusterId);
        return client.execute(new ListClusterInstances(clusterId));
    }

    /**
     * Search clusters based on the specified query
     * @param searchQuery search query
     * @return list of matching clusters
     */
    public List<Cluster> searchClusters(SearchQuery searchQuery) {
        checkSearchQuery(searchQuery);
        return client.execute(new SearchClusters(searchQuery.toProperties()));
    }

    private void checkSearchQuery(SearchQuery searchQuery) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            throw new XplentyAPIException("No search query specified!");
        }
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
	 * @return newly created cluster object
	 */
    @Deprecated
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
     * Create new cluster with specified properties. Note that nodes paramter MUST be specified in case you are creating
     * production cluster. If you create sandbox - cluster type must be specified. All other parameters are optional and
     * will be assigned with default/generated values
     * @param cluster Filled cluster object
     * @return newly created cluster object
     */
    public Cluster createCluster(Cluster cluster) {
        cluster.withId(null);
        return client.execute(new CreateCluster(cluster));
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
        final List<Job> jobList = client.execute(new ListJobs(params));
        for (Job job : jobList) {
            job.withParentApiInstance(this);
        }
        return jobList;
	}

    /**
     * Search jobs based on the specified query
     * @param searchQuery search query
     * @return list of matching jobs
     */
    public List<Job> searchJobs(SearchQuery searchQuery) {
        checkSearchQuery(searchQuery);
        return client.execute(new SearchJobs(searchQuery.toProperties()));
    }


    /**
     * List all job variables that were used during job runtime.
     * @param jobId id of the job
     * @return map var_name => var_value
     */
    public Map<String, String> getJobExecutionVariables(long jobId) {
        return client.execute(new JobExecutionVariables(jobId));
    }

    /**
     * Log summary for the job.
     * @param jobId id of the job
     * @return job log object
     */
    public JobLog getJobLog(long jobId) {
        return client.execute(new JobLogs(jobId));
    }

    /**
     * The calls returns up to 100 lines raw preview of a job output.
     * @param jobId job id
     * @param outputId output id
     * @return job output preview object
     */
    public JobOutputPreview previewJobOutput(long jobId, long outputId) {
        checkId(jobId);
        checkId(outputId);
        return client.execute(new JobPreviewOutput(jobId, outputId));
    }

    /**
     * Information about a particular job
     * @param jobId id of the job, see {@link #listJobs()} to get a list of jobs with id's
     * @return job object
     */
    public Job jobInformation(long jobId) {
        return jobInformation(jobId, false, false);
    }

    /**
     * Information about a particular job
     * @param jobId id of the job, see {@link #listJobs()} to get a list of jobs with id's
     * @param includeCluster include cluster object
     * @param includePackage include package object
     * @return job object
     */
	public Job jobInformation(long jobId, boolean includeCluster, boolean includePackage) {
		return client.execute(new JobInfo(jobId, includeCluster, includePackage)).withParentApiInstance(this);
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
            schedule.withId(null);
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
     * Sends user password reset instructions
     * @param email email of the user
     */
    public void resetUserPassword(String email) {
        checkStringId(email);
        client.execute(new ResetUserPassword(email));
    }

    /**
     * Get all supported hook events to use when creating/updating hooks
     * @return list of hook events
     */
    public List<AvailableHookEvent> listHookEvents() {
        return client.execute(new ListHookEvents());
    }

    /**
     * List all hook types that are available with related groups.
     * @return list of hook types
     */
    public List<AvailableHookType> listHookTypes() {
        return client.execute(new ListHookTypes());
    }

    /**
     * Create new hook to recieve notifications for events subscribed
     * @param name name of the hook. Leave null for default
     * @param settings settings used to connect to your server
     * @param events list of events, retrieved using {@link #listHookEvents() listHookEvents}, you want to subscribe to
     * @return created hook object
     */
    public Hook createHook(String name, HookSettings settings, List<String> events) {
        return client.execute(new CreateHook(name, settings, events));
    }

    /**
     * Create new hook to recieve notifications for events subscribed
     *  @param name name of the hook. Leave null for default
     * @param events list of predefined events you want to subscribe to
     * @param settings settings used to connect to your server
     * @return created hook object
     */
    public Hook createHook(String name, List<Xplenty.HookEvent> events, HookSettings settings) {
        return client.execute(new CreateHook(name, events, settings));
    }

    /**
     * Update hook
     * @param hookId id of the hook
     * @param name name of the hook
     * @param settings settings used to connect to your server, pass null if no change required
     * @param events subscribe to these events. All existing events will be replaced. Leave null if no change required
     * @return updated hook object
     */
    public Hook updateHook(long hookId, String name, HookSettings settings, List<String> events) {
        checkId(hookId);
        return client.execute(new UpdateHook(hookId, name, settings, events));
    }

    /**
     * Update hook
     * @param hookId id of the hook
     * @param name name of the hook
     * @param events subscribe to these events. All existing events will be replaced. Leave null if no change required
     * @param settings settings used to connect to your server, pass null if no change required
     * @return updated hook object
     */
    public Hook updateHook(long hookId, String name, List<Xplenty.HookEvent> events, HookSettings settings) {
        checkId(hookId);
        return client.execute(new UpdateHook(hookId, name, events, settings));
    }

    /**
     * Enable/disable hook
     * @param hookId id of the hook
     * @param active true to enable hook, false otherwise
     * @return updated hook object
     */
    public Hook toggleHook(long hookId, boolean active) {
        checkId(hookId);
        return client.execute(new ToggleHook(hookId, active));
    }

    /**
     * List hooks associated with the account
     * @return list of hooks
     */
    public List<Hook> listHooks() {
        return listHooks(new Properties());
    }

    /**
     * List hooks associated with the account
     * @param offset number of record to  start results from
     * @param limit number of results
     * @return list of hooks
     */
    public List<Hook> listHooks(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listHooks(props);
    }

    /**
     * List hooks associated with the account
     * @param params map of request parameters, see {@link Xplenty.Sort}, {@link Xplenty.SortDirection}.
     * @return list of hooks
     */
    public List<Hook> listHooks(Properties params) {
        return client.execute(new ListHooks(params));
    }

    /**
     * Search hooks based on the specified query
     * @param searchQuery search query
     * @return list of matching hooks
     */
    public List<Hook> searchHooks(SearchQuery searchQuery) {
        checkSearchQuery(searchQuery);
        return client.execute(new SearchHooks(searchQuery.toProperties()));
    }

    /**
     * Delete hook with specified id
     * @param hookId id of the hook to delete
     * @return deleted hook object
     */
    public Hook deleteHook(long hookId) {
        checkId(hookId);
        return client.execute(new DeleteHook(hookId));
    }

    /**
     * Reset salt for hook with specified id
     * @param hookId id of the hook to reset salt for
     * @return newly generated salt
     */
    public String hookResetSalt(long hookId) {
        checkId(hookId);
        return client.execute(new HookResetSalt(hookId));
    }

    /**
     * Ping (fire test notification) to url configured in specified hook
     * @param hookId id of the hook to ping
     * @return hook object
     */
    public Hook pingHook(long hookId) {
        checkId(hookId);
        return client.execute(new PingHook(hookId));
    }

    /**
     * Get hook information
     * @param hookId id of the hook to get info for
     * @return hook object
     */
    public Hook getHookInfo(long hookId) {
        checkId(hookId);
        return client.execute(new HookInfo(hookId));
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
     * Search public keys based on the specified query
     * @param searchQuery search query
     * @return list of matching public keys
     */
    public List<PublicKey> searchPublicKeys(SearchQuery searchQuery) {
        checkSearchQuery(searchQuery);
        return client.execute(new SearchPublicKeys(searchQuery.toProperties()));
    }

    /**
     * Deletes public key with specified id
     * @param publicKeyId id of public key to delete
     * @return deleted public key object
     */
    public PublicKey deletePublicKey(long publicKeyId) {
        checkId(publicKeyId);
        return client.execute(new DeletePublicKey(publicKeyId));
    }

    /**
     * Get public key information
     * @param publicKeyId id of public key to get
     * @return public key object
     */
    public PublicKey getPublicKeyInfo(long publicKeyId) {
        checkId(publicKeyId);
        return client.execute(new PublicKeyInfo(publicKeyId));
    }

    /**
     * Creates a new member on an account. The call sends an invitation to join Xplenty in case the user is not yet a user of Xplenty.
     * @param email email of the member to add
     * @param role role of the member
     * @param name name of the member
     * @return newly created member object
     */
    public Member createMember(String email, Xplenty.AccountRole role, String name) {
        return client.execute(new CreateMember(email, role, name));
    }


    /**
     * List existing account members. Optionally, you can supply the input parameters to filter the member list so that
     * it contains user with specific role or email only and to determine the order by which the list will be sorted.
     * @return list of account members
     */
    public List<Member> listMembers() {
        return listMembers(new Properties());
    }

    /**
     * List existing account members. Optionally, you can supply the input parameters to filter the member list so that
     * it contains user with specific role or email only and to determine the order by which the list will be sorted.
     * @param offset number of record to start results from
     * @param limit number of results
     * @return list of account members
     */
    public List<Member> listMembers(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listMembers(props);
    }

    /**
     * List existing account members. Optionally, you can supply the input parameters to filter the member list so that
     * it contains user with specific role or email only and to determine the order by which the list will be sorted.
     * @param params map of request parameters, see {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, {@link com.xplenty.api.request.member.ListMembers}.
     * @return list of account members
     */
    public List<Member> listMembers(Properties params) {
        return client.execute(new ListMembers(params));
    }

    /**
     * Set existing account member's role
     * @param memberId id of member to set role for
     * @param role new role for member
     * @return updated member object
     */
    public Member setMemberRole(long memberId, Xplenty.AccountRole role) {
        checkId(memberId);
        return client.execute(new SetMemberRole(memberId, role));
    }

    /**
     * Information about member of the account.
     * @param memberId id of member to get
     * @return member object
     */
    public Member getMemberInfo(long memberId) {
        checkId(memberId);
        return client.execute(new MemberInfo(memberId));
    }

    /**
     * Delete an existing member from an account. This call does not delete the user, just the account membership.
     * @param memberId id of member to delete
     * @return Deleted member object
     */
    public Member deleteMember(long memberId) {
        checkId(memberId);
        return client.execute(new DeleteMember(memberId));
    }

    /**
     * This call returns information for the list of regions that are available for your account.
     * You can use this information to verify the regions in which you can create a cluster
     * @return list of regions, available for account
     */
    public List<Region> listAvailableRegions() {
        return client.execute(new ListAccountRegions(new Properties()));
    }

    /**
     * Creates a new account. This operation is possible only by confirmed users.
     * @param name The name of the account.
     * @param region The default region for the account. Possible values can be obtained through {@link #listAvailableRegions()}
     * @param accountId Unique identifier of the account. Optional parameter. If null is passed will be autogenerated.
     * @return newly created account object
     */
    public Account createAccount(String name, String region, String accountId) {
        return client.execute(new CreateAccount(name, region, accountId));
    }

    /**
     * List active accounts in which the authenticated user is a member of (with either admin or member role).
     * Optionally, you can supply the input parameters to filter the account list so that it contains only accounts with a
     * specific role or id, and to determine the order by which the list will be sorted.
     * @return list of accounts
     */
    public List<Account> listAccounts() {
        return listAccounts(new Properties());
    }

    /**
     * List active accounts in which the authenticated user is a member of (with either admin or member role).
     * Optionally, you can supply the input parameters to filter the account list so that it contains only accounts with a
     * specific role or id, and to determine the order by which the list will be sorted.
     * @param offset number of record to start results from
     * @param limit number of results
     * @return list of accounts
     */
    public List<Account> listAccounts(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listAccounts(props);
    }

    /**
     * List active accounts in which the authenticated user is a member of (with either admin or member role).
     * Optionally, you can supply the input parameters to filter the account list so that it contains only accounts with a
     * specific role or id, and to determine the order by which the list will be sorted.
     * @param params map of request parameters, see {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, {@link com.xplenty.api.request.account.ListAccounts}.
     * @return list of accounts
     */
    public List<Account> listAccounts(Properties params) {
        return client.execute(new ListAccounts(params));
    }

    /**
     * This call updates account. You must use {@link com.xplenty.api.model.Account#Account(String)} for account object creation
     * @param account account to be updated
     * @return updated account object
     */
    public Account updateAccount(Account account) {
        checkStringId(account.getCurrentAccountId());
        return client.execute(new UpdateAccount(account));
    }

    /**
     * Delete an existing account. The operation can be executed only by the account owner.
     * @param accountId unique string id of the account to delete
     * @return deleted account object
     */
    public Account deleteAccount(String accountId) {
        checkStringId(accountId);
        return client.execute(new DeleteAccount(accountId));
    }

    /**
     * Get information about account. The authenticated user must be a member of this account (with either admin or member role).
     * @param accountId unique string id of the account to get
     * @return account object
     */
    public Account getAccountInfo(String accountId) {
        checkStringId(accountId);
        return client.execute(new AccountInfo(accountId));
    }

    /**
     * List connections that are accessible by the authenticated user.
     * Optionally, you can supply the input parameters to filter the connection list so that it contains only connections
     * with specific types and to determine the order by which the list will be sorted.
     * @return list of connections
     */
    public List<Connection> listConnections() {
        return listConnections(new Properties());
    }

    /**
     * List connections that are accessible by the authenticated user.
     * Optionally, you can supply the input parameters to filter the connection list so that it contains only connections
     * with specific types and to determine the order by which the list will be sorted.
     * @param offset number of record to start results from
     * @param limit number of results
     * @return list of connections
     */
    public List<Connection> listConnections(int offset, int limit) {
        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_LIMIT, limit);
        props.put(AbstractListRequest.PARAMETER_OFFSET, offset);
        return listConnections(props);
    }

    /**
     * List connections that are accessible by the authenticated user.
     * Optionally, you can supply the input parameters to filter the connection list so that it contains only connections
     * with specific types and to determine the order by which the list will be sorted.
     * @param params map of request parameters, see {@link Xplenty.Sort}, {@link Xplenty.SortDirection}, {@link com.xplenty.api.request.connection.ListConnections}.
     * @return list of connections
     */
    public List<Connection> listConnections(Properties params) {
        return client.execute(new ListConnections(params));
    }


    /**
     * List all connection types that are available with related groups.
     * @return list of connection types
     */
    public List<ConnectionType> listConnectionTypes() {
        return client.execute(new ListConnectionTypes());
    }

    /**
     * Delete an existing connection.
     * Please note that deleting the connection will invalidate all items referencing it.
     * @param connectionId id of the connection to delete
     * @param conType type of the connection to delete
     * @return deleted connection object
     */
    public Connection deleteConnection(long connectionId, Xplenty.ConnectionType conType) {
        return client.execute(new DeleteConnection(connectionId, conType));
    }

    /**
     * Get connection information
     * @param connectionId id of the connection to get
     * @param conType type of the connection to get
     * @return connection object
     */
    public Connection getConnectionInfo(long connectionId, Xplenty.ConnectionType conType) {
        return client.execute(new ConnectionInfo(connectionId, conType));
    }

    /**
     * This call returns information for the list of supported Stacks.
     * @return list of stacks
     */
    public List<Stack> listStacks() {
        return client.execute(new ListStacks());
    }

    /**
     * This call returns list of supported Time Zones.
     * @return list of timezones
     */
    public List<Timezone> listTimezones() {
        return client.execute(new ListTimezones());
    }

    /**
     * List public system variables
     * @return map var_name => var_value
     */
    public Map<String, String> listSystemVariables() {
        return client.execute(new ListSystemVariables());
    }

    /**
     * This call returns information for the list of regions supported by Xplenty.
     * You can also select regions for particular Brand. You can use this information to verify the regions in which you can create a cluster.
     * @return list of regions
     */
    public List<Region> listRegions() {
        return listRegions(new Properties());
    }

    /**
     * This call returns information for the list of regions supported by Xplenty.
     * You can also select regions for particular Brand. You can use this information to verify the regions in which you can create a cluster.
     * @param params map of request parameters, see {@link com.xplenty.api.request.misc.ListRegions}.
     * @return list of regions
     */
    public List<Region> listRegions(Properties params) {
        return client.execute(new ListRegions(params));
    }

    /**
     * This call returns a list of notifications of the authenticated user.
     * Optionally, you can supply the input parameters to filter the list so that it contains only unread notifications
     * or all notifications, and to determine the order by which the list will be sorted.
     * @return list of user notifications
     */
    public List<Notification> listUserNotifications() {
        return listUserNotifications(new Properties());
    }

    /**
     * This call returns a list of notifications of the authenticated user.
     * Optionally, you can supply the input parameters to filter the list so that it contains only unread notifications
     * or all notifications, and to determine the order by which the list will be sorted.
     * @param params map of request parameters, see {@link com.xplenty.api.request.user.ListNotifications}.
     * @return list of user notifications
     */
    public List<Notification> listUserNotifications(Properties params) {
        return client.execute(new ListNotifications(params));
    }

    /**
     * Marks the authenticated user's notifications as read. This call returns empty response.
     * @return null
     */
    public void markNotificationAsRead() {
        client.execute(new MarkNotificationsRead());
    }

    /**
     * List plans that are available for an account.
     * @return list of plans
     */
    public List<Plan> listPlans() {
        return client.execute(new ListPlans());
    }

    /**
     * Information about current account subscription.
     * @return subscription object
     */
    public Subscription getSubscriptionInfo() {
        return client.execute(new SubscriptionInfo());
    }

    /**
     * This call updates the payment method or plan or both.
     * @param billingPaymentToken The valid payment token created through the billing provider (Paymill).
     * @param planId ID of the plan.
     * @return credit card object
     */
    public CreditCardInfo updatePaymentAndPlan(String billingPaymentToken, String planId) {
        return client.execute(new UpdatePaymentAndPlan(billingPaymentToken, planId));
    }

    /**
     * Get payment method on file, for an existing account. If there is no payment method on file, returns Resource not found (404).
     * @return credit card object
     */
    public CreditCardInfo getPaymentMethodInfo() {
       return client.execute(new PaymentMehodInfo());
    }

    /**
     * This call returns list of latest product announcements.
     * @return list of product updates
     */
    public List<ProductUpdate> listProductUpdates() {
        return client.execute(new ListProductUpdates());
    }

    /**
     * This action allows to like product update by authenticated user.
     * @param productUpdateId id of product update to like
     * @return liked product update
     */
    public ProductUpdate likeProductUpdate(long productUpdateId) {
        checkId(productUpdateId);
        return client.execute(new LikeProductUpdate(productUpdateId));
    }

    private void checkId(long id) {
        if (id == 0) {
            throw new XplentyAPIException("No Id specified!");
        }
    }

    private void checkStringId(String id) {
        if (id == null || id.length() == 0) {
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
