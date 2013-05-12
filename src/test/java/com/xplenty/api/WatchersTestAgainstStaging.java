package com.xplenty.api;

import com.xplenty.api.model.ClusterWatchingLogEntry;
import com.xplenty.api.model.JobWatchingLogEntry;
import com.xplenty.api.model.Watcher;
import junit.framework.Assert;
import org.joda.time.DateTime;
import junit.framework.TestCase;
import java.util.List;
import org.junit.Ignore;

@Ignore
public class WatchersTestAgainstStaging extends TestCase {
    private  XplentyAPI api;
    private String host = "api-staging.xplenty.com";
    private String apiKey = "";
    private String accountID = "";

    static Long _clusterId =  602L;
    static Long _jobId = 903L;
    static Long _watcherId = 89L;
    static String _watcherEmail = "";

    @Override public void setUp(){
        api = new XplentyAPI(accountID, apiKey);

    }

    public void testListClusterWatchers() {
        List<Watcher> res = api.listClusterWatchers(_clusterId);
        Assert.assertEquals( _watcherId, res.get(0).getId());
        Assert.assertEquals( _watcherEmail, res.get(0).getName());
    }

    public void testJobWatchers() {
        List<Watcher> res = api.listJobWatchers(_jobId);
        Assert.assertEquals(_watcherId, res.get(0).getId());
        Assert.assertEquals(_watcherEmail, res.get(0).getName());
    }

    public void testAddWatchers4Cluster() {
        ClusterWatchingLogEntry res = api.addClusterWatchers(_clusterId);
        Assert.assertEquals(new DateTime("2013-05-12T11:19:20+03:00").withTimeAtStartOfDay(), new DateTime(res.getCreationTimeStamp()).withTimeAtStartOfDay());
        Assert.assertEquals( "https://"+host+"/"+accountID+"/api/clusters/602", res.getUrl());
    }

    public void testAddWatchers4Job() {
        JobWatchingLogEntry res = api.addJobWatchers(_jobId);
        Assert.assertEquals(new DateTime("2013-05-12T11:19:20+03:00").withTimeAtStartOfDay(), new DateTime(res.getCreationTimeStamp()).withTimeAtStartOfDay());
        Assert.assertEquals("https://"+host+"/"+accountID+"/api/jobs/903", res.getUrl() );
    }

    public void testRemoveWatchersFromCluster() {
        Boolean res = api.removeClusterWatchers(_clusterId);
        Assert.assertTrue(res);
    }

    public void testRemoveWatchersFromJob() {
        Boolean res = api.removeJobWatchers(_jobId);
        Assert.assertTrue(res);
    }
}
