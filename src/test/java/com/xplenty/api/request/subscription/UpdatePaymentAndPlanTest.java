package com.xplenty.api.request.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.CreditCardInfo;
import com.xplenty.api.model.CreditCardInfoTest;
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
public class UpdatePaymentAndPlanTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        UpdatePaymentAndPlan cc = new UpdatePaymentAndPlan("abcd", "free---666");
        assertEquals(Xplenty.Resource.UpdatePaymentMethodAndPlan.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.UpdatePaymentMethodAndPlan.name, cc.getName());
        assertEquals(Http.Method.PUT, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertTrue(cc.hasBody());
        assertNotNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        CreditCardInfo c = CreditCardInfoTest.createMockCreditCardInfo();

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        UpdatePaymentAndPlan cc = new UpdatePaymentAndPlan("abcd", "free---666");
        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(c);
        assertEquals(9876, c.getCardLast4().intValue());
        assertEquals("xxxx-xxxx-xxxx-9876", c.getCardNumber());
        assertEquals("MasterCard", c.getCardType());
        assertEquals("06/66", c.getExpirationDate());
        assertEquals("https://testapi.xplenty.com/api/payment_method", c.getUrl());
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        CreditCardInfo c = CreditCardInfoTest.createMockCreditCardInfo();
        String json = JsonMapperFactory.getInstance().writeValueAsString(c).replace("{", "[");

        try {
            UpdatePaymentAndPlan cc = new UpdatePaymentAndPlan("abcd", "free---666");
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.UpdatePaymentMethodAndPlan.name + ": error parsing response object", e.getMessage());
        }

    }
}
