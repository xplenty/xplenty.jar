package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 09.01.16
 * Time: 16:51
 */
public class NotificationTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Notification notification = createMockNotification(now);
        assertNotNull(notification);
        assertEquals(now.getTime(), notification.getCreatedAt().getTime());

    }

    public static Notification createMockNotification(Date now) {
        Notification notification = new Notification();
        notification.id = 666L;
        notification.title = "Cluster available";
        notification.message = "Cluster is available";
        notification.createdAt = now;
        notification.updatedAt = now;
        notification.lastReadAt = now;
        return notification;
    }
}
