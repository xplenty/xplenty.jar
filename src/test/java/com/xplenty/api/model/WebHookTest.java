package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 19:02
 */
public class WebHookTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        WebHook webhook = createMockWebHook(now);
        assertNotNull(webhook);
        assertEquals(now.getTime(), webhook.getEvents().get(0).getLastTriggerTime().getTime());

    }


    public static WebHook createMockWebHook(Date now) {
        WebHook webHook = new WebHook();
        webHook.id = 666L;
        webHook.active = true;
        webHook.salt = "000abcdead";
        WebHookSettings whs = new WebHookSettings("http://localhost/test", true, false, "somedata");
        whs.encryptedBasicAuthData = "wtjnIcvVp1fLC2fy9rAsSQ==\\n";
        webHook.settings = whs;
        List<WebHookEvent> events = new ArrayList<>();
        WebHookEvent whe = new WebHookEvent();
        whe.id = 111L;
        whe.lastTriggerStatus = "omg!";
        whe.lastTriggerTime = now;
        whe.name = "job";
        events.add(whe);
        whe = new WebHookEvent();
        whe.id = 222L;
        whe.lastTriggerStatus = "omg2!";
        whe.lastTriggerTime = now;
        whe.name = "cluster";
        events.add(whe);
        webHook.events = events;
        return webHook;
    }
}
