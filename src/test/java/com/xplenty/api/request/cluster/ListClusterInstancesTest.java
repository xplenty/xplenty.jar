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
import com.xplenty.api.model.ClusterInstance;
import com.xplenty.api.model.ClusterTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xardazz
 *
 */
public class ListClusterInstancesTest extends TestCase {
	@Test
	public void testIntegrity() {
		ListClusterInstances lc = new ListClusterInstances(666);
		
		assertEquals(Xplenty.Resource.ClusterInstances.format("666"), lc.getEndpoint());
		assertEquals(Xplenty.Resource.ClusterInstances.name, lc.getName());
		assertEquals(Http.Method.GET, lc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, lc.getResponseType());
		assertFalse(lc.hasBody());
		assertNull(lc.getBody());
	}

	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        ListClusterInstances lc = new ListClusterInstances(666);
        List<ClusterInstance> list = new ArrayList<>();
        list.add(ClusterTest.createMockClusterInstance());
		
		String json = new ObjectMapper().writeValueAsString(list);
		list = lc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		assertNotNull(list);
		assertFalse(list.isEmpty());

        ClusterInstance c = list.get(0);
        assertNotNull(c);
        assertEquals("i-4d1b39a7", c.getInstanceId());
        assertEquals("ip-10-124-29-23.ec2.internal", c.getPrivateDns());
        assertEquals("ec2-55-27-210-201.compute-1.amazonaws.com", c.getPublicDns());
        assertEquals("sometype", c.getInstanceType());
        assertEquals("eu-west1", c.getZone());
        assertEquals("https://testapi.xplenty.com/api/clusters/5/instances/i-4d1b39a7", c.getUrl());
        assertEquals(Xplenty.ClusterInstanceStatus.available, c.getStatus());
        assertTrue(c.getMaster());
        assertFalse(c.getSpot());
        assertFalse(c.getVpc());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        ListClusterInstances lc = new ListClusterInstances(666);
		List<ClusterInstance> list = new ArrayList<>();
		list.add(ClusterTest.createMockClusterInstance());
		
		String json = new ObjectMapper().writeValueAsString(list).replace("[", "");
		try {
			list = lc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.ClusterInstances.name + ": error parsing response object", e.getMessage());
		}
	}
}
