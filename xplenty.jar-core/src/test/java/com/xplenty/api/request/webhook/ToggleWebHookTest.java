package com.xplenty.api.request.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.model.WebHookEvent;
import com.xplenty.api.model.WebHookSettings;
import com.xplenty.api.model.WebHookTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 19:43
 */
public class ToggleWebHookTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        ToggleWebHook cc = new ToggleWebHook(1L, false);
        assertEquals(Xplenty.Resource.UpdateWebHook.format("1"), cc.getEndpoint());
        assertEquals(Xplenty.Resource.UpdateWebHook.name, cc.getName());
        assertEquals(Http.Method.PUT, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertTrue(cc.hasBody());
        assertNotNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        WebHook c = WebHookTest.createMockWebHook(now);

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        ToggleWebHook cc = new ToggleWebHook(1L, true);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);
        assertEquals(new Long(666), c.getId());
        assertEquals(true, c.getActive().booleanValue());
        assertEquals("000abcdead", c.getSalt());
        final WebHookSettings settings = c.getSettings();
        assertEquals("http://localhost/test", settings.getUrl());
        assertEquals(false, settings.getBasicAuth().booleanValue());
        assertEquals(true, settings.getInsecureSSL().booleanValue());
        final WebHookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertNull(event.getId());
        assertNull(event.getLastTriggerStatus());
        assertEquals("job", event.getName());
        assertEquals("cluster", c.getEvents().get(1).getName());
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        WebHook c = WebHookTest.createMockWebHook(now);

        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");
        try {

            ToggleWebHook cc = new ToggleWebHook(1L, true);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.UpdateWebHook.name + ": error parsing response object", e.getMessage());
        }

    }
}
