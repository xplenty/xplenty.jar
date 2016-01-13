package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 14:18
 */
public class UserTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        User user = createMockUser(now, false);
        assertNotNull(user);
        assertEquals(now.getTime(), user.getConfirmedAt().getTime());

    }

    public static User createMockUser(Date now, boolean hasApiKey) {
        User user = new User();
        user.email ="test@xplenty.com";
        user.receiveNewsLetter = true;
        user.confirmedAt = now;
        user.name = "Vasya Pupkin";
        user.id = 34L;
        user.location = "Colorado";
        user.timeZone = "UTC";
        user.notificationsCount = 7;
        user.unreadNotificationsCount = 3;
        user.notificationSettings = new UserNotificationSettings(true, false);
        user.createdAt = now;
        user.updatedAt = now;
        user.confirmed = true;
        user.lastLogin = now;
        user.gravatarEmail = "test@gravatar.com";
        user.avatarUrl = "https://secure.gravatar.com/avatar/8318e89006033f0f8eec181f1fcec54e276.png";
        user.url = "https://api.xplenty.com/user";
        if (hasApiKey) {
            user.apiKey = "yepitsapikey";
        }
        return user;

    }

}
