package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.ClusterWatchingLogEntry;
import com.xplenty.api.model.JobWatchingLogEntry;
import com.xplenty.api.model.Watcher;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ITWatchersTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/watchers";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
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

    public void testAddWatchers4Cluster() throws ParseException {
        ClusterWatchingLogEntry res = api.addClusterWatchers(5);
        Assert.assertEquals(dFormat.parse("2013-04-09T11:19:20Z"), res.getCreationTimeStamp());
        Assert.assertEquals( "https://api.xplenty.com/xplenation/api/clusters/370", res.getUrl());
    }

    public void testAddWatchers4Job() throws ParseException {
        JobWatchingLogEntry res = api.addJobWatchers(7);
        Assert.assertEquals(dFormat.parse("2013-04-09T11:19:20Z"), res.getCreationTimeStamp());
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
