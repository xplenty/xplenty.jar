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
import com.xplenty.api.request.AbstractListRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Author: Xardas
 * Date: 05.01.16
 * Time: 19:43
 */
public class ListHookTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        ListHooks cc = new ListHooks(new Properties());
        assertEquals(Xplenty.Resource.Hooks.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.Hooks.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());


        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.created);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        cc = new ListHooks(props);
        assertEquals(Xplenty.Resource.Hooks.value + "?" + AbstractListRequest.PARAMETER_SORT + "=created&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());
        assertEquals(Xplenty.Resource.Hooks.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Hook> whList = new ArrayList<>();

        Hook c = HookTest.createMockHook(now);
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList);

        ListHooks cc = new ListHooks(new Properties());
        whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        c = whList.get(0);
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
        List<Hook> whList = new ArrayList<>();

        Hook c = HookTest.createMockHook(now);
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList).replace("{", "[");

        try {

            ListHooks cc = new ListHooks(new Properties());
            whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.Hooks.name + ": error parsing response object", e.getMessage());
        }

    }
}
