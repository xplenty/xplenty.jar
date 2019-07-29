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
public class ListAccountsTest extends TestCase {
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {

        final Properties props = new Properties();
        ListAccounts cc = new ListAccounts(props);
		assertEquals(Xplenty.Resource.Accounts.value, cc.getEndpoint());
		assertEquals(Xplenty.Resource.Accounts.name, cc.getName());
		assertEquals(Http.Method.GET, cc.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cc.getResponseType());
		assertFalse(cc.hasBody());
		assertNull(cc.getBody());

        props.put(AbstractListRequest.PARAMETER_SORT, Xplenty.Sort.name);
        props.put(AbstractListRequest.PARAMETER_DIRECTION, Xplenty.SortDirection.descending);
        cc = new ListAccounts(props);
        assertEquals(Xplenty.Resource.Accounts.value + "?" + AbstractListRequest.PARAMETER_SORT + "=name&" +
                AbstractListRequest.PARAMETER_DIRECTION + "=desc", cc.getEndpoint());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();

        List<Account> accountList = new ArrayList<>();
		Account c = AccountTest.createMockAccount(now);
        accountList.add(c);

		String json = JsonMapperFactory.getInstance().writeValueAsString(accountList);

        ListAccounts cc = new ListAccounts(new Properties());
        accountList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(accountList);
        assertTrue(accountList.size() > 0);

        c = accountList.get(0);
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
        List<Account> accountList = new ArrayList<>();
        Account c = AccountTest.createMockAccount(now);
        accountList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(accountList).replace("{", "[");

		try {
            ListAccounts cc = new ListAccounts(new Properties());
            accountList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Accounts.name + ": error parsing response object", e.getMessage());
		}

	}
}
