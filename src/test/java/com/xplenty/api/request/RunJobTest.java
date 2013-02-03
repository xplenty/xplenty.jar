/**
 * 
 */
package com.xplenty.api.request;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.InBoundHeaders;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobTest;
import com.xplenty.api.util.Http;

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
	public void testValidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
		RunJob lc = new RunJob(JobTest.createMockJob(new Date()));
		Job j = JobTest.createMockJob(new Date());
		
		String json = new ObjectMapper().writeValueAsString(j);
		j = lc.getResponse(new ClientResponse(Status.OK.getStatusCode(),
								new InBoundHeaders(), 
								new ByteArrayInputStream(json.getBytes("UTF-8")),
								Client.create().getMessageBodyWorkers()));
		assertNotNull(j);
	}
	
	@Test
	public void testInvalidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
		RunJob lc = new RunJob(JobTest.createMockJob(new Date()));
		Job j = JobTest.createMockJob(new Date());
		
		String json = new ObjectMapper().writeValueAsString(j).replace("7", "seven");
		try {
			j = lc.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.RunJob.name + ": error parsing response object", e.getMessage());
		} 
	}
}
