package com.xplenty.api.request.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Author: Xardas
 * Date: 23.02.16
 * Time: 19:02
 */
public class ResetUserPasswordTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {
        ResetUserPassword cc = new ResetUserPassword("test@test.com");
        assertEquals(Xplenty.Resource.ResetUserPassword.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.ResetUserPassword.name, cc.getName());
        assertEquals(Http.Method.POST, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertTrue(cc.hasBody());
        assertNotNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {

        ResetUserPassword cc = new ResetUserPassword("test@test.com");
        User c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
            "",
            ClientResponse.Status.CREATED.getStatusCode(),
            new HashMap<String, String>()));

        assertNull(c);
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {

        ResetUserPassword cc = new ResetUserPassword("test@test.com");
        try {
            final Response response = Response.forContentType(Http.MediaType.JSON,
                "",
                ClientResponse.Status.BAD_REQUEST.getStatusCode(),
                new HashMap<String, String>());
            response.validate(cc.getName());
            User c = cc.getResponse(response);
            fail();
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.ResetUserPassword.name + " failed, HTTP status code: 400[Bad Request: The request was invalid. An accompanying error message will explain why.], server response: []", e.getMessage());
        }
    }
}
