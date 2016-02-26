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
public class HookResetSaltTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        HookResetSalt cc = new HookResetSalt(1L);
        assertEquals(Xplenty.Resource.HookResetSalt.format("1"), cc.getEndpoint());
        assertEquals(Xplenty.Resource.HookResetSalt.name, cc.getName());
        assertEquals(Http.Method.PUT, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Hook c = HookTest.createMockHook(now);

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        HookResetSalt cc = new HookResetSalt(666);
        String newSalt = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                "{ \"salt\" : \"deadabc\" }",
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertEquals("deadabc", newSalt);
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {

        try {

            HookResetSalt cc = new HookResetSalt(666);
            String newSalt = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    "[[ \"salt\" : \"deadabc\" }",
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));

            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.HookResetSalt.name + ": error parsing response object", e.getMessage());
        }

    }
}
