package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.JobTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 08.01.16
 * Time: 20:23
 */
public class JobExecutionVariablesTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        JobExecutionVariables cc = new JobExecutionVariables(666);
        assertEquals(Xplenty.Resource.JobExecVars.format(String.valueOf(666)), cc.getEndpoint());
        assertEquals(Xplenty.Resource.JobExecVars.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Map<String, String> c = JobTest.createMockJobExecutionVariables();

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        JobExecutionVariables cc = new JobExecutionVariables(666);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);

        assertEquals("666", c.get("_ACCOUNT_ID"));
        assertEquals("777", c.get("_ACCOUNT_ID2"));

    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Map<String, String> c = JobTest.createMockJobExecutionVariables();

        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

        try {
            JobExecutionVariables cc = new JobExecutionVariables(666);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.JobExecVars.name + ": error parsing response object", e.getMessage());
        }

    }
}
