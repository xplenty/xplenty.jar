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
import com.xplenty.api.Xplenty.JobStatus;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobTest;
import com.xplenty.api.util.Http;

/**
 * @author Yuriy Kovalek
 *
 */
public class JobInfoTest extends TestCase {

	@Test
	public void testIntegrity() {
		JobInfo ji = new JobInfo(111);
		
		assertEquals(Xplenty.Resource.Job.format(Long.toString(111)), ji.getEndpoint());
		assertEquals(Xplenty.Resource.Job.name, ji.getName());
		assertEquals(Http.Method.GET, ji.getHttpMethod());
		assertEquals(Http.MediaType.JSON, ji.getResponseType());
		assertFalse(ji.hasBody());
		assertNull(ji.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
		JobInfo ji = new JobInfo(111);
		Job j = JobTest.createMockJob(now);
		String json = new ObjectMapper().writeValueAsString(j);
		
		j = ji.getResponse(new ClientResponse(Status.OK.getStatusCode(),
												new InBoundHeaders(), 
												new ByteArrayInputStream(json.getBytes("UTF-8")),
												Client.create().getMessageBodyWorkers()));
		
		assertNotNull(j);
		assertEquals(new Long(7), j.getId());
		assertEquals(new Long(1), j.getClusterId());
		assertEquals(new Integer(3), j.getOutputsCount());
		assertEquals(new Long(2), j.getOwnerId());
		assertEquals(new Long(8), j.getPackageId());
		assertEquals(new Long(333), j.getRuntimeInSeconds());
		assertEquals(50.0, j.getProgress());
		assertEquals("", j.getErrors());
		assertEquals(JobStatus.running, j.getStatus());
		assertEquals("https://www.xplenty.com/api/" + Xplenty.Resource.Job.format(Long.toString(7)), j.getUrl());
		assertNotNull(j.getVariables());
		assertTrue(Math.abs(now.getTime() - j.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
		assertTrue(Math.abs(now.getTime() - j.getUpdatedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - j.getStartedAt().getTime()) < 1000);
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
		JobInfo ji = new JobInfo(111);
		Job j = JobTest.createMockJob(now);
		String json = new ObjectMapper().writeValueAsString(j).replace("running", "success");
		
		try {
			j = ji.getResponse(new ClientResponse(Status.OK.getStatusCode(),
													new InBoundHeaders(), 
													new ByteArrayInputStream(json.getBytes("UTF-8")),
													Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Job.name + ": error parsing response object", e.getMessage());
		}
	}
}
