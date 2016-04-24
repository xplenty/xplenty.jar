/**
 * 
 */
package com.xplenty.api.request.cluster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.ClusterTest;
import com.xplenty.api.model.SearchQueryTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author Yuriy Kovalek
 *
 */
public class SearchClustersTest extends TestCase {
	@Test
	public void testIntegrity() {
        SearchClusters lc = new SearchClusters(SearchQueryTest.createMockSearchQuery(new Date()).toProperties());
		
		assertEquals(Xplenty.Resource.SearchClusters.value, lc.getEndpoint());
		assertEquals(Xplenty.Resource.SearchClusters.name, lc.getName());
		assertEquals(Http.Method.GET, lc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lc.getResponseType());
		assertTrue(lc.hasBody());
		assertNotNull(lc.getBody());
	}

	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		SearchClusters lc = new SearchClusters(SearchQueryTest.createMockSearchQuery(new Date()).toProperties());
		List<Cluster> list = new ArrayList<Cluster>();
		list.add(ClusterTest.createMockCluster(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list);
		list = lc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        SearchClusters lc = new SearchClusters(SearchQueryTest.createMockSearchQuery(new Date()).toProperties());
		List<Cluster> list = new ArrayList<Cluster>();
		list.add(ClusterTest.createMockCluster(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("[", "");
		try {
			list = lc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.SearchClusters.name + ": error parsing response object", e.getMessage());
		}
	}
}
