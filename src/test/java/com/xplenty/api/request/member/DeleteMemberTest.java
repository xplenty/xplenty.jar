/**
 * 
 */
package com.xplenty.api.request.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Member;
import com.xplenty.api.model.MemberTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xardas
 *
 */
public class DeleteMemberTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        DeleteMember cc = new DeleteMember(666);
		assertEquals(Xplenty.Resource.DeleteMember.format(String.valueOf(666)), cc.getEndpoint());
		assertEquals(Xplenty.Resource.DeleteMember.name, cc.getName());
		assertEquals(Http.Method.DELETE, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Member c = MemberTest.createMockMember(now);

		String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        DeleteMember cc = new DeleteMember(666);
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
        assertEquals("test", c.getName());
        assertEquals("xardazz@github.com", c.getEmail());
        assertEquals("https://testapi.xplenty.com/settings/members/666", c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/avatar", c.getAvatarUrl());
        assertTrue(c.getConfirmed());
        assertEquals("Colorado", c.getLocation());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals("https://testapi.xplenty.com/api/members/666", c.getUrl());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getConfirmedAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Member c = MemberTest.createMockMember(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

		try {
            DeleteMember cc = new DeleteMember(666);
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.DeleteMember.name + ": error parsing response object", e.getMessage());
		}

	}
}
