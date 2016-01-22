package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.model.WebHookEvent;
import com.xplenty.api.model.WebHookEventResponse;
import com.xplenty.api.model.WebHookSettings;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ITWebHookTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/webhook";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 666L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testCreateWebHook() throws Exception {
        List<Xplenty.WebHookEvent> events = new ArrayList<>();
        events.add(Xplenty.WebHookEvent.cluster_available);
        events.add(Xplenty.WebHookEvent.cluster_error);
        WebHookSettings settings = new WebHookSettings("http://localhost", true, true, "md5encodedcredientials");
        WebHook c = api.createWebHook(events, settings);
        checkEntity(c);
    }

    public void testUpdateWebHook() throws Exception {
        List<Xplenty.WebHookEvent> rmevents = new ArrayList<>();
        rmevents.add(Xplenty.WebHookEvent.cluster_available);
        rmevents.add(Xplenty.WebHookEvent.cluster_error);
        List<Xplenty.WebHookEvent> addevents = new ArrayList<>();
        addevents.add(Xplenty.WebHookEvent.job_completed);
        addevents.add(Xplenty.WebHookEvent.job_started);
        WebHookSettings settings = new WebHookSettings("http://localhost", true, true, "md5encodedcredientials");
        WebHook c = api.updateWebHook(entityId, addevents, rmevents, settings);
        checkEntity(c);
        c = api.updateWebHook(entityId, addevents, rmevents, null);
        checkEntity(c);
    }


    public void testDeleteWebHook() throws Exception {
        WebHook c = api.deleteWebHook(entityId);
        checkEntity(c);
    }

    public void testGetWebHookInfo() throws Exception {
        WebHook c = api.getWebHookInfo(entityId);
        checkEntity(c);
    }

    public void testPingWebHook() throws Exception {
        WebHook c = api.pingWebHook(entityId);
        checkEntity(c);
    }

    public void testListWebHooks() throws Exception {
        List<WebHook> list = api.listWebHooks();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        WebHook c = list.get(0);
        checkEntity(c);
    }

    public void testToggleWebHook() throws Exception {
        WebHook c = api.toggleWebHook(entityId, false);
        checkEntity(c);
    }

    public void testWebHookResetSalt() throws Exception {
        String newsalt = api.webHookResetSalt(entityId);
        assertEquals("newsalt", newsalt);
    }


    private void checkEntity(WebHook c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals(true, c.getActive().booleanValue());
        assertEquals("000abcdead", c.getSalt());
        final WebHookSettings settings = c.getSettings();
        assertEquals("http://localhost/test", settings.getUrl());
        assertEquals(false, settings.getBasicAuth().booleanValue());
        assertEquals(true, settings.getInsecureSSL().booleanValue());
        final WebHookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertEquals(333, event.getId().longValue());
        assertEquals("success", event.getLastTriggerStatus());
        assertNotNull(event.getLastResponse());
        WebHookEventResponse wher = event.getLastResponse();
        assertEquals("200", wher.getCode());
        assertEquals("nice event", wher.getBody());
        assertEquals(dFormat.parse("2016-01-18T11:19:20Z"), event.getLastTriggerTime());
        assertEquals(Xplenty.WebHookEvent.job_all, event.getEvent());
        assertEquals("job", event.getName());
    }
}
