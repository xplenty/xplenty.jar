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
public class ListWebHookTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        ListWebHooks cc = new ListWebHooks(new Properties());
        assertEquals(Xplenty.Resource.WebHooks.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.WebHooks.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());


        final Properties props = new Properties();
        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.created);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        cc = new ListWebHooks(props);
        assertEquals(Xplenty.Resource.WebHooks.value + "?" + AbstractListRequest.PARAMETER_SORT + "=created&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());
        assertEquals(Xplenty.Resource.WebHooks.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<WebHook> whList = new ArrayList<>();

        WebHook c = WebHookTest.createMockWebHook(now);
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList);

        ListWebHooks cc = new ListWebHooks(new Properties());
        whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        c = whList.get(0);
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
        List<WebHook> whList = new ArrayList<>();

        WebHook c = WebHookTest.createMockWebHook(now);
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList).replace("{", "[");

        try {

            ListWebHooks cc = new ListWebHooks(new Properties());
            whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.WebHooks.name + ": error parsing response object", e.getMessage());
        }

    }
}
