/**
 * 
 */
package com.xplenty.api.request.public_key;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.model.PublicKeyTest;
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
public class ListPublicKeysTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties props = new Properties();
        ListPublicKeys cc = new ListPublicKeys(props);
		assertEquals(Xplenty.Resource.PublicKeys.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.PublicKeys.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        cc = new ListPublicKeys(props);
        assertEquals(Xplenty.Resource.PublicKeys.value + "?" + AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());

	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<PublicKey> publicKeys = new ArrayList<>();
        publicKeys.add(PublicKeyTest.createMockPublicKey(now));

		String json = JsonMapperFactory.getInstance().writeValueAsString(publicKeys);

        ListPublicKeys cc = new ListPublicKeys(new Properties());
        publicKeys = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(publicKeys);
        assertTrue(publicKeys.size() > 0);

        PublicKey c = publicKeys.get(0);
		assertEquals(new Long(666), c.getId());
		assertEquals("xardazz@github.com", c.getComment());
		assertEquals("ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff", c.getFingerprint());
        assertEquals("test", c.getName());
        assertEquals("https://testapi.xplenty.com/user/keys/666", c.getUrl());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        List<PublicKey> publicKeys = new ArrayList<>();
        publicKeys.add(PublicKeyTest.createMockPublicKey(now));
        String json = JsonMapperFactory.getInstance().writeValueAsString(publicKeys).replace("{", "[");

		try {
            ListPublicKeys cc = new ListPublicKeys(new Properties());
            publicKeys = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.PublicKeys.name + ": error parsing response object", e.getMessage());
		}

	}
}
