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
import com.xplenty.api.model.Stack;
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
public class ListStacksTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        ListStacks cc = new ListStacks();
		assertEquals(Xplenty.Resource.Stacks.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.Stacks.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<Stack> stacks = new ArrayList<>();
        stacks.add(MiscTest.createMockStack());

		String json = JsonMapperFactory.getInstance().writeValueAsString(stacks);

        ListStacks cc = new ListStacks();
        stacks = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(stacks);
        assertTrue(stacks.size() > 0);

        Stack c = stacks.get(0);

		assertEquals("blue-everest", c.getId());
		assertEquals("Blue Everest", c.getName());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Stack> stacks = new ArrayList<>();
        stacks.add(MiscTest.createMockStack());
        String json = JsonMapperFactory.getInstance().writeValueAsString(stacks).replace("{", "[");

		try {
            ListStacks cc = new ListStacks();
            stacks = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Stacks.name + ": error parsing response object", e.getMessage());
		}

	}
}
