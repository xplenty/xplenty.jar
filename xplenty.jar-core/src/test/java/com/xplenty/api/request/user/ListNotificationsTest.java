/**
 * 
 */
package com.xplenty.api.request.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Notification;
import com.xplenty.api.model.NotificationTest;
import com.xplenty.api.request.AbstractListRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author xardas
 *
 */
public class ListNotificationsTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties props = new Properties();
        ListNotifications cc = new ListNotifications(props);
		assertEquals(Xplenty.Resource.UserNotifications.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.UserNotifications.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        cc = new ListNotifications(props);
        assertEquals(Xplenty.Resource.UserNotifications.value + "?" + AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());

	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<Notification> notifications = new ArrayList<>();
        notifications.add(NotificationTest.createMockNotification(now));

		String json = JsonMapperFactory.getInstance().writeValueAsString(notifications);

        ListNotifications cc = new ListNotifications(new Properties());
        notifications = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(notifications);
        assertTrue(notifications.size() > 0);

        Notification c = notifications.get(0);
		assertEquals(new Long(666), c.getId());
		assertEquals("Cluster available", c.getTitle());
		assertEquals("Cluster is available", c.getMessage());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getLastReadAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Notification> notifications = new ArrayList<>();
        notifications.add(NotificationTest.createMockNotification(now));
        String json = JsonMapperFactory.getInstance().writeValueAsString(notifications).replace("{", "[");

		try {
            ListNotifications cc = new ListNotifications(new Properties());
            notifications = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.UserNotifications.name + ": error parsing response object", e.getMessage());
		}

	}
}
