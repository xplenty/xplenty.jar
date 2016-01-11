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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author xardas
 *
 */
public class ListProductUpdatesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        ListProductUpdates cc = new ListProductUpdates();
		assertEquals(Xplenty.Resource.ProductUpdates.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.ProductUpdates.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<ProductUpdate> productList = new ArrayList<>();
        ProductUpdate c = MiscTest.createMockProductUpdate(now);
        productList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(productList);

        ListProductUpdates cc = new ListProductUpdates();
        productList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(productList);
        assertTrue(productList.size() > 0);

        c = productList.get(0);
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
        List<ProductUpdate> productList = new ArrayList<>();
        ProductUpdate c = MiscTest.createMockProductUpdate(now);
        productList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(productList).replace("{", "[");

		try {
            ListProductUpdates cc = new ListProductUpdates();
            productList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.ProductUpdates.name + ": error parsing response object", e.getMessage());
		}

	}
}
