package com.xplenty.api.request.subscription;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.JsonMapperFactory;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Plan;
import com.xplenty.api.model.PlanTest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:33
 */
public class ListPlansTest extends TestCase {
    @Before
    public void setUp() {

    }

    @Test
    public void testIntegrity() {

        ListPlans cc = new ListPlans();
        assertEquals(Xplenty.Resource.Plans.value, cc.getEndpoint());
        assertEquals(Xplenty.Resource.Plans.name, cc.getName());
        assertEquals(Http.Method.GET, cc.getHttpMethod());
        assertEquals(Http.MediaType.JSON, cc.getResponseType());
        assertFalse(cc.hasBody());
        assertNull(cc.getBody());
    }

    @Test
    public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Plan c = PlanTest.createMockPlan(now);
        List<Plan> planList = new ArrayList<>();
        planList.add(c);

        String json = JsonMapperFactory.getInstance().writeValueAsString(planList);

        ListPlans cc = new ListPlans();
        planList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                ClientResponse.Status.OK.getStatusCode(),
                new HashMap<String, String>()));

        assertNotNull(planList);
        assertTrue(planList.size() > 0);

        c = planList.get(0);
        assertNotNull(c);

        assertEquals("Expert", c.getName());
        assertEquals("Expert", c.getDescription());
        assertEquals("USD", c.getPriceCurrency());
        assertEquals("USD", c.getClusterNodePriceCurrency());
        assertEquals(Xplenty.PriceUnit.month, c.getPriceUnit());
        assertEquals(Xplenty.PriceUnit.hour, c.getClusterNodePriceUnit());
        assertEquals(199900, c.getPriceCents().longValue());
        assertEquals(71, c.getClusterNodePriceCents().longValue());
        assertEquals(0, c.getClusterNodeHoursIncluded().intValue());
        assertEquals(-1, c.getClusterNodeHoursLimit().intValue());
        assertEquals(32, c.getClusterNodesLimit().intValue());
        assertEquals(0, c.getClusterSizeLimit().intValue());
        assertEquals(4096, c.getClustersLimit().intValue());
        assertEquals(1, c.getSandboxClustersLimit().intValue());
        assertEquals(0, c.getSandboxNodeHoursIncluded().intValue());
        assertEquals(-1, c.getSandboxNodeHoursLimit().intValue());
        assertEquals(4096, c.getMembersLimit().intValue());
        assertEquals(7, c.getPosition().intValue());
        assertTrue(Math.abs(now.getTime() - c.getCreatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertTrue(Math.abs(now.getTime() - c.getUpdatedAt().getTime()) < 1000); //fractions of second are not serialized
        assertEquals("expert--7", c.getId());
    }

    @Test
    public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        Date now = new Date();
        Plan c = PlanTest.createMockPlan(now);
        List<Plan> planList = new ArrayList<>();
        planList.add(c);
        String json = JsonMapperFactory.getInstance().writeValueAsString(planList).replace("{", "[");

        try {
            ListPlans cc = new ListPlans();
            planList = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    ClientResponse.Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
            assertTrue(false);
        } catch (XplentyAPIException e) {
            assertEquals(Xplenty.Resource.Plans.name + ": error parsing response object", e.getMessage());
        }

    }
}
