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
import com.xplenty.api.model.User;
import com.xplenty.api.model.UserTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xardas
 *
 */
public class CurrentUserInfoTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        final Date now = new Date();
        CurrentUserInfo cc = new CurrentUserInfo(null);
		assertEquals(Xplenty.Resource.User.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.User.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        cc = new CurrentUserInfo("hhh");
        assertTrue(cc.hasBody());
        assertNotNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
		User c = UserTest.createMockUser(now, true);
		
		String json = JsonMapperFactory.getInstance().writeValueAsString(c);
        CurrentUserInfo cc = new CurrentUserInfo("hhh");
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(34), c.getId());
        assertEquals("yepitsapikey", c.getApiKey());
		assertEquals("Vasya Pupkin", c.getName());
		assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
		assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - c.getConfirmedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - c.getLastLogin().getTime()) < 1000);
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

        c = UserTest.createMockUser(now, false);

        json = JsonMapperFactory.getInstance().writeValueAsString(c);
        cc = new CurrentUserInfo(null);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);
        assertEquals(new Long(34), c.getId());
        assertEquals(null, c.getApiKey());
        assertEquals("Vasya Pupkin", c.getName());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000);
        assertTrue(Math.abs(now.getTime() - c.getConfirmedAt().getTime()) < 1000);
        assertTrue(Math.abs(now.getTime() - c.getLastLogin().getTime()) < 1000);
        assertEquals("https://api.xplenty.com/user", c.getUrl());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        User c = UserTest.createMockUser(now, false);

        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "]");
        CurrentUserInfo cc = new CurrentUserInfo(null);
		try {
			c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			fail();
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.User.name + ": error parsing response object", e.getMessage());
		}
	}
}
