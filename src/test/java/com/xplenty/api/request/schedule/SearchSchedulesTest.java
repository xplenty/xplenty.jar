/**
 * 
 */
package com.xplenty.api.request.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.model.ScheduleTest;
import com.xplenty.api.model.SearchQueryTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *  Author: Xardas
 */
public class SearchSchedulesTest extends TestCase {
	@Test
	public void testIntegrity() {
        SearchSchedules ls = new SearchSchedules(SearchQueryTest.createMockSearchQuery(new Date()).toProperties());
		
		assertEquals(Xplenty.Resource.SearchSchedules.value, ls.getEndpoint());
		assertEquals(Xplenty.Resource.SearchSchedules.name, ls.getName());
		assertEquals(Http.Method.GET, ls.getHttpMethod());
		assertEquals(Http.MediaType.JSON, ls.getResponseType());
		assertTrue(ls.hasBody());
		assertNotNull(ls.getBody());
	}
	
	@Test
	public void testValidResponceHandling() throws JsonProcessingException, UnsupportedEncodingException {
        final Date now = new Date();
        SearchSchedules ls = new SearchSchedules(SearchQueryTest.createMockSearchQuery(now).toProperties());
		List<Schedule> list = new ArrayList<Schedule>();
        final Schedule mockSchedule = ScheduleTest.createMockSchedule(now);
        mockSchedule.getTask().getPackages().clear();
        list.add(mockSchedule);
		
		String json = new ObjectMapper().writeValueAsString(list);
        json = json.replace("\"packages\":{}", "\"packages\":[]"); // sent and recieved json for schedules slightly differ.
		list = ls.getResponse(Response.forContentType(Http.MediaType.JSON,
                json,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}
	
	@Test
	public void testInvalidResponceHandling() throws JsonProcessingException, UnsupportedEncodingException {
        final Date now = new Date();
        SearchSchedules ls = new SearchSchedules(SearchQueryTest.createMockSearchQuery(now).toProperties());
        List<Schedule> list = new ArrayList<Schedule>();
        list.add(ScheduleTest.createMockSchedule(now));
		
		String json = new ObjectMapper().writeValueAsString(list).replace("{", "");
		try {
			list = ls.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.SearchSchedules.name + ": error parsing response object", e.getMessage());
		}
	}

}
