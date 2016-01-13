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
import com.xplenty.api.model.PackageTest;
import com.xplenty.api.model.PackageValidation;
import com.xplenty.api.model.PackageValidationError;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author xardas
 *
 */
public class PackageValidationInfoTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties params = new Properties();
        PackageValidationInfo cc = new PackageValidationInfo(666, 777);
		assertEquals(Xplenty.Resource.PackageValidation.format(String.valueOf(777), String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.PackageValidation.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		PackageValidation c = PackageTest.createMockPackageValidation(now);

		String json = JsonMapperFactory.getInstance().writeValueAsString(MemberTest.createMockMemberForCreation());
        assertNotNull(json);

		json = JsonMapperFactory.getInstance().writeValueAsString(c);

        PackageValidationInfo cc = new PackageValidationInfo(666, 777);
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
        assertEquals("Something bad happened", c.getStatusMessage());
        assertEquals("https://testapi.xplenty.com/api/packages/777/validations/666", c.getUrl());
        assertEquals(Xplenty.PackageValidationStatus.failed, c.getStatus());
        assertEquals(222, c.getOwnerId().longValue());
        assertEquals(111, c.getAccountId().longValue());
        assertEquals(777, c.getPackageId().longValue());
        assertEquals(1234, c.getRuntime().longValue());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

        List<PackageValidationError> errors  = c.getErrors();
        assertNotNull(errors);
        assertEquals("12", errors.get(0).getComponentId());
        assertEquals("couldn't obtain value for var_1", errors.get(0).getMessage());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        PackageValidation c = PackageTest.createMockPackageValidation(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

		try {
            PackageValidationInfo cc = new PackageValidationInfo(666, 777);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.PackageValidation.name + ": error parsing response object", e.getMessage());
		}

	}
}
