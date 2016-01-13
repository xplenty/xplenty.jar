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
public class ListPackageValidationsTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties params = new Properties();
        ListPackageValidations cc = new ListPackageValidations(666, params);
		assertEquals(Xplenty.Resource.PackageValidations.format(String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.PackageValidations.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        params.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        params.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        params.put(ListPackageValidations.PARAMETER_STATUS, Xplenty.PackageValidationStatus.running);
        cc = new ListPackageValidations(666, params);
        assertEquals(Xplenty.Resource.PackageValidations.format(String.valueOf(666)) + "?" +
                ListPackageValidations.PARAMETER_STATUS + "=running&" +
                AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		PackageValidation c = PackageTest.createMockPackageValidation(now);
        List<PackageValidation> packList = new ArrayList<>();
        packList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(MemberTest.createMockMemberForCreation());
        assertNotNull(json);

		json = JsonMapperFactory.getInstance().writeValueAsString(packList);

        ListPackageValidations cc = new ListPackageValidations(666, new Properties());
        packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(packList);
        assertTrue(packList.size() > 0);

        c = packList.get(0);
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
        List<PackageValidation> packList = new ArrayList<>();
        packList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(packList).replace("{", "[");

		try {
            ListPackageValidations cc = new ListPackageValidations(666, new Properties());
            packList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.PackageValidations.name + ": error parsing response object", e.getMessage());
		}

	}
}
