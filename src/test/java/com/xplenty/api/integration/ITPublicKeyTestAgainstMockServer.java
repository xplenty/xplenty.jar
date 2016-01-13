package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.PublicKey;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ITPublicKeyTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/pk";
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

    public void testDeletePublicKey() throws Exception {
        PublicKey c = api.deletePublicKey(entityId);
        checkEntity(c);
    }

    public void testPublicKeyInfo() throws Exception {
        PublicKey c = api.getPublicKeyInfo(entityId);
        checkEntity(c);
    }

    public void testListPublicKeys() throws Exception {
        List<PublicKey> list = api.listPublicKeys();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        PublicKey c = list.get(0);
        checkEntity(c);
    }

    public void testCreatePublicKey() throws Exception {
        PublicKey c = api.createPublicKey("Test", "ssh-rsa AAAAB3NzaC1yc2EAAAABIwAA...AAA xardazz@github.com");
        checkEntity(c);
    }

    private void checkEntity(PublicKey c) throws ParseException {
        assertNotNull(c);
        assertEquals(new Long(entityId), c.getId());
        assertEquals("xardazz@github.com", c.getComment());
        assertEquals("ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff", c.getFingerprint());
        assertEquals("Test", c.getName());
        assertEquals(String.format("https://localhost/user/keys/%s", entityId), c.getUrl());
        assertEquals(dFormat.parse("2016-01-06T20:05:21Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-06T20:05:21Z"), c.getUpdatedAt());
    }
}
