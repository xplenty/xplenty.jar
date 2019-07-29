package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.JobLog;
import com.xplenty.api.model.JobTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * Author: Xardas
 * Date: 08.01.16
 * Time: 20:23
 */
public class JobLogsTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        JobLogs cc = new JobLogs(666);
        assertEquals(Xplenty.Resource.JobLog.format(String.valueOf(666)), cc.getEndpoint());
        assertEquals(Xplenty.Resource.JobLog.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        JobLog c = JobTest.createMockJobLog();

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        JobLogs cc = new JobLogs(666);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);

        assertEquals("Invalid output path: couldn't fetch", c.getLog());
        assertEquals("https://testapi.xplenty.com/api/jobs/666/log", c.getUrl());


    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        JobLog c = JobTest.createMockJobLog();

        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

        try {
            JobLogs cc = new JobLogs(666);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.JobLog.name + ": error parsing response object", e.getMessage());
        }

    }
}
