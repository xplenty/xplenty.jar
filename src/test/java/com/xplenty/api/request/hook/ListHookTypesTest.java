package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.AvailableHookType;
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
public class ListHookTypesTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        ListHookTypes cc = new ListHookTypes();
        assertEquals(Xplenty.Resource.HookTypes.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.HookTypes.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        List<AvailableHookType> whList = new ArrayList<>();

        AvailableHookType c = HookTest.createMockHookType();
        whList.add(c);


        String json = JsonMapperFactory.getInstance().writeValueAsString(whList);

        ListHookTypes cc = new ListHookTypes();
        whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        c = whList.get(0);
        assertNotNull(c);
        assertEquals("Email", c.getName());
        assertEquals("email", c.getType());
        assertEquals("Our Email integration enables you to receive real-time email alerts about your account activity.", c.getDescription());
        assertEquals("http://api.xplenty.com/assets/vendor/hooks/emailhook-9231bb4b71377e2722ceb6b581ecfaf4.png", c.getIconUrl());
        assertNotNull(c.getGroups());
        assertEquals("Email", c.getGroups().get(0).getGroupName());
        assertEquals("email", c.getGroups().get(0).getGroupType());
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<AvailableHookType> whList = new ArrayList<>();

        AvailableHookType c = HookTest.createMockHookType();
        whList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(whList).replace("{", "[");

        try {
            ListHookTypes cc = new ListHookTypes();
            whList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.HookTypes.name + ": error parsing response object", e.getMessage());
        }

    }
}
