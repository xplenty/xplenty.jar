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
import com.xplenty.api.model.SearchQueryTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author xardas
 *
 */
public class SearchPackagesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        Date now = new Date();
        SearchPackages cc = new SearchPackages(SearchQueryTest.createMockSearchQuery(now).toProperties());
		assertEquals(Xplenty.Resource.SearchPackages.format(String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.SearchPackages.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertTrue(cc.hasBody());
		assertNotNull(cc.getBody());

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

        SearchPackages cc = new SearchPackages(SearchQueryTest.createMockSearchQuery(now).toProperties());
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
            SearchPackages cc = new SearchPackages(SearchQueryTest.createMockSearchQuery(now).toProperties());
            packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.SearchPackages.name + ": error parsing response object", e.getMessage());
		}

	}
}
