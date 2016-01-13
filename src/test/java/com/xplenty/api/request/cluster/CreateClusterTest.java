/**
 * 
 */
package com.xplenty.api.request.cluster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterTest;
import com.xplenty.api.request.cluster.CreateCluster;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

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
															.withNodes(2)
															.withDescription("desc")
															.ofType(ClusterType.production)
															.withTerminateOnIdle(true)
															.withTimeToIdle(3600L));
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
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.CREATED.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(3), c.getId());
		assertEquals("my cluster", c.getName());
		assertEquals("description", c.getDescription());
		assertEquals(ClusterStatus.available, c.getStatus());
		assertEquals(new Long(1), c.getOwnerId());
		assertEquals(new Integer(2), c.getNodes());
		assertEquals(ClusterType.production, c.getType());
		assertEquals(new Long(0), c.getRunningJobsCount());
		assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
		assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - c.getAvailableSince().getTime()) < 1000);
		assertTrue(Math.abs(now.getTime() - c.getTerminatedAt().getTime()) < 1000);
		assertEquals("https://www.xplenty.com/api/" + Xplenty.Resource.Cluster.format(Long.toString(3)), c.getUrl());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Cluster c = ClusterTest.createMockCluster(now);
		
		String json = new ObjectMapper().writeValueAsString(c).replace("1", "one");
		CreateCluster cc = new CreateCluster(c);
		try {
			c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.CREATED.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.CreateCluster.name + ": error parsing response object", e.getMessage());
		}
		
		c = ClusterTest.createMockCluster(now);
		
		json = new ObjectMapper().writeValueAsString(c).replace("available", "ready");
		cc = new CreateCluster(c);
		try {
			c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.CREATED.getStatusCode(),
                    new HashMap<String, String>()));
			fail();
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.CreateCluster.name + ": error parsing response object", e.getMessage());
		}
	}
}
