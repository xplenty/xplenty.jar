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
public class PaymentMethodInfoTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        PaymentMehodInfo cc = new PaymentMehodInfo();
        assertEquals(Xplenty.Resource.PaymentMethod.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.PaymentMethod.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        CreditCardInfo c = CreditCardInfoTest.createMockCreditCardInfo();

        String json = JsonMapperFactory.getInstance().writeValueAsString(c);

        PaymentMehodInfo cc = new PaymentMehodInfo();
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
            PaymentMehodInfo cc = new PaymentMehodInfo();
            c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.PaymentMethod.name + ": error parsing response object", e.getMessage());
        }

    }
}
