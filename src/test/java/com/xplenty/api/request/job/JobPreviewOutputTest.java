/**
 * 
 */
package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.JobOutputPreview;
import com.xplenty.api.model.JobTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xardazz
 *
 */
public class JobPreviewOutputTest extends TestCase {

	@Test
	public void testIntegrity() {
		JobPreviewOutput ji = new JobPreviewOutput(666, 777);
		
		assertEquals(Xplenty.Resource.JobPreviewOutput.format(String.valueOf(666), String.valueOf(777)), ji.getEndpoint());
		assertEquals(Xplenty.Resource.JobPreviewOutput.name, ji.getName());
		assertEquals(Http.Method.GET, ji.getHttpMethod());
		assertEquals(Http.MediaType.JSON, ji.getResponseType());
		assertFalse(ji.hasBody());
		assertNull(ji.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
        JobPreviewOutput ji = new JobPreviewOutput(666, 777);
		JobOutputPreview j = JobTest.createMockJobOutputPreview();
		String json = new ObjectMapper().writeValueAsString(j);
		
		j = ji.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(j);
        assertEquals("out1\tout2\tout3", j.getPreview());
        assertEquals("https://testapi.xplenty.com/api/jobs/1/outputs/1/preview", j.getUrl());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
        JobPreviewOutput ji = new JobPreviewOutput(666, 777);
        JobOutputPreview j = JobTest.createMockJobOutputPreview();
		String json = new ObjectMapper().writeValueAsString(j).replace("{", "[");
		
		try {
			j = ji.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.JobPreviewOutput.name + ": error parsing response object", e.getMessage());
		}
	}
}
