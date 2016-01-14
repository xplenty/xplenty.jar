/**
 * 
 */
package com.xplenty.api.request.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.ConnectionType;
import com.xplenty.api.model.ConnectionTypeTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author xardas
 *
 */
public class ListConnectionTypesTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        ListConnectionTypes cc = new ListConnectionTypes();
		assertEquals(Xplenty.Resource.ConnectionTypes.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.ConnectionTypes.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
        ConnectionType c = ConnectionTypeTest.createMockConnectionType();

        List<ConnectionType> conTypeList = new ArrayList<>();
        conTypeList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(conTypeList);

        ListConnectionTypes cc = new ListConnectionTypes();
        conTypeList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(conTypeList);
        assertTrue(conTypeList.size() > 0);

        c = conTypeList.get(0);

		assertNotNull(c);
		assertEquals("mysql", c.getType());
        assertEquals("MySQL", c.getName());
        assertEquals("The world's greatest open source database", c.getDescription());
        assertEquals("https://assets.xplenty.com/assets/vendor/mysql.png", c.getIconUrl());
        assertEquals("Relational Databases", c.getGroups().get(0).getGroupName());
        assertEquals("relational", c.getGroups().get(0).getGroupType());

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        ConnectionType c = ConnectionTypeTest.createMockConnectionType();

        List<ConnectionType> conTypeList = new ArrayList<>();
        conTypeList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(conTypeList).replace("{", "[");

		try {
            ListConnectionTypes cc = new ListConnectionTypes();
            conTypeList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.ConnectionTypes.name + ": error parsing response object", e.getMessage());
		}

	}
}
