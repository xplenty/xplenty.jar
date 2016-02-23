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
public class HookTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Hook webhook = createMockHook(now);
        assertNotNull(webhook);
        assertEquals(now.getTime(), webhook.getEvents().get(0).getLastTriggerTime().getTime());

    }


    public static Hook createMockHook(Date now) {
        Hook hook = new Hook();
        hook.id = 666L;
        hook.name = "test";
        hook.active = true;
        hook.salt = "000abcdead";
        WebHookSettings whs = new WebHookSettings("http://localhost/test", true, false, "somedata");
        whs.encryptedBasicAuthData = "wtjnIcvVp1fLC2fy9rAsSQ==\\n";
        hook.settings = whs;
        List<HookEvent> events = new ArrayList<>();
        HookEvent whe = new HookEvent();
        whe.id = 111L;
        whe.lastTriggerStatus = "omg!";
        whe.lastTriggerTime = now;
        whe.name = "job";
        events.add(whe);
        whe = new HookEvent();
        whe.id = 222L;
        whe.lastTriggerStatus = "omg2!";
        whe.lastTriggerTime = now;
        whe.name = "cluster";
        events.add(whe);
        hook.events = events;
        return hook;
    }
}
