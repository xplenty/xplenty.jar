/**
 * 
 */
package com.xplenty.api.request;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.xplenty.api.model.ClusterPlan;
import com.xplenty.api.model.ClusterPlanTest;
import com.xplenty.api.util.Http;

/**
 * @author Yuriy Kovalek
 *
 */
public class ListClusterPlansTest extends TestCase {

	@Test
	public void testIntegrity() {
		ListClusterPlans lcp = new ListClusterPlans();
		
		assertEquals(Xplenty.Resource.ClusterPlans.value, lcp.getEndpoint());
		assertEquals(Xplenty.Resource.ClusterPlans.name, lcp.getName());
		assertEquals(Http.Method.GET, lcp.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lcp.getResponseType());
		assertFalse(lcp.hasBody());
		assertNull(lcp.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListClusterPlans lcp = new ListClusterPlans();
		List<ClusterPlan> cp = new ArrayList<ClusterPlan>();
		cp.add(ClusterPlanTest.createMockClusterPlan());
		String json = new ObjectMapper().writeValueAsString(cp);
		
		cp = lcp.getResponse(new ClientResponse(Status.OK.getStatusCode(),
								new InBoundHeaders(), 
								new ByteArrayInputStream(json.getBytes("UTF-8")),
								Client.create().getMessageBodyWorkers()));
		
		assertNotNull(cp);
		assertFalse(cp.isEmpty());
		assertEquals(new Long(1), cp.get(0).getId());
		assertEquals("test plan", cp.get(0).getName());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListClusterPlans lcp = new ListClusterPlans();
		List<ClusterPlan> cp = new ArrayList<ClusterPlan>();
		cp.add(ClusterPlanTest.createMockClusterPlan());
		String json = new ObjectMapper().writeValueAsString(cp).replace("1", "string");
		
		try {
			cp = lcp.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.ClusterPlans.name + ": error parsing response object", e.getMessage());
		}
	}
}
