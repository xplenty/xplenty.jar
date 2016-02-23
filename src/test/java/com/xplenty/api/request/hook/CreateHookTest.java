/**
 * 
 */
package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Hook;
import com.xplenty.api.model.HookTest;
import com.xplenty.api.model.WebHookSettings;
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
public class CreateHookTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        WebHookSettings whs = new WebHookSettings("http://localhost", false, false, "np");
        List<String> events = new ArrayList<>();
        events.add("job");

        CreateHook cc = new CreateHook("test", whs, events);
		assertEquals(Xplenty.Resource.CreateHook.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.CreateHook.name, cc.getName());
		assertEquals(Http.Method.POST, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertTrue(cc.hasBody());
		assertNotNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Hook c = HookTest.createMockHook(now);
		
		String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        WebHookSettings whs = new WebHookSettings("http://localhost/test", true, false, "somedata");
        List<String> events = new ArrayList<>();
        events.add("job");

        CreateHook cc = new CreateHook("test", whs, events);
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.CREATED.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
        assertEquals("test", c.getName());
		assertEquals(true, c.getActive().booleanValue());
		assertEquals("000abcdead", c.getSalt());
        final WebHookSettings settings = (WebHookSettings) c.getSettings();
        assertEquals("http://localhost/test", settings.getUrl());
        assertEquals(false, settings.getBasicAuth().booleanValue());
        assertEquals(true, settings.getInsecureSSL().booleanValue());
        final Xplenty.HookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertEquals(Xplenty.HookEvent.job_all, event);
        assertEquals("cluster", c.getRawEvents().get(1));
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Hook c = HookTest.createMockHook(now);

        WebHookSettings whs = new WebHookSettings("http://localhost/test", true, false, "somedata");
        List<String> events = new ArrayList<>();
        events.add("job");

        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");
		try {
            CreateHook cc = new CreateHook("test", whs, events);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.CREATED.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.CreateHook.name + ": error parsing response object", e.getMessage());
		}

	}
}
