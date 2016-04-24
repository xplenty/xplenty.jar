package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.*;
import com.xplenty.api.request.hook.ListHooks;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ITHookTestAgainstMockServer extends TestCase {
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

    public void testCreateHook() throws Exception {
        List<Xplenty.HookEvent> events = new ArrayList<>();
        events.add(Xplenty.HookEvent.cluster_available);
        events.add(Xplenty.HookEvent.cluster_error);
        HookSettings settings = new WebHookSettings("http://localhost", true, true, "md5encodedcredientials");
        Hook c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.web, c.getType());
        checkEntity(c);
        settings = new WebHookSettings().withBasicAuth(false).withBasicAuthData("somemd5credentials").
            withInsecureSSL(false).withUrl("https://xplenty.com");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.web, c.getType());
        checkEntity(c);

        settings = new SlackHookSettings("http://localhost", "xplenty", "xplenty", "xardazz");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.slack, c.getType());
        checkEntity(c);
        settings = new SlackHookSettings().withChannel("xplentych").withTeam("xplenty").withUrl("https://slack.com").
            withUsername("xardazz");
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
        settings = new PagerDutyHookSettings().withPdAccount("xplenty").withServiceKey("xplentysc").
            withServiceName("xplenty-service");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.pagerduty, c.getType());
        checkEntity(c);

        settings = new HipChatHookSettings("xplenty", "aaaaa");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.hipchat, c.getType());
        checkEntity(c);
        settings = new HipChatHookSettings().withAuthToken("cccccc").withRoom("xplenty");
        c = api.createHook(null, events, settings);
        assertEquals(Xplenty.HookType.hipchat, c.getType());
        checkEntity(c);

        List<String> strEvents = new ArrayList<>();
        strEvents.add("cluster");
        c = api.createHook(null, settings, strEvents);
        assertEquals(Xplenty.HookType.hipchat, c.getType());
        checkEntity(c);
    }

    public void testUpdateHook() throws Exception {
        List<Xplenty.HookEvent> replaceevents = new ArrayList<>();
        replaceevents.add(Xplenty.HookEvent.job_completed);
        replaceevents.add(Xplenty.HookEvent.job_started);
        WebHookSettings settings = new WebHookSettings("http://localhost", true, true, "md5encodedcredientials");
        Hook c = api.updateHook(entityId, null, replaceevents, settings);
        assertEquals(Xplenty.HookType.web, c.getType());
        checkEntity(c);
        c = api.updateHook(entityId, null, replaceevents, null);
        checkEntity(c);

        List<String> strEvents = new ArrayList<>();
        strEvents.add("cluster");
        c = api.updateHook(entityId, "kitty", settings, strEvents);
        assertEquals(Xplenty.HookType.web, c.getType());
        checkEntity(c);
    }


    public void testDeleteHook() throws Exception {
        Hook c = api.deleteHook(entityId);
        checkEntity(c);
    }

    public void testGetHookInfo() throws Exception {
        Hook c = api.getHookInfo(entityId);
        checkEntity(c);
    }

    public void testPingHook() throws Exception {
        Hook c = api.pingHook(entityId);
        checkEntity(c);
    }

    public void testListHooks() throws Exception {
        List<Hook> list = api.listHooks();
        assertNotNull(list);
        assertTrue(list.size() > 0);

        Hook c = list.get(0);
        checkEntity(c);

        list = api.listHooks(5, 55);
        assertNotNull(list);
        assertTrue(list.size() > 0);

        c = list.get(0);
        checkEntity(c);

        Properties props = new Properties();
        props.put(ListHooks.PARAMETER_TYPE, Xplenty.HookType.slack);
        list = api.listHooks(props);
        assertNotNull(list);
        assertTrue(list.size() > 0);

        c = list.get(0);
        checkEntity(c);

        props = new Properties();
        List<Xplenty.HookType> hookTypes = new ArrayList<>();
        hookTypes.add(Xplenty.HookType.email);
        hookTypes.add(Xplenty.HookType.slack);
        props.put(ListHooks.PARAMETER_TYPE, hookTypes);
        list = api.listHooks(props);
        assertNotNull(list);
        assertTrue(list.size() > 0);

        c = list.get(0);
        checkEntity(c);
    }

    public void testToggleHook() throws Exception {
        Hook c = api.toggleHook(entityId, false);
        checkEntity(c);
    }

    public void testHookResetSalt() throws Exception {
        String newsalt = api.hookResetSalt(entityId);
        assertEquals("newsalt", newsalt);
    }

    public void testListHookEvents() throws Exception {
        List<AvailableHookEvent> hookEvents = api.listHookEvents();

        assertNotNull(hookEvents);
        assertTrue(hookEvents.size() > 0);

        checkHookEvent(hookEvents.get(0));
    }

    public void testListHookTypes() throws Exception {
        List<AvailableHookType> hookTypes = api.listHookTypes();

        assertNotNull(hookTypes);
        assertTrue(hookTypes.size() > 0);

        checkHookType(hookTypes.get(0));
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

        final Xplenty.HookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertEquals(Xplenty.HookEvent.job_all, event);
        assertEquals("cluster", c.getRawEvents().get(1));
    }

    private void checkHookEvent(AvailableHookEvent hookEvent) {
        assertNotNull(hookEvent);
        assertEquals("job", hookEvent.getId());
        assertEquals("Job", hookEvent.getGroupName());
        assertEquals("All Job Notifications", hookEvent.getDescription());
    }

    private void checkHookType(AvailableHookType c) {
        assertNotNull(c);
        assertEquals("Email", c.getName());
        assertEquals("email", c.getType());
        assertEquals("Our Email integration enables you to receive real-time email alerts about your account activity.", c.getDescription());
        assertEquals("http://api.xplenty.com/assets/vendor/hooks/emailhook-9231bb4b71377e2722ceb6b581ecfaf4.png", c.getIconUrl());
        assertNotNull(c.getGroups());
        assertEquals("Email", c.getGroups().get(0).getGroupName());
        assertEquals("email", c.getGroups().get(0).getGroupType());
    }
}
