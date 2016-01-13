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
import com.xplenty.api.model.PackageTemplate;
import com.xplenty.api.model.PackageTemplateAuthor;
import com.xplenty.api.model.PackageTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xardas
 *
 */
public class ListPackageTemplatesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        ListPackageTemplates cc = new ListPackageTemplates();
		assertEquals(Xplenty.Resource.PackageTemplates.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.PackageTemplates.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        PackageTemplate c = PackageTest.createMockPackageTemplate();
        List<PackageTemplate> packList = new ArrayList<>();
        packList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(MemberTest.createMockMemberForCreation());
        assertNotNull(json);

		json = JsonMapperFactory.getInstance().writeValueAsString(packList);

        ListPackageTemplates cc = new ListPackageTemplates();
        packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(packList);
        assertTrue(packList.size() > 0);

        c = packList.get(0);
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
        assertEquals("test template", c.getName());
        assertEquals("really good template", c.getDescription());
        assertEquals(1, c.getPosition().longValue());

        PackageTemplateAuthor pta = c.getAuthor();
        assertNotNull(pta);
        assertEquals(333, pta.getId().longValue());
        assertEquals("best template author", pta.getName());
        assertEquals("https://testapi.xplenty.com/api/user/333", pta.getAvatarUrl());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        PackageTemplate c = PackageTest.createMockPackageTemplate();
        List<PackageTemplate> packList = new ArrayList<>();
        packList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(packList).replace("{", "[");

		try {
            ListPackageTemplates cc = new ListPackageTemplates();
            packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.PackageTemplates.name + ": error parsing response object", e.getMessage());
		}

	}
}
