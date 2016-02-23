package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Hook;
import com.xplenty.api.model.HookEvent;
import com.xplenty.api.model.WebHookSettings;
import com.xplenty.api.model.HookTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 19:43
 */
public class UpdateHookTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        WebHookSettings whs = new WebHookSettings("http://localhost", false, false, "np");
        List<String> events = new ArrayList<>();
        events.add("job");

        UpdateHook cc = new UpdateHook(1L, null, whs, events);
        assertEquals(Xplenty.Resource.UpdateHook.format("1"), cc.getEndpoint());
        assertEquals(Xplenty.Resource.UpdateHook.name, cc.getName());
        assertEquals(Http.Method.PUT, cc.getHttpMethod());
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
        events.add("cluster");

        UpdateHook cc = new UpdateHook(666L, null, whs, events);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);
        assertEquals(new Long(666), c.getId());
        assertEquals(true, c.getActive().booleanValue());
        assertEquals("000abcdead", c.getSalt());
        final WebHookSettings settings = (WebHookSettings) c.getSettings();
        assertEquals("http://localhost/test", settings.getUrl());
        assertEquals(false, settings.getBasicAuth().booleanValue());
        assertEquals(true, settings.getInsecureSSL().booleanValue());
        final HookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertNull(event.getId());
        assertNull(event.getLastTriggerStatus());
        assertEquals("job", event.getName());
        assertEquals("cluster", c.getEvents().get(1).getName());
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Hook c = HookTest.createMockHook(now);

        WebHookSettings whs = new WebHookSettings("http://localhost/test", true, false, "somedata");
        List<String> events = new ArrayList<>();
        events.add("job");
        events.add("cluster");

        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");
        try {

            UpdateHook cc = new UpdateHook(666L, null, whs, events);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.UpdateHook.name + ": error parsing response object", e.getMessage());
        }

    }
}
