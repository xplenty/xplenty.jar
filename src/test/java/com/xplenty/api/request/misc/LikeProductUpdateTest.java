/**
 * 
 */
package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.MiscTest;
import com.xplenty.api.model.ProductUpdate;
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
public class LikeProductUpdateTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        LikeProductUpdate cc = new LikeProductUpdate(666);
		assertEquals(Xplenty.Resource.LikeProductUpdate.format(String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.LikeProductUpdate.name, cc.getName());
		assertEquals(Http.Method.POST, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        ProductUpdate c = MiscTest.createMockProductUpdate(now);

		String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        LikeProductUpdate cc = new LikeProductUpdate(666);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertEquals("Breaking news", c.getTitle());
        assertEquals("Now you can cross-join!", c.getBody());
        assertEquals("<b>Now you can cross-join</b>", c.getBodyHtml());
        assertEquals("now you can cross-join\n", c.getBodyText());
        assertEquals(666, c.getId().longValue());
        assertEquals(15, c.getLikes().longValue());
        assertTrue(c.getLiked());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        ProductUpdate c = MiscTest.createMockProductUpdate(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

		try {
            LikeProductUpdate cc = new LikeProductUpdate(666);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.LikeProductUpdate.name + ": error parsing response object", e.getMessage());
		}

	}
}
