/**
 * 
 */
package com.xplenty.api.request;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.InBoundHeaders;
import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterTest;
import com.xplenty.api.util.Http;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterInfoTest extends TestCase {
	
	ClusterInfo ci;
	
	@Before
	public void setUp() {
		ci = new ClusterInfo(1);
	}
	
	@Test
	public void testIntegrity() {		
		assertEquals(Xplenty.Resource.Cluster.name, ci.getName());
		assertEquals(Xplenty.Resource.Cluster.format("1"), ci.getEndpoint());
		assertEquals(Http.Method.GET, ci.getHttpMethod());
		assertEquals(Http.MediaType.JSON, ci.getResponseType());
		assertFalse(ci.hasBody());
		assertNull(ci.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws UnsupportedEncodingException, ParseException, JsonProcessingException {
		Date now = new Date();
		String json = new ObjectMapper().writeValueAsString(ClusterTest.createMockCluster(now));
		
		Cluster c = ci.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
		
		assertNotNull(c);
		assertEquals(new Long(3), c.getId());
		assertEquals("my cluster", c.getName());
		assertEquals("description", c.getDescription());
		assertEquals(ClusterStatus.available, c.getStatus());
		assertEquals(new Long(1), c.getOwnerId());
		assertEquals(new Long(2), c.getPlanId());
		assertEquals(new Long(0), c.getRunningJobsCount());
		assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
		assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000);
		assertEquals("https://www.xplenty.com/api/" + Xplenty.Resource.Cluster.format(Long.toString(3)), c.getUrl());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Cluster c = ClusterTest.createMockCluster(now);
		
		String json = new ObjectMapper().writeValueAsString(c).replace("1", "one");
		try {
			c = ci.getResponse(new ClientResponse(Status.CREATED.getStatusCode(),
													new InBoundHeaders(), 
													new ByteArrayInputStream(json.getBytes("UTF-8")),
													Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Cluster.name + ": error parsing response object", e.getMessage());
		}
		
		c = ClusterTest.createMockCluster(now);
		
		json = new ObjectMapper().writeValueAsString(c).replace("available", "ready");
		try {
			c = ci.getResponse(new ClientResponse(Status.CREATED.getStatusCode(),
													new InBoundHeaders(), 
													new ByteArrayInputStream(json.getBytes("UTF-8")),
													Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Cluster.name + ": error parsing response object", e.getMessage());
		}
	}	
}
