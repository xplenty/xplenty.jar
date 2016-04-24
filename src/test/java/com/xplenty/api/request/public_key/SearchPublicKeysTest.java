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
public class SearchPublicKeysTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        Date now = new Date();
        SearchPublicKeys cc = new SearchPublicKeys(SearchQueryTest.createMockSearchQuery(now).toProperties());
		assertEquals(Xplenty.Resource.SearchPublicKeys.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.SearchPublicKeys.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertTrue(cc.hasBody());
		assertNotNull(cc.getBody());


	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<PublicKey> publicKeys = new ArrayList<>();
        publicKeys.add(PublicKeyTest.createMockPublicKey(now));

		String json = JsonMapperFactory.getInstance().writeValueAsString(publicKeys);

        SearchPublicKeys cc = new SearchPublicKeys(SearchQueryTest.createMockSearchQuery(now).toProperties());
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
            SearchPublicKeys cc = new SearchPublicKeys(SearchQueryTest.createMockSearchQuery(now).toProperties());
            publicKeys = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.SearchPublicKeys.name + ": error parsing response object", e.getMessage());
		}

	}
}
