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
        List<String> events = new ArrayList<>();
        events.add("job");
        events.add("cluster");
        hook.events = events;
        return hook;
    }

    public static AvailableHookType createMockHookType() {
        AvailableHookType hookType = new AvailableHookType();
        hookType.name = "Email";
        hookType.type = "email";
        hookType.description = "Our Email integration enables you to receive real-time email alerts about your account activity.";
        hookType.iconUrl = "http://api.xplenty.com/assets/vendor/hooks/emailhook-9231bb4b71377e2722ceb6b581ecfaf4.png";
        AvailableHookType.HookTypeGroup hookGroup = new AvailableHookType.HookTypeGroup();
        hookGroup.groupName = "Email";
        hookGroup.groupType = "email";
        List<AvailableHookType.HookTypeGroup> hookGroups = new ArrayList<>();
        hookGroups.add(hookGroup);
        hookType.groups = hookGroups;
        return hookType;
    }

    public static AvailableHookEvent createMockHookEvent() {
        AvailableHookEvent hookEvent = new AvailableHookEvent();
        hookEvent.id = "job";
        hookEvent.groupName = "Job";
        hookEvent.description = "All Job Notifications";
        return hookEvent;
    }
}
