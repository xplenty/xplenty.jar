/**
 * 
 */
package com.xplenty.api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.core.header.InBoundHeaders;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.model.ScheduleTest;
import com.xplenty.api.util.Http;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *  Author: Xardas
 */
public class ListSchedulesTest extends TestCase {
	@Test
	public void testIntegrity() {
		ListSchedules ls = new ListSchedules(new Properties());
		
		assertEquals(Xplenty.Resource.Schedules.value, ls.getEndpoint());
		assertEquals(Xplenty.Resource.Schedules.name, ls.getName());
		assertEquals(Http.Method.GET, ls.getHttpMethod());
		assertEquals(Http.MediaType.JSON, ls.getResponseType());
		assertFalse(ls.hasBody());
		assertNull(ls.getBody());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
        ListSchedules ls = new ListSchedules(new Properties());
        List<Schedule> list = new ArrayList<Schedule>();
        list.add(ScheduleTest.createMockSchedule(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("{", "");
		try {
			list = ls.getResponse(new ClientResponse(Status.OK.getStatusCode(),
									new InBoundHeaders(), 
									new ByteArrayInputStream(json.getBytes("UTF-8")),
									Client.create().getMessageBodyWorkers()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.Schedules.name + ": error parsing response object", e.getMessage());
		}
	}

    @Test
    public void testPaging() throws Exception {
        final Properties params = new Properties();
        params.put(AbstractParametrizedRequest.PARAMETER_LIMIT, 1);
        params.put(AbstractParametrizedRequest.PARAMETER_OFFSET, 2);

        ListSchedules ls = new ListSchedules(params);
        assertEquals(ls.getEndpoint(), String.format("%s?limit=1&offset=2", ls.getEndpointRoot()));


    }

    @Test
    public void testInvalidPaging() throws Exception {
        final Properties params = new Properties();
        params.put(AbstractParametrizedRequest.PARAMETER_LIMIT, -1);
        params.put(AbstractParametrizedRequest.PARAMETER_OFFSET, -2);

        try {
            ListSchedules ls = new ListSchedules(params);
        } catch (XplentyAPIException ex) {
            assertEquals(String.format("'limit' parameter should be less or equal to %s and greater than 0", Xplenty.MAX_LIMIT), ex.getMessage());
        }

        try {
            params.put(AbstractParametrizedRequest.PARAMETER_LIMIT, 101);
            ListSchedules ls = new ListSchedules(params);
        } catch (XplentyAPIException ex) {
            assertEquals(String.format("'limit' parameter should be less or equal to %s and greater than 0", Xplenty.MAX_LIMIT), ex.getMessage());
        }

        try {
            params.put(AbstractParametrizedRequest.PARAMETER_LIMIT, 1);
            ListSchedules ls = new ListSchedules(params);
        } catch (XplentyAPIException ex) {
            assertEquals("'offset' parameter should be greater than 0", ex.getMessage());
        }
    }
}
