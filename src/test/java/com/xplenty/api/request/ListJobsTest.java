/**
 * 
 */
package com.xplenty.api.request;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class ListJobsTest extends TestCase {
	@Test
	public void testIntegrity() {
		ListJobs lj = new ListJobs();
		
		assertEquals(Xplenty.Resource.Jobs.value, lj.getEndpoint());
		assertEquals(Xplenty.Resource.Jobs.name, lj.getName());
		assertEquals(Http.Method.GET, lj.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lj.getResponseType());
		assertFalse(lj.hasBody());
		assertNull(lj.getBody());
	}
	
	@Test
	public void testValidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListJobs lj = new ListJobs();
		List<Job> list = new ArrayList<Job>();
		list.add(JobTest.createMockJob(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list);
		list = lj.getResponse(new ClientResponse(Status.OK.getStatusCode(),
								new InBoundHeaders(), 
								new ByteArrayInputStream(json.getBytes("UTF-8")),
								Client.create().getMessageBodyWorkers()));
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testInvalidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListJobs lj = new ListJobs();
		List<Job> list = new ArrayList<Job>();
		list.add(JobTest.createMockJob(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("[", "");
		try {
			list = lj.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Jobs.name + ": error parsing response object", e.getMessage());
		}
	}
}
