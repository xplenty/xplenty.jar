/**
 * 
 */
package com.xplenty.api.request;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

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
import com.xplenty.api.request.CreateCluster;
import com.xplenty.api.util.Http;

import junit.framework.TestCase;

/**
 * @author Yuriy Kovalek
 *
 */
public class CreateClusterTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
		CreateCluster cc = new CreateCluster(new Cluster().named("my cluster")
															.onPlan(1)
															.withDescription("desc"));
		assertEquals(Xplenty.Resource.CreateCluster.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.CreateCluster.name, cc.getName());
		assertEquals(Http.Method.POST, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertTrue(cc.hasBody());
		assertNotNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Cluster c = ClusterTest.createMockCluster(now);
		
		String json = new ObjectMapper().writeValueAsString(c);
		CreateCluster cc = new CreateCluster(c);
		c = cc.getResponse(new ClientResponse(Status.CREATED.getStatusCode(),
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
		CreateCluster cc = new CreateCluster(c);
		try {
			c = cc.getResponse(new ClientResponse(Status.CREATED.getStatusCode(),
													new InBoundHeaders(), 
													new ByteArrayInputStream(json.getBytes("UTF-8")),
													Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.CreateCluster.name + ": error parsing response object", e.getMessage());
		}
		
		c = ClusterTest.createMockCluster(now);
		
		json = new ObjectMapper().writeValueAsString(c).replace("available", "ready");
		cc = new CreateCluster(c);
		try {
			c = cc.getResponse(new ClientResponse(Status.CREATED.getStatusCode(),
													new InBoundHeaders(), 
													new ByteArrayInputStream(json.getBytes("UTF-8")),
													Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.CreateCluster.name + ": error parsing response object", e.getMessage());
		}
	}
}
