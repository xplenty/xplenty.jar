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
import com.xplenty.api.model.Timezone;
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
public class ListTimezonesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        ListTimezones cc = new ListTimezones();
		assertEquals(Xplenty.Resource.Timezones.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.Timezones.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<Timezone> timezones = new ArrayList<>();
        timezones.add(MiscTest.createMockTimeZone());

		String json = JsonMapperFactory.getInstance().writeValueAsString(timezones);

        ListTimezones cc = new ListTimezones();
        timezones = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(timezones);
        assertTrue(timezones.size() > 0);

        Timezone c = timezones.get(0);

		assertEquals("St. Petersburg", c.getId());
		assertEquals("(GMT+03:00) St. Petersburg", c.getName());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Timezone> timezones = new ArrayList<>();
        timezones.add(MiscTest.createMockTimeZone());
        String json = JsonMapperFactory.getInstance().writeValueAsString(timezones).replace("{", "[");

		try {
            ListTimezones cc = new ListTimezones();
            timezones = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Timezones.name + ": error parsing response object", e.getMessage());
		}

	}
}
