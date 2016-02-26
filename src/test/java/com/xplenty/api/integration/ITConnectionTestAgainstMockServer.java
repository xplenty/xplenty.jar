package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Connection;
import com.xplenty.api.model.ConnectionType;
import com.xplenty.api.request.connection.ListConnections;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class ITConnectionTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/connection";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 33L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testGetConnection() throws Exception {
        Connection c = api.getConnectionInfo(entityId, Xplenty.ConnectionType.salesforce);
        checkEntity(c);
    }

    public void testDeleteConnection() throws Exception {
        Connection c = api.deleteConnection(entityId, Xplenty.ConnectionType.salesforce);
        checkEntity(c);
    }

    public void testListConnections() throws Exception {
        List<Connection> list = api.listConnections();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Connection c = list.get(0);
        checkEntity(c);

        list = api.listConnections(3, 5);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        c = list.get(0);
        checkEntity(c);

        Properties props = new Properties();
        props.put(ListConnections.PARAMETER_TYPE, Xplenty.ConnectionType.hdfs);
        list = api.listConnections(props);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        c = list.get(0);
        checkEntity(c);
    }

    public void testListConnectionTypes() {
        List<ConnectionType> list = api.listConnectionTypes();

        assertNotNull(list);
        assertTrue(list.size() > 0);

        ConnectionType c = list.get(0);
    }

    private void checkEntity(Connection c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals("Salesforce Connection", c.getName());
        assertEquals(Xplenty.ConnectionType.salesforce, c.getType());
        assertEquals(dFormat.parse("2015-11-08T11:47:16Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-15T08:15:50Z"), c.getUpdatedAt());
    }

    private void checkConnectionType(ConnectionType c) {
        assertNotNull(c);
        assertEquals("adwords", c.getName());
        assertEquals("Google AdWords", c.getName());
        assertEquals("Google (PPC) Pay-Per-Click online advertising service", c.getName());
        assertEquals("https://xplenty.com/assets/vendor/google-words-66f0580dec1f5c82432c95adf2bdf20d.png", c.getName());

        assertNotNull(c.getGroups());
        assertEquals("Services", c.getGroups().get(0).getGroupName());
        assertEquals("services", c.getGroups().get(0).getGroupType());
    }
}
