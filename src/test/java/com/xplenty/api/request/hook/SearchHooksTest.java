package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Hook;
import com.xplenty.api.model.HookTest;
import com.xplenty.api.model.SearchQueryTest;
import com.xplenty.api.model.WebHookSettings;
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
public class SearchHooksTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        Date now = new Date();
        SearchHooks cc = new SearchHooks(SearchQueryTest.createMockSearchQuery(now).toProperties());
        assertEquals(Xplenty.Resource.SearchHooks.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.SearchHooks.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertTrue(cc.hasBody());
        assertNotNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Hook> whList = new ArrayList<>();

        Hook c = HookTest.createMockHook(now);
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList);

        SearchHooks cc = new SearchHooks(SearchQueryTest.createMockSearchQuery(now).toProperties());
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
        final Xplenty.HookEvent event = c.getEvents().get(0);
        // we've got custom json serializer that removes everything except name
        assertEquals(Xplenty.HookEvent.job_all, event);
        assertEquals("cluster", c.getRawEvents().get(1));
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Hook> whList = new ArrayList<>();

        Hook c = HookTest.createMockHook(now);
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList).replace("{", "[");

        try {

            SearchHooks cc = new SearchHooks(SearchQueryTest.createMockSearchQuery(now).toProperties());
            whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.SearchHooks.name + ": error parsing response object", e.getMessage());
        }

    }
}
