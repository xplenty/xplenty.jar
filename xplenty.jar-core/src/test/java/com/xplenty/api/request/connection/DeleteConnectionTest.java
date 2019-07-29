/**
 * 
 */
package com.xplenty.api.request.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Connection;
import com.xplenty.api.model.ConnectionTest;
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
public class DeleteConnectionTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        DeleteConnection cc = new DeleteConnection(666, Xplenty.ConnectionType.mysql);
		assertEquals(Xplenty.Resource.DeleteConnection.format(Xplenty.ConnectionType.mysql.toString(), String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.DeleteConnection.name, cc.getName());
		assertEquals(Http.Method.DELETE, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Connection c = ConnectionTest.createMockConnection(now);

		String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        DeleteConnection cc = new DeleteConnection(666, Xplenty.ConnectionType.mysql);
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
		assertEquals(Xplenty.ConnectionType.mysql, c.getType());
        assertEquals("test", c.getName());
        assertEquals("https://testapi.xplenty.com/api/connections/mysql/666", c.getUrl());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Connection c = ConnectionTest.createMockConnection(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

		try {
            DeleteConnection cc = new DeleteConnection(666, Xplenty.ConnectionType.mysql);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.DeleteConnection.name + ": error parsing response object", e.getMessage());
		}

	}
}
