/**
 * 
 */
package com.xplenty.api.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.model.ScheduleTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;

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
	public void testValidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
        ListSchedules ls = new ListSchedules(new Properties());
		List<Schedule> list = new ArrayList<Schedule>();
		list.add(ScheduleTest.createMockSchedule(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list);
		list = ls.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testInvalidResponcehandling() throws JsonProcessingException, UnsupportedEncodingException {
        ListSchedules ls = new ListSchedules(new Properties());
        List<Schedule> list = new ArrayList<Schedule>();
        list.add(ScheduleTest.createMockSchedule(new Date()));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("{", "");
		try {
			list = ls.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
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
