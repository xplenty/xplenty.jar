/**
 * 
 */
package com.xplenty.api.request.public_key;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.model.PublicKeyTest;
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
public class DeletePublicKeyTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        DeletePublicKey cc = new DeletePublicKey(666);
		assertEquals(Xplenty.Resource.DeletePublicKey.format(String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.DeletePublicKey.name, cc.getName());
		assertEquals(Http.Method.DELETE, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		PublicKey c = PublicKeyTest.createMockPublicKey(now);

		String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        DeletePublicKey cc = new DeletePublicKey(666);
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
		assertEquals("xardazz@github.com", c.getComment());
		assertEquals("ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff", c.getFingerprint());
        assertEquals("test", c.getName());
        assertEquals("https://testapi.xplenty.com/user/keys/666", c.getUrl());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        PublicKey c = PublicKeyTest.createMockPublicKey(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

		try {
            DeletePublicKey cc = new DeletePublicKey(666);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.DeletePublicKey.name + ": error parsing response object", e.getMessage());
		}

	}
}
