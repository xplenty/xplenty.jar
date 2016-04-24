package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterBootstrapAction;
import com.xplenty.api.model.ClusterInstance;
import com.xplenty.api.model.Creator;
import com.xplenty.api.request.cluster.ListClusters;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ITClusterTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/cluster";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 666L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.Jersey).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testCreateCluster() throws Exception {
        List<ClusterBootstrapAction> actions = new ArrayList<>();
        List<String> scriptParams = new ArrayList<>();
        scriptParams.add("--ride-the-horse");
        actions.add(new ClusterBootstrapAction("/dev/null", scriptParams));
        Cluster c = new Cluster().ofType(Xplenty.ClusterType.production).withAllowFallback(true).withDescription("somedesc").
                withMasterInstanceType("mastertype").withMasterSpotPercentage(0.7).withMasterSpotPrice(1.3).withNodes(7).
                withRegion("Hogwarts").withSlaveInstanceType("slavetype").withSlaveSpotPercentage(0.3).withSlaveSpotPrice(1.1).
                withStack("pinky-everest").withTerminateOnIdle(true).withTimeToIdle(2000L).withZone("GB").named("clustername").
                withBootstrapActions(actions);
        c = api.createCluster(c);
        checkEntity(c);

    }

    public void testUpdateCluster() throws Exception {
        Cluster c = api.updateCluster(entityId, 15, "new name", "some  new description", false, 12345L);
        checkEntity(c);
    }


    public void testTerminateCluster() throws Exception {
        Cluster c = api.terminateCluster(entityId);
        checkEntity(c);
    }

    public void testGetClusterInfo() throws Exception {
        Cluster c = api.clusterInformation(entityId);
        checkEntity(c);
    }

    public void testWaitForStatus() throws Exception {
        Cluster c = api.clusterInformation(entityId);
        checkEntity(c);
        c.waitForStatus(Xplenty.ClusterStatus.terminated);
        try {
            c.waitForStatus(1L, Xplenty.ClusterStatus.available);
        } catch (XplentyAPIException ex) {
            assertEquals("Timeout occurred while waiting for required cluster status", ex.getMessage());
        }
    }

    public void testListClusters() throws Exception {
        List<Cluster> list = api.listClusters();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Cluster c = list.get(0);
        checkEntity(c);

        list = api.listClusters(7, 77);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        c = list.get(0);
        checkEntity(c);

        Properties props = new Properties();
        props.put(ListClusters.PARAMETER_STATUS, Xplenty.ClusterStatus.creating);
        list = api.listClusters(props);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        c = list.get(0);
        checkEntity(c);
    }

    public void testListClusterInstances() throws Exception {
        List<ClusterInstance> list = api.listClusterInstances(entityId);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        ClusterInstance c = list.get(0);
        checkClusterInstance(c);
    }

    private void checkEntity(Cluster c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());

        assertEquals("brown-sea-277", c.getName());
        assertEquals("sea is brown in this time of the year", c.getDescription());
        assertEquals(Xplenty.ClusterStatus.terminated, c.getStatus());
        assertEquals(new Long(352), c.getOwnerId());
        assertEquals(new Integer(7), c.getNodes());
        assertEquals(new Long(3600), c.getTimeToIdle());
        assertEquals(Xplenty.ClusterType.production, c.getType());
        assertEquals(new Long(0), c.getRunningJobsCount());
        assertTrue(c.getTerminateOnIdle());
        assertTrue(c.getTerminatedOnIdle());
        assertEquals(dFormat.parse("2016-01-22T13:22:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-22T14:33:03Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-22T13:31:07Z"), c.getAvailableSince());
        assertEquals(dFormat.parse("2016-01-22T14:33:02Z"), c.getTerminatedAt());
        assertEquals(dFormat.parse("2016-01-22T14:33:02Z"), c.getIdleSince());
        assertEquals(dFormat.parse("2016-01-22T13:31:06Z"), c.getLaunchedAt());
        assertEquals(String.format("https://localhost/%s/api/clusters/%s", accountID, entityId), c.getUrl());
        assertEquals(String.format("https://localhost/%s/clusters/%s", accountID, entityId), c.getHtmlUrl());
        assertTrue(c.getAllowFallback());
        assertNull(c.getMasterInstanceType());
        assertNull(c.getSlaveInstanceType());
        assertNull(c.getZone());
        assertEquals("amazon-web-services::eu-west-1", c.getRegion());
        assertEquals("pro", c.getPlanId());
        assertEquals("white-everest", c.getStack());
        assertNull(c.getMasterSpotPercentage());
        assertNull(c.getMasterSpotPrice());
        assertNull(c.getSlaveSpotPercentage());
        assertNull(c.getSlaveSpotPrice());
        Creator creator = c.getCreator();
        assertEquals("User", creator.getType());
        assertEquals(352, creator.getId().longValue());
        assertEquals("Alexey Gromov", creator.getDisplayName());
        assertEquals(String.format("https://localhost/%s/api/members/352", accountID), creator.getUrl());
        assertEquals(String.format("https://localhost/%s/settings/members/352", accountID), creator.getHtmlUrl());
        List<ClusterBootstrapAction> bactions = c.getBootstrapActions();
        assertNotNull(bactions);
        assertTrue(bactions.size() > 0);
        ClusterBootstrapAction baction = bactions.get(0);
        assertEquals("my/super/script.sh", baction.getScriptPath());
        List<String> args = baction.getArgs();
        assertNotNull(args);
        assertTrue(args.size() > 0);
        assertEquals("arg1", args.get(0));
    }
    
    private void checkClusterInstance(ClusterInstance ci) throws ParseException {
        assertNotNull(ci);
        assertEquals("i-4d1b39a7", ci.getInstanceId());
        assertEquals("ip-10-124-29-23.ec2.internal", ci.getPrivateDns());
        assertEquals("ec2-55-27-210-201.compute-1.amazonaws.com", ci.getPublicDns());
        assertEquals("sometype", ci.getInstanceType());
        assertEquals("eu-west1", ci.getZone());
        assertEquals(String.format("https://localhost/%s/api/clusters/%s/instances/i-4d1b39a7", accountID, entityId), ci.getUrl());
        assertEquals(Xplenty.ClusterInstanceStatus.available, ci.getStatus());
        assertTrue(ci.getMaster());
        assertFalse(ci.getSpot());
        assertFalse(ci.getVpc());
    }
}
