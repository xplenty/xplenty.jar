package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.AvailableHookEvent;
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
public class ListHookEventsTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        ListHookEvents cc = new ListHookEvents();
        assertEquals(Xplenty.Resource.HookEvents.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.HookEvents.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        List<AvailableHookEvent> whList = new ArrayList<>();

        AvailableHookEvent c = HookTest.createMockHookEvent();
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList);

        ListHookEvents cc = new ListHookEvents();
        whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        c = whList.get(0);
        assertNotNull(c);
        assertEquals("job", c.getId());
        assertEquals("Job", c.getGroupName());
        assertEquals("All Job Notifications", c.getDescription());
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<AvailableHookEvent> whList = new ArrayList<>();

        AvailableHookEvent c = HookTest.createMockHookEvent();
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList).replace("{", "[");

        try {

            ListHookEvents cc = new ListHookEvents();
            whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.HookEvents.name + ": error parsing response object", e.getMessage());
        }

    }
}
