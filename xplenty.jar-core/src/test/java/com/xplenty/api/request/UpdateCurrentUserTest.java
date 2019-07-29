/**
 * 
 */
package com.xplenty.api.request;

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
public class UpdateCurrentUserTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        final Date now = new Date();
        UpdateCurrentUser cc = new UpdateCurrentUser(UserTest.createMockUser(now, false));
		assertEquals(Xplenty.Resource.UpdateUser.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.UpdateUser.name, cc.getName());
		assertEquals(Http.Method.PUT, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertTrue(cc.hasBody());
		assertNotNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
		User c = UserTest.createMockUser(now, false);
		
		String json = JsonMapperFactory.getInstance().writeValueAsString(c);
        UpdateCurrentUser cc = new UpdateCurrentUser(c);
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(34), c.getId());
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
        UpdateCurrentUser cc = new UpdateCurrentUser(c);
		try {
			c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			fail();
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.UpdateUser.name + ": error parsing response object", e.getMessage());
		}
	}
}
