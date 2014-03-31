/**
 * 
 */
package com.xplenty.api.request;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.InBoundHeaders;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterTest;
import com.xplenty.api.util.Http;

import junit.framework.TestCase;

/**
 * @author Yuriy Kovalek
 *
 */
public class ListClustersTest extends TestCase {
	@Test
	public void testIntegrity() {
		ListClusters lc = new ListClusters(new Properties());
		
		assertEquals(Xplenty.Resource.Clusters.value, lc.getEndpoint());
		assertEquals(Xplenty.Resource.Clusters.name, lc.getName());
		assertEquals(Http.Method.GET, lc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lc.getResponseType());
		assertFalse(lc.hasBody());
		assertNull(lc.getBody());
	}

	@Test
	public void testValidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListClusters lc = new ListClusters(new Properties());
		List<Cluster> list = new ArrayList<Cluster>();
		list.add(ClusterTest.createMockCluster(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list);
		list = lc.getResponse(new ClientResponse(Status.OK.getStatusCode(),
								new InBoundHeaders(), 
								new ByteArrayInputStream(json.getBytes("UTF-8")),
								Client.create().getMessageBodyWorkers()));
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		ListClusters lc = new ListClusters(new Properties());
		List<Cluster> list = new ArrayList<Cluster>();
		list.add(ClusterTest.createMockCluster(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("[", "");
		try {
			list = lc.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Clusters.name + ": error parsing response object", e.getMessage());
		}
	}
}
