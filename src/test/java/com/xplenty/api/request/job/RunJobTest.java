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
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobTest;
import com.xplenty.api.request.job.RunJob;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Yuriy Kovalek
 *
 */
public class RunJobTest extends TestCase {
	@Test
	public void testIntegrity() {
		RunJob lc = new RunJob(JobTest.createMockJob(new Date()));
		
		assertEquals(Xplenty.Resource.RunJob.value, lc.getEndpoint());
		assertEquals(Xplenty.Resource.RunJob.name, lc.getName());
		assertEquals(Http.Method.POST, lc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lc.getResponseType());
		assertTrue(lc.hasBody());
		assertNotNull(lc.getBody());
	}

	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		RunJob lc = new RunJob(JobTest.createMockJob(new Date()));
		Job j = JobTest.createMockJob(new Date());
		
		String json = new ObjectMapper().writeValueAsString(j);
		j = lc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		assertNotNull(j);
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		RunJob lc = new RunJob(JobTest.createMockJob(new Date()));
		Job j = JobTest.createMockJob(new Date());
		
		String json = new ObjectMapper().writeValueAsString(j).replace("7", "seven");
		try {
			j = lc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			fail();
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.RunJob.name + ": error parsing response object", e.getMessage());
		} 
	}
}
