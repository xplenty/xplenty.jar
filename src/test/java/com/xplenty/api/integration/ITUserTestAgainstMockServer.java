package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Notification;
import com.xplenty.api.model.User;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ITUserTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/user";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long userId = 666L;
    private Long notificationId = 777L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testGetUserInfo() throws Exception {
        User c = api.getCurrentUserInfo();
        checkUser(c, false);
        c = api.getCurrentUserInfo("supersecretpassword");
        checkUser(c, true);
    }

    public void testUpdateCurrentUser() throws Exception {
        User c = new User();
        c.setEmail("omg@omg.com");
        c.setLocation("Colorado");
        c.setCurrentPassword("supersecretpassword");
        c.setNewPassword("123");
        c.setGravatarEmail("grav@grav.com");
        c.setName("Xardazz");
        c.setReceiveNewsLetter(true);
        c.setTimeZone("UTC");
        c = api.updateCurrentUser(c);
        checkUser(c, false);
    }

    public void testListNotifications() throws Exception {
        List<Notification> list = api.listUserNotifications();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Notification c = list.get(0);
        checkNotification(c);
    }

    public void testMarkNotificationsRead() throws Exception {
        Void c = api.markNotificationAsRead();
        assertNull(c);
    }

    private void checkUser(User c, boolean withApiKey) throws ParseException {
        assertNotNull(c);
        assertEquals(userId, c.getId());

        if (withApiKey) {
            assertEquals("yepitsapikey", c.getApiKey());
        } else {
            assertNull(c.getApiKey());
        }
        assertEquals("Vasya Pupkin", c.getName());
        assertEquals(dFormat.parse("2016-01-17T19:41:12Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-17T19:41:12Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-17T19:41:12Z"), c.getLastLogin());
        assertEquals(dFormat.parse("2016-01-17T19:41:12Z"), c.getConfirmedAt());
        assertEquals(true, c.isReceiveNewsLetter().booleanValue());
        assertEquals("https://secure.gravatar.com/avatar/8318e89006033f0f8eec181f1fcec54e276.png", c.getAvatarUrl());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@gravatar.com", c.getGravatarEmail());
        assertEquals("UTC", c.getTimeZone());
        assertEquals("Colorado", c.getLocation());
        assertEquals(true, c.isConfirmed().booleanValue());
        assertEquals(7, c.getNotificationsCount().intValue());
        assertEquals(3, c.getUnreadNotificationsCount().intValue());
        assertEquals(true, c.getNotificationSettings().getEmail().booleanValue());
        assertEquals(false, c.getNotificationSettings().getWeb().booleanValue());
        assertEquals("https://api.xplenty.com/user", c.getUrl());

    }

    private void checkNotification(Notification c) throws ParseException {
        assertNotNull(c);
        assertEquals(notificationId, c.getId());
        assertEquals("Cluster available", c.getTitle());
        assertEquals("Cluster is available", c.getMessage());
        assertEquals(dFormat.parse("2016-01-17T19:42:18Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-17T19:42:18Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-17T19:42:18Z"), c.getLastReadAt());
    }
}
