package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:34
 */
public class SubscriptionTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Subscription sub = createMockSubscription(now);
        assertNotNull(sub);
        assertEquals(now.getTime(), sub.getTrialStart().getTime());
    }


    public static Subscription createMockSubscription(Date now) {
        Subscription sub = new Subscription();
        sub.planId = "free---777";
        sub.trialEnd = now;
        sub.trialStart = now;
        sub.trialPeriodDays = 666;
        sub.isTrial = true;
        sub.url = "https://testapi.xplenty.com/api/subscription";
        return sub;
    }
}
