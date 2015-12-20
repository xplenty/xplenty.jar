/**
 * 
 */
package com.xplenty.api.request;

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
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author Yuriy Kovalek
 *
 */
public class StopJobTest extends TestCase {
	@Test
	public void testIntegrity() {
		StopJob sj = new StopJob(1L);
		
		assertEquals(Xplenty.Resource.StopJob.format(Long.toString(1)), sj.getEndpoint());
		assertEquals(Xplenty.Resource.StopJob.name, sj.getName());
		assertEquals(Http.Method.DELETE, sj.getHttpMethod());
		assertEquals(Http.MediaType.JSON, sj.getResponseType());
		assertFalse(sj.hasBody());
		assertNull(sj.getBody());
	}

	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		StopJob sj = new StopJob(1L);
		Job j = JobTest.createMockJob(new Date());
		
		String json = new ObjectMapper().writeValueAsString(j);
		j = sj.getResponse(new ClientResponse(Status.OK.getStatusCode(),
								new InBoundHeaders(), 
								new ByteArrayInputStream(json.getBytes("UTF-8")),
								Client.create().getMessageBodyWorkers()));
		assertNotNull(j);
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		StopJob sj = new StopJob(1L);
		Job j = JobTest.createMockJob(new Date());
		
		String json = new ObjectMapper().writeValueAsString(j).replace("7", "seven");
		try {
			j = sj.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.StopJob.name + ": error parsing response object", e.getMessage());
		} 
	}
}
