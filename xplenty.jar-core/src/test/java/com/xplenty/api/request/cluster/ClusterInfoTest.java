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
import com.xplenty.api.model.ClusterBootstrapAction;
import com.xplenty.api.model.ClusterTest;
import com.xplenty.api.model.Creator;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterInfoTest extends TestCase {
	
	ClusterInfo ci;
	
	@Before
	public void setUp() {
		ci = new ClusterInfo(1L);
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
		
		Cluster c = ci.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
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
		assertTrue(Math.abs(now.getTime() - c.getIdleSince().getTime()) < 1000);
		assertEquals("https://www.xplenty.com/api/" + Xplenty.Resource.Cluster.format(Long.toString(3)), c.getUrl());
        assertTrue(c.getAllowFallback());
        assertEquals("eu-west1", c.getZone());
        assertEquals("https://www.xplenty.com/clusters/3", c.getHtmlUrl());
        assertEquals("sometype", c.getMasterInstanceType());
        assertEquals("sometype1", c.getSlaveInstanceType());
        assertEquals("eu-west1-amazon:1", c.getRegion());
        assertEquals("month-10", c.getPlanId());
        assertEquals("pinky-everest", c.getStack());
        assertEquals(0.3, c.getMasterSpotPercentage());
        assertEquals(42.42, c.getMasterSpotPrice());
        assertEquals(0.4, c.getSlaveSpotPercentage());
        assertEquals(12.12, c.getSlaveSpotPrice());

        List<ClusterBootstrapAction> cbas = c.getBootstrapActions();
        assertNotNull(cbas);
        assertTrue(cbas.size() > 0);
        ClusterBootstrapAction act = cbas.get(0);
        assertNotNull(act);
        assertEquals("http://xplenty.s3.amazonaws.com/bootstrap-actions/script_path", act.getScriptPath());
        assertNotNull(act.getArgs());
        List<String> argList = act.getArgs();
        assertTrue(argList.size() > 0);
        assertEquals("arg1", argList.get(0));
        assertEquals("some more", argList.get(1));

        Creator cr = c.getCreator();
        assertNotNull(cr);
        assertEquals("xardazz", cr.getDisplayName());
        assertEquals("https://www.xplenty.com/user/3", cr.getHtmlUrl());
        assertEquals("https://www.xplenty.com/api/user/3", cr.getUrl());
        assertEquals("User", cr.getType());
        assertEquals(666, cr.getId().longValue());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Cluster c = ClusterTest.createMockCluster(now);
		
		String json = new ObjectMapper().writeValueAsString(c).replace("1", "one");
		try {
			c = ci.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.CREATED.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Cluster.name + ": error parsing response object", e.getMessage());
		}
		
		c = ClusterTest.createMockCluster(now);
		
		json = new ObjectMapper().writeValueAsString(c).replace("available", "ready");
		try {
			c = ci.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.CREATED.getStatusCode(),
                    new HashMap<String, String>()));
			fail();
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Cluster.name + ": error parsing response object", e.getMessage());
		}
	}	
}
