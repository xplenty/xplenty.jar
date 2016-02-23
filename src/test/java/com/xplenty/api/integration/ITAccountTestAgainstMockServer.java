package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Account;
import com.xplenty.api.model.Region;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ITAccountTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/account";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 666L;
    private String uniqueId = "superunique";


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testListaccountRegions() {
        List<Region> list = api.listAvailableRegions();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        Region c = list.get(0);
        assertEquals("AWS - US East (N. Virginia)", c.getName());
        assertEquals("Amazon Web Services", c.getGroupName());
        assertEquals("amazon-web-services::us-east-1", c.getId());
    }

    public void testDeleteAccount() throws Exception {
        Account c = api.deleteAccount(uniqueId);
        checkEntity(c);
    }

    public void testAccountInfo() throws Exception {
        Account c = api.getAccountInfo(uniqueId);
        checkEntity(c);
    }

    public void testListAccounts() throws Exception {
        List<Account> list = api.listAccounts();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Account c = list.get(0);
        checkEntity(c);
    }

    public void testCreateAccount() throws Exception {
        Account c = api.createAccount("test", "gcloud::europe-west", uniqueId);
        checkEntity(c);
    }

    public void testUpdateAccount() throws Exception {
        Account c = new Account(uniqueId);
        c.setAccountId(uniqueId);
        c.setName("test");
        c.setBillingEmail("xardazz@github.com");
        c.setGravatarEmail("xardazz@github.com");
        c.setLocation("Private Drive");
        c.setRegion("gcloud::europe-west");
        c = api.updateAccount(c);
        checkEntity(c);
    }

    private void checkEntity(Account c) throws ParseException {
        assertNotNull(c);

        assertEquals(new Long(entityId), c.getId());
        assertEquals("test", c.getName());
        assertEquals(uniqueId, c.getAccountId());
        assertEquals("gcloud::europe-west", c.getRegion());
        assertEquals("https://secure.gravatar.com", c.getAvatarUrl());
        assertEquals("xardazz@github.com", c.getBillingEmail());
        assertEquals("gravatar@gravatar.com", c.getGravatarEmail());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals(123, c.getConnectionsCount().intValue());
        assertEquals(1234, c.getJobsCount().intValue());
        assertEquals(12345, c.getMembersCount().intValue());
        assertEquals(123456, c.getPackagesCount().intValue());
        assertEquals(1234567, c.getRunningJobsCount().intValue());
        assertEquals(12345678, c.getSchedulesCount().intValue());
        assertEquals(7, c.getHooksCount().intValue());
        assertEquals(String.format("ssh-rsa AAAAAAA....AAAAAA Xplenty/%s", uniqueId), c.getPublicKey());
        assertEquals(111, c.getOwnerId().longValue());
        assertEquals("Private Drive", c.getLocation());
        assertEquals(String.format("u_%s", entityId), c.getUname());
        assertEquals(String.format("https://localhost/accounts/%s", uniqueId), c.getUrl());
        assertEquals(dFormat.parse("2016-01-13T20:07:21Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T20:07:21Z"), c.getUpdatedAt());
    }
}
