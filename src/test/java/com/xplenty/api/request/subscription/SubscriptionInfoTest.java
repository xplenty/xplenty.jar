package com.xplenty.api.request.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Subscription;
import com.xplenty.api.model.SubscriptionTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:33
 */
public class SubscriptionInfoTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        SubscriptionInfo cc = new SubscriptionInfo();
        assertEquals(Xplenty.Resource.Subscription.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.Subscription.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Subscription c = SubscriptionTest.createMockSubscription(now);

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        SubscriptionInfo cc = new SubscriptionInfo();
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);
        assertEquals(666, c.getTrialPeriodDays().intValue());
        assertEquals("free---777", c.getPlanId());
        assertEquals("https://testapi.xplenty.com/api/subscription", c.getUrl());
        assertTrue(c.isTrial());
        assertTrue(Math.abs(now.getTime() - c.getTrialStart().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getTrialEnd().getTime()) < 1000); //fractions of second are not serialized

    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Subscription c = SubscriptionTest.createMockSubscription(now);
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

        try {
            SubscriptionInfo cc = new SubscriptionInfo();
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.Subscription.name + ": error parsing response object", e.getMessage());
        }

    }
}
