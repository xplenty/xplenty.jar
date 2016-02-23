/**
 * 
 */
package com.xplenty.api.request.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Account;
import com.xplenty.api.model.AccountTest;
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
public class CreateAccountTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        CreateAccount cc = new CreateAccount("test", "gcloud::europe-west", "superunique");
		assertEquals(Xplenty.Resource.CreateAccount.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.CreateAccount.name, cc.getName());
		assertEquals(Http.Method.POST, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertTrue(cc.hasBody());
		assertNotNull(cc.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Account c = AccountTest.createMockAccount(now);

		String json = JsonMapperFactory.getInstance().writeValueAsString(AccountTest.createMockAccountForCreation());
        assertNotNull(json);

		json = JsonMapperFactory.getInstance().writeValueAsString(c);

        CreateAccount cc = new CreateAccount("test", "gcloud::europe-west", "superunique");
		c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.CREATED.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);

		assertEquals(new Long(666), c.getId());
        assertEquals("test", c.getName());
        assertEquals("superunique", c.getAccountId());
        assertEquals("gcloud::europe-west", c.getRegion());
        assertEquals("https://secure.gravatar.com", c.getAvatarUrl());
        assertEquals("xardazz@github.com", c.getBillingEmail());
        assertEquals("gravatar@gravatar.com", c.getGravatarEmail());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals(123, c.getConnectionsCount().intValue());
        assertEquals(1234, c.getJobsCount().intValue());
        assertEquals(12345, c.getMembersCount().intValue());
        assertEquals(123456, c.getPackagesCount().intValue());
        assertEquals(1234567, c.getRunningJobsCount().intValue());
        assertEquals(12345678, c.getSchedulesCount().intValue());
        assertEquals(123456789, c.getHooksCount().intValue());
        assertEquals("ssh-rsa AAAAAAA....AAAAAA Xplenty/superunique", c.getPublicKey());
        assertEquals(111, c.getOwnerId().longValue());
        assertEquals("Private Drive", c.getLocation());
        assertEquals("u_666", c.getUname());
        assertEquals("https://testapi.xplenty.com/accounts/superunique", c.getUrl());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized

	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Account c = AccountTest.createMockAccount(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

		try {
            CreateAccount cc = new CreateAccount("test", "gcloud::europe-west", "superunique");
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.CREATED.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.CreateAccount.name + ": error parsing response object", e.getMessage());
		}

	}
}
