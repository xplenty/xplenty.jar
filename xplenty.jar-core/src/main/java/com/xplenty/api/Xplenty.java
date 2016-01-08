/**
 * 
 */
package com.xplenty.api;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * A bunch of convenience structures
 * @author Yuriy Kovalek
 *
 */
@SuppressWarnings("unused")
public class Xplenty {
    public static final int MAX_LIMIT = 100;

	public enum Sort {
		updated("updated"),
		created("created"),
        // todo include all entities that support name sort
        /**
         * Note that name sort is available only for {@link com.xplenty.api.model.PublicKey Public Key List},
         * {@link com.xplenty.api.model.Member Member List}, {@link com.xplenty.api.model.Account Account List},
         * {@link com.xplenty.api.model.Connection Connection List}
         */
        name("name"),
        /**
         * Note that email sort is available only for {@link com.xplenty.api.model.Member Member List}
         */
        email("email"),
        /**
         * Note that id sort is available only for {@link com.xplenty.api.model.Account Account List},
         * {@link com.xplenty.api.model.Connection Connection List}
         */
        id("id"),
        /**
         * Note that type sort is available only for {@link com.xplenty.api.model.Connection Connection List}
         */
        type("type")
        ;
		
		public final String value;
		
		Sort(String value) {
			this.value = value;
		}
		
		public String toString() {
			return value;
		}
	}

	
	public enum SortDirection {
		ascending("asc"),
		descending("desc");
		
		public final String value;
		
		SortDirection(String value) {
			this.value = value;
		}
		
		public String toString() {
			return value;
		}
	}
	
	public enum JobStatus {
        /**
         * the user sent a request to run the job
         */
		idle("idle"),
        /**
         *  the job is initializing
         */
        pending("pending"),
        /**
         * the job is running
         */
        running("running"),
        /**
         * the job completed successfully
         */
        completed("completed"),
        /**
         * the job failed to complete
         */
        failed("failed"),
        /**
         *  the user sent a request to stop the job
         */
        pending_stoppage("pending_stoppage"),
        /**
         *  the job is stopping
         */
        stopping("stopping"),
        /**
         * the job has stopped
         */
		stopped("stopped")
		;
		
		@SuppressWarnings("unused")
		private final String status;
		
		JobStatus(String status) {
			this.status = status;
		}

	}

    public enum ScheduleStatus {
        enabled("enabled"),
        disabled("disabled"),
        /**
         * used only for filtering meaning all the statuses above
         */
        all("all")
        ;
        @SuppressWarnings("unused")
        private final String status;

        ScheduleStatus(String status) {
            this.status = status;
        }
    }
    
    public enum ScheduleIntervalUnit {

        minutes("minutes"),
        hours("hours"),
        days("days"),
        weeks("weeks"),
        months("months");

        @SuppressWarnings("unused")
        private final String unit;

        ScheduleIntervalUnit(String unit) {
            this.unit = unit;
        }
    }
	
	public enum ClusterType {		
		production("production"),
		sandbox("sandbox");
		
		@SuppressWarnings("unused")
		private final String type;
		
		ClusterType(String type) {
			this.type = type;
		}

	}

    public enum AccountRole {
        /**
         * only current owner can transfer ownership to another member
         */
        owner("owner"),
        admin("admin"),
        member("member"),
        /**
         * used only for filtering meaning all the roles above
         */
        all("all");

        @SuppressWarnings("unused")
        private final String role;

        AccountRole(String role) {
            this.role = role;
        }
    }

    public enum ClusterInstanceStatus {
        available("available"),
        terminated("terminated");

        @SuppressWarnings("unused")
        private final String status;

        ClusterInstanceStatus(String status) {
            this.status = status;
        }
    }
    
    public enum ConnectionType {
        s3("s3"),
        swift("swift"),
        gs("gs"),
        rackspace("rackspace"),
        softlayer("softlayer"),
        hdfs("hdfs"),
        adwords("adwords"),
        postgres("postgres"),
        redshift("redshift"),
        mongo("mongo"),
        mysql("mysql"),
        hana("hana"),
        sqlserver("sqlserver"),
        herokupostgres("herokupostgres"),
        googlecloudsql("googlecloudsql"),
        bigquery("bigquery"),
        segment("segment"),
        salesforce("salesforce"),
        /**
         * used only for filtering meaning all the types above
         */
        all("all");

        @SuppressWarnings("unused")
        private final String type;

        ConnectionType(String type) {
            this.type = type;
        }
    }

    public enum PackageValidationStatus {
        running,
        completed,
        failed;
    }

    public enum PriceUnit {
        year,
        month,
        day,
        hour;
    }
    
