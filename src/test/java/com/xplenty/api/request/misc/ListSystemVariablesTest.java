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
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xardas
 *
 */
public class ListSystemVariablesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        ListSystemVariables cc = new ListSystemVariables();
		assertEquals(Xplenty.Resource.SystemVariables.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.SystemVariables.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        Map<String, String> sysVars = MiscTest.createMockSystemVars();

		String json = JsonMapperFactory.getInstance().writeValueAsString(sysVars);

        ListSystemVariables cc = new ListSystemVariables();
        sysVars = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(sysVars);
        assertTrue(sysVars.size() > 0);

		assertEquals("777777", sysVars.get("_MAX_COMBINED_SPLIT_SIZE"));
		assertEquals("666666", sysVars.get("_BYTES_PER_REDUCER"));
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Map<String, String> sysVars = MiscTest.createMockSystemVars();
        String json = JsonMapperFactory.getInstance().writeValueAsString(sysVars).replace("{", "[");

		try {
            ListSystemVariables cc = new ListSystemVariables();
            sysVars = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.SystemVariables.name + ": error parsing response object", e.getMessage());
		}

	}
}
