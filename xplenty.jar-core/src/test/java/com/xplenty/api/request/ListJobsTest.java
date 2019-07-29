/**
 * 
 */
package com.xplenty.api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Yuriy Kovalek
 *
 */
public class ListJobsTest extends TestCase {
	@Test
	public void testIntegrity() {
		ListJobs lj = new ListJobs(new Properties());
		
		assertEquals(Xplenty.Resource.Jobs.value, lj.getEndpoint());
		assertEquals(Xplenty.Resource.Jobs.name, lj.getName());
		assertEquals(Http.Method.GET, lj.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lj.getResponseType());
		assertFalse(lj.hasBody());
		assertNull(lj.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListJobs lj = new ListJobs(new Properties());
		List<Job> list = new ArrayList<Job>();
		list.add(JobTest.createMockJob(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list);
		list = lj.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListJobs lj = new ListJobs(new Properties());
		List<Job> list = new ArrayList<Job>();
		list.add(JobTest.createMockJob(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("[", "");
		try {
			list = lj.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Jobs.name + ": error parsing response object", e.getMessage());
		}
	}
}