    public enum WebHookEvent {
        job_all("job"),
        job_submitted("job.submitted"),
        job_started("job.started"),
        job_stopped("job.stopped"),
        job_completed("job.completed"),
        job_failed("job.failed"),
        cluster("cluster"),
        cluster_requested("cluster.requested"),
        cluster_available("cluster.available"),
        cluster_terminated("cluster.terminated"),
        cluster_idled("cluster.idled"),
        cluster_error("cluster.error");

        @SuppressWarnings("unused")
        private final String event;

        WebHookEvent(String event) {
            this.event = event;
        }

        @Override
        public String toString() {
            return event;
        }
    }


	@JsonFormat(shape= JsonFormat.Shape.SCALAR)
	public static enum ClusterStatus {
		pending("pending"),
		error("error"),
		creating("creating"),
		available("available"),
		scaling("scaling"),
		pending_terminate("pending_terminate"),
		terminating("terminating"),
		terminated("terminated");
		
		@SuppressWarnings("unused")
		private final String status;
		
		ClusterStatus(String status) {
			this.status = status;
		}
	}

	/**
	 * Endpoints and short descriptions for REST resources
	 */
	public static enum Resource {
        Package("packages/%s", "Get package info"),
        Packages("packages", "List packages"),
		ClusterPlans("cluster_plans", "List cluster plans"),
		Clusters("clusters", "List clusters"), 
		Cluster("clusters/%s", "Get cluster information"),
		CreateCluster("clusters", "Create cluster"),
		UpdateCluster("clusters/%s", "Update cluster"),
		TerminateCluster("clusters/%s", "Terminate cluster"),
		Jobs("jobs", "List jobs"),
		Job("jobs/%s", "Get job info"),
		RunJob("jobs", "Run job"),
		StopJob("jobs/%s", "Stop job"),
		JobExecVars("jobs/%s/variables", "List job execution variables"),
		JobLog("jobs/%s/log", "Get job output log"),
        ClusterWatcher("clusters/%s/watchers", "Adding/removing a cluster watchers"),
        JobWatcher("jobs/%s/watchers", "Adding/removing a job watchers"),
        Schedules("schedules", "List schedules"),
        CreateSchedule("schedules", "Create schedule"),
        CloneSchedule("schedules/%s/clone", "Clone schedule"),
        UpdateSchedule("schedules/%s", "Update schedule"),
        RemoveSchedule("schedules/%s", "Remove schedule"),
        Schedule("schedules/%s", "Get schedule information"),
        User("user", "Get current user information"),
        UpdateUser("user", "Update current user information"),
        HookEvents("hook_events", "List supported Hook Events"),
        CreateWebHook("hooks/web", "Create new Web hook"),
        UpdateWebHook("hooks/web/%s", "Update existing Web hook"),
        DeleteWebHook("hooks/web/%s", "Delete Web hook"),
        PingWebHook("hooks/web/%s/ping", "Ping(fire test notification) for Web hook"),
        WebHookResetSalt("hooks/web/%s/reset_salt", "Reset Web hook's salt"),
        WebHook("hooks/web/%s", "Get  Web hook information"),
        WebHooks("hooks/web", "List Web hooks"),
        CreatePublicKey("user/keys", "Create Public Key"),
        PublicKey("user/keys/%s", "Get  Public Key information"),
        PublicKeys("user/keys", "List Public Keys"),
        DeletePublicKey("user/keys/%s", "Delete Public Key"),
        CreateMember("members", "Create new Member"),
        DeleteMember("members/%s", "Delete member"),
        Member("members/%s", "Get member information"),
        Members("members", "List account members"),
        SetMemberRole("members/%s", "Change member role"),
        Regions("regions", "List available account regions"),
        CreateAccount("accounts", "Create new account"),
        DeleteAccount("accounts/%s", "Delete account"),
        Account("accounts/%s", "Get account information"),
        Accounts("accounts", "List accounts"),
        UpdateAccount("accounts/%s", "Update account information"),
        Connections("connections", "List Connections"),
        ConnectionTypes("connections/types", "List Connection Types"),
        DeleteConnection("connections/%s/%s", "Delete Connection"),
        Connection("connections/%s/%s", "Get Connection information")
        ;
		
		public final String value;
		public final String name;
		
		Resource(String val, String name) {
			this.value = val;
			this.name = name;
		}
		
		public String format(String... values) {
			return String.format(value, (Object[]) values);
		}
	}
	
	public static enum Version {
		V1(1), V2(2);
		
		private final int value;
		
		Version(int ver) {
			this.value = ver;
		}
		
		public String format() {
			return "version=" + Integer.toString(value);
		}
	}

    public static enum SubjectType {
        CLUSTER, JOB
    }
}
