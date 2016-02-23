package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.*;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ITWebHookTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/hook";
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
        List<Xplenty.HookEvent> events = new ArrayList<>();
        events.add(Xplenty.HookEvent.cluster_available);
        events.add(Xplenty.HookEvent.cluster_error);
        HookSettings settings = new WebHookSettings("http://localhost", true, true, "md5encodedcredientials");
        Hook c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.web, c.getType());
        checkEntity(c);

        settings = new SlackHookSettings("http://localhost", "xplenty", "xplenty", "xardazz");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.slack, c.getType());
        checkEntity(c);

        List<String> emails = new ArrayList<>();
        emails.add("test@tesr.com");
        settings = new EmailHookSettings(emails);
        c = api.createHook("omg", events, settings);
        assertEquals(Xplenty.HookType.email, c.getType());
        checkEntity(c);

        settings = new PagerDutyHookSettings("xplenty", "xplenty", "aaa");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.pagerduty, c.getType());
        checkEntity(c);

        settings = new HipChatHookSettings("xplenty", "aaaaa");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.hipchat, c.getType());
        checkEntity(c);
    }

    public void testUpdateWebHook() throws Exception {
        List<Xplenty.HookEvent> replaceevents = new ArrayList<>();
        replaceevents.add(Xplenty.HookEvent.job_completed);
        replaceevents.add(Xplenty.HookEvent.job_started);
        WebHookSettings settings = new WebHookSettings("http://localhost", true, true, "md5encodedcredientials");
        Hook c = api.updateHook(entityId, null, replaceevents, settings);
        assertEquals(Xplenty.HookType.web, c.getType());
        checkEntity(c);
        c = api.updateHook(entityId, null, replaceevents, null);
        checkEntity(c);
    }


    public void testDeleteWebHook() throws Exception {
        Hook c = api.deleteWebHook(entityId);
        checkEntity(c);
    }

    public void testGetWebHookInfo() throws Exception {
        Hook c = api.getWebHookInfo(entityId);
        checkEntity(c);
    }

    public void testPingWebHook() throws Exception {
        Hook c = api.pingWebHook(entityId);
        checkEntity(c);
    }

    public void testListWebHooks() throws Exception {
        List<Hook> list = api.listWebHooks();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        Hook c = list.get(0);
        checkEntity(c);
    }

    public void testToggleWebHook() throws Exception {
        Hook c = api.toggleWebHook(entityId, false);
        checkEntity(c);
    }

    public void testWebHookResetSalt() throws Exception {
        String newsalt = api.webHookResetSalt(entityId);
        assertEquals("newsalt", newsalt);
    }


    private void checkEntity(Hook c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals(true, c.getActive().booleanValue());
        assertEquals("000abcdead", c.getSalt());
        switch (c.getType()) {
            case email:
                EmailHookSettings ehs = (EmailHookSettings) c.getSettings();
                assertNotNull(ehs.getEmails());
                assertEquals(2, ehs.getEmails().size());
                assertEquals("a@a.com", ehs.getEmails().get(0));
                assertEquals("b@b.com", ehs.getEmails().get(1));
                break;
            case hipchat:
                HipChatHookSettings hchs = (HipChatHookSettings) c.getSettings();
                assertEquals("aaaa", hchs.getAuthToken());
                assertEquals("xplenty", hchs.getRoom());
                break;
            case pagerduty:
                PagerDutyHookSettings pdhs = (PagerDutyHookSettings) c.getSettings();
                assertEquals("xplenty1", pdhs.getPdAccount());
                assertEquals("xplenty", pdhs.getServiceName());
                assertEquals("aaaaaa", pdhs.getServiceKey());
                break;
            case slack:
                SlackHookSettings shs = (SlackHookSettings) c.getSettings();
                assertEquals("xplenty", shs.getTeam());
                assertEquals("xplentych", shs.getChannel());
                assertEquals("http://localhost", shs.getUrl());
                assertEquals("xardazz", shs.getUsername());
                break;
            default:
                final WebHookSettings whSettings = (WebHookSettings) c.getSettings();
                assertEquals("http://localhost/test", whSettings.getUrl());
                assertEquals(false, whSettings.getBasicAuth().booleanValue());
                assertEquals(true, whSettings.getInsecureSSL().booleanValue());
        }

        final HookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertEquals(333, event.getId().longValue());
        assertEquals("success", event.getLastTriggerStatus());
        assertNotNull(event.getLastResponse());
        WebHookEventResponse wher = event.getLastResponse();
        assertEquals("200", wher.getCode());
        assertEquals("nice event", wher.getBody());
        assertEquals(dFormat.parse("2016-01-18T11:19:20Z"), event.getLastTriggerTime());
        assertEquals(Xplenty.HookEvent.job_all, event.getEvent());
        assertEquals("job", event.getName());
    }
}
