/**
 * 
 */
package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.JobStatus;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Job;
import com.xplenty.api.model.JobOutput;
import com.xplenty.api.model.JobTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Yuriy Kovalek
 *
 */
public class JobInfoTest extends TestCase {

	@Test
	public void testIntegrity() {
		JobInfo ji = new JobInfo(111L, false, false);
		
		assertEquals(Xplenty.Resource.Job.format(Long.toString(111)), ji.getEndpoint());
		assertEquals(Xplenty.Resource.Job.name, ji.getName());
		assertEquals(Http.Method.GET, ji.getHttpMethod());
		assertEquals(Http.MediaType.JSON, ji.getResponseType());
		assertFalse(ji.hasBody());
		assertNull(ji.getBody());

        ji = new JobInfo(111L, true, false);
        assertEquals(Xplenty.Resource.Job.format(Long.toString(111)) + "?include=cluster", ji.getEndpoint());
        ji = new JobInfo(111L, false, true);
        assertEquals(Xplenty.Resource.Job.format(Long.toString(111)) + "?include=package", ji.getEndpoint());
        ji = new JobInfo(111L, true, true);
        assertEquals(Xplenty.Resource.Job.format(Long.toString(111)) + "?include=cluster,package", ji.getEndpoint());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
		JobInfo ji = new JobInfo(111L, false, false);
		Job j = JobTest.createMockJob(now);
		String json = new ObjectMapper().writeValueAsString(j);
		
		j = ji.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
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
		assertTrue(Math.abs(now.getTime() - j.getCreatedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - j.getStartedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - j.getCompletedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - j.getFailedAt().getTime()) < 1000);

        assertNotNull(j.getOutputs());
        JobOutput jo = j.getOutputs().get(0);
        assertEquals(1, jo.getId().longValue());
        assertEquals(2, jo.getRecordsCount().longValue());
        assertTrue(Math.abs(now.getTime() - jo.getCreatedAt().getTime()) < 1000);
        assertTrue(Math.abs(now.getTime() - jo.getCreatedAt().getTime()) < 1000);
        assertEquals("mock job output", jo.getName());
        assertEquals("json", jo.getPreviewType());
        assertEquals("https://xplenty.com/api/jobs/7/outputs/777/preview", jo.getPreviewUrl());
        assertNotNull(jo.getComponent());
        assertEquals("dest1", jo.getComponent().getName());
        assertEquals("cloud_storage", jo.getComponent().getType());
        assertNotNull(jo.getComponent().getFields());
        assertEquals("ddd", jo.getComponent().getFields().get(0));
        assertEquals("bbb", jo.getComponent().getFields().get(1));
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
		JobInfo ji = new JobInfo(111L, false, false);
		Job j = JobTest.createMockJob(now);
		String json = new ObjectMapper().writeValueAsString(j).replace("running", "success");
		
		try {
			j = ji.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Job.name + ": error parsing response object", e.getMessage());
		}
	}
}
