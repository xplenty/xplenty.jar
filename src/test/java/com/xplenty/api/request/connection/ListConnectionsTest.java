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
import com.xplenty.api.model.Connection;
import com.xplenty.api.model.ConnectionTest;
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
public class ListConnectionsTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties props = new Properties();
        ListConnections cc = new ListConnections(props);
		assertEquals(Xplenty.Resource.Connections.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.Connections.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        props.put(ListConnections.PARAMETER_TYPE, Xplenty.ConnectionType.mysql);
        cc = new ListConnections(props);
        assertEquals(Xplenty.Resource.Connections.value + "?" + AbstractListRequest.PARAMETER_SORT + "=name&"
                + ListConnections.PARAMETER_TYPE + "=mysql&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());

	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Connection c = ConnectionTest.createMockConnection(now);

        List<Connection> connectionList = new ArrayList<>();
        connectionList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(connectionList);

        ListConnections cc = new ListConnections(new Properties());
        connectionList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(connectionList);
        assertTrue(connectionList.size() > 0);

        c = connectionList.get(0);

		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
        assertEquals("MYSQL_CONNECTION_666", c.getUniqueId());
		assertEquals(Xplenty.ConnectionType.mysql, c.getType());
        assertEquals("test", c.getName());
        assertEquals("https://testapi.xplenty.com/api/connections/mysql/666", c.getUrl());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Connection c = ConnectionTest.createMockConnection(now);

        List<Connection> connectionList = new ArrayList<>();
        connectionList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(connectionList).replace("{", "[");

		try {
            ListConnections cc = new ListConnections(new Properties());
            connectionList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Connections.name + ": error parsing response object", e.getMessage());
		}

	}
}
