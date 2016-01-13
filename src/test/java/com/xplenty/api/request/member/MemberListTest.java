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
import com.xplenty.api.request.AbstractListRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xardas
 *
 */
public class MemberListTest extends TestCase {
    protected final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {
        Date now = new Date();

        final Properties props = new Properties();
        ListMembers cc = new ListMembers(props);
		assertEquals(Xplenty.Resource.Members.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.Members.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
        assertNull(cc.getBody());

        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        props.put(AbstractListRequest.PARAMETER_SINCE, now);
        cc = new ListMembers(props);
        assertEquals(Xplenty.Resource.Members.value + "?" + AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc&" + AbstractListRequest.PARAMETER_SINCE +
                "=" +dateFormat.format(now), cc.getEndpoint());
    }

    @Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
        List<Member> memberList = new ArrayList<>();
		Member c = MemberTest.createMockMember(now);
        memberList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(memberList);

        ListMembers cc = new ListMembers(new Properties());
        memberList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(memberList);
        assertTrue(memberList.size() > 0);
        c = memberList.get(0);
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
        List<Member> memberList = new ArrayList<>();
        Member c = MemberTest.createMockMember(now);
        memberList.add(c);

        String json = JsonMapperFactory.getInstance().writeValueAsString(memberList).replace("{", "[");

		try {
            ListMembers cc = new ListMembers(new Properties());
            memberList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Members.name + ": error parsing response object", e.getMessage());
		}

	}
}
