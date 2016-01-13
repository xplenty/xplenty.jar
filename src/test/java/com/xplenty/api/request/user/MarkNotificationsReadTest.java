/**
 * 
 */
package com.xplenty.api.request.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
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
public class MarkNotificationsReadTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        MarkNotificationsRead cc = new MarkNotificationsRead();
		assertEquals(Xplenty.Resource.MarkUserNotificationRead.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.MarkUserNotificationRead.name, cc.getName());
		assertEquals(Http.Method.POST, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        MarkNotificationsRead cc = new MarkNotificationsRead();
		Object c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                null,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNull(c);

	}
	
	@Test
	public void testBadResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        MarkNotificationsRead cc = new MarkNotificationsRead();
        Response resp = Response.forContentType(Http.MediaType.JSON,
                null,
                Status.UNAUTHORIZED.getStatusCode(),
                new HashMap<String, String>());
        assertFalse(resp.isValid());

	}
}
