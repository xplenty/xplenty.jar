/**
 * 
 */
package com.xplenty.api.request.xpackage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.MemberTest;
import com.xplenty.api.model.Package;
import com.xplenty.api.model.PackageTest;
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
public class ListPackagesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Date now = new Date();
        final Properties params = new Properties();
        ListPackages cc = new ListPackages(params);
		assertEquals(Xplenty.Resource.Packages.format(String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.Packages.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        params.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        params.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        params.put(ListPackages.PARAMETER_FLOW_TYPE, Xplenty.PackageFlowType.dataflow);
        cc = new ListPackages(params);
        assertEquals(Xplenty.Resource.Packages.value + "?" +
                ListPackages.PARAMETER_FLOW_TYPE + "=dataflow&" +
                AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Package c = PackageTest.createMockPackage(now);
        List<Package> packList = new ArrayList<>();
        packList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(MemberTest.createMockMemberForCreation());
        assertNotNull(json);

		json = JsonMapperFactory.getInstance().writeValueAsString(packList);

        ListPackages cc = new ListPackages(new Properties());
        packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(packList);
        assertTrue(packList.size() > 0);

        c = packList.get(0);
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
        assertEquals("TestPack", c.getName());
        assertEquals("TestPack Description", c.getDescription());
        assertEquals(Xplenty.PackageFlowType.workflow, c.getFlowType());
        assertEquals("https://testapi.xplenty.com/api/package/666", c.getUrl());
        assertEquals("https://testapi.xplenty.com/package/666", c.getHtmlUrl());
        assertEquals(Xplenty.PackageStatus.active, c.getStatus());
        assertEquals(111, c.getOwnerId().longValue());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

        Map<String, String> packVars = c.getVariables();
        assertNotNull(packVars);
        assertEquals("val1", packVars.get("var_1"));
        assertEquals("super$$$\"complex'val\n\t", packVars.get("var_2"));
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Package c = PackageTest.createMockPackage(now);
        List<Package> packList = new ArrayList<>();
        packList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(packList).replace("{", "[");

		try {
            ListPackages cc = new ListPackages(new Properties());
            packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Packages.name + ": error parsing response object", e.getMessage());
		}

	}
}
