package com.xplenty;

import com.xplenty.api.XplentyAPI;
import com.xplenty.api.model.ClusterWatchingLogEntry;
import com.xplenty.api.model.JobWatchingLogEntry;
import com.xplenty.api.model.Watcher;
import com.xplenty.api.util.Http;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.joda.time.DateTime;

public class ITWatchersTestAgainstMockServer extends TestCase {

    private XplentyAPI api;
    private String host = "localhost:8080/mock";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";


    @Override
    public void setUp(){
        api = new XplentyAPI(accountID, apiKey, host, Http.Protocol.Http);
    }

    public void testListClusterWatchers() {
        Watcher[] res = api.listClusterWatchers(5).toArray(new Watcher[1]);
        Assert.assertEquals(res[0].getId(), new Long(1));
        Assert.assertEquals(res[0].getName(), "Xplenty");
    }

    public void testJobWatchers() {
        Watcher[] res = api.listJobWatchers(5).toArray(new Watcher[1]);
        Assert.assertEquals(res[0].getId(), new Long(1));
        Assert.assertEquals("Xplenty - Job watcher", res[0].getName());
    }

    public void testAddWatchers4Cluster() {
        ClusterWatchingLogEntry res = api.addClusterWatchers(5);
        Assert.assertEquals(new DateTime("2013-04-09T11:19:20+03:00").toDate(), res.getCreationTimeStamp());
        Assert.assertEquals( "https://api.xplenty.com/xplenation/api/clusters/370", res.getUrl());
    }

    public void testAddWatchers4Job() {
        JobWatchingLogEntry res = api.addJobWatchers(7);
        Assert.assertEquals(new DateTime("2013-04-09T11:19:20+03:00").toDate(), res.getCreationTimeStamp());
        Assert.assertEquals("https://api.xplenty.com/xplenation/api/jobs/370", res.getUrl() );
    }

    public void testRemoveWatchersFromCluster() {
        Boolean res = api.removeClusterWatchers(7);
        Assert.assertTrue(res);
    }

    public void testRemoveWatchersFromJob() {
        Boolean res = api.removeJobWatchers(7);
        Assert.assertTrue(res);
    }
}
