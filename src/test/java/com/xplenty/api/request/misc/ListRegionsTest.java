/**
 * 
 */
package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Region;
import com.xplenty.api.model.RegionTest;
import com.xplenty.api.request.AbstractListRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author xardas
 *
 */
public class ListRegionsTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties props = new Properties();
        ListRegions cc = new ListRegions(props);
		assertEquals(Xplenty.Resource.Regions.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.Regions.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        props.put(ListRegions.PARAMETER_BRAND_ID, 11);
        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        cc = new ListRegions(props);
        assertEquals(Xplenty.Resource.Regions.value + "?" +
                ListRegions.PARAMETER_BRAND_ID + "=11&" +
                AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());

	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<Region> regionList = new ArrayList<>();
		Region c = RegionTest.createMockRegion();
        regionList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(regionList);

        ListRegions cc = new ListRegions(new Properties());
        regionList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(regionList);
        assertTrue(regionList.size() > 0);

        c = regionList.get(0);

        assertEquals("gcloud", c.getGroupName());
        assertEquals("West Europe Google Cloud", c.getName());
        assertEquals("gcloud::europe-west", c.getId());

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<Region> regionList = new ArrayList<>();
        Region c = RegionTest.createMockRegion();
        regionList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(regionList).replace("{", "[");

		try {
            ListRegions cc = new ListRegions(new Properties());
            regionList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Regions.name + ": error parsing response object", e.getMessage());
		}

	}
}
