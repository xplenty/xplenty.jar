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
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * @author xardas
 *
 */
public class UpdateScheduleTest extends TestCase {

    private final static String TEST_JSON = "{\"id\":666,\"name\":\"testSchedule\",\"description\":\"some neat description\",\"execution_count\":0,\"interval_amount\":100,\"interval_unit\":\"hours\",\"last_run_at\":null,\"last_run_status\":null,\"next_run_at\":\"2015-12-23T00:04:32Z\",\"owner_id\":352,\"start_at\":\"2015-12-18T20:04:32Z\",\"status\":\"enabled\",\"created_at\":\"2015-12-20T18:26:39Z\",\"updated_at\":\"2015-12-20T18:26:39Z\",\"reuse_cluster_strategy\":\"any\",\"overlap\":true,\"url\":\"https://xplenty.com/api/schedules/copy\",\"task\":{\"nodes\":1,\"terminate_on_idle\":true,\"time_to_idle\":1000,\"packages\":[{\"package_id\":1234,\"variables\":{\"testvar\":\"testval\"}}]}}";
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testIntegrity() {


        UpdateSchedule cs = new UpdateSchedule(ScheduleTest.createMockSchedule(new Date()));
		assertEquals(Xplenty.Resource.UpdateSchedule.format(String.valueOf(666)), cs.getEndpoint());
		assertEquals(Xplenty.Resource.UpdateSchedule.name, cs.getName());
		assertEquals(Http.Method.PUT, cs.getHttpMethod());
		assertEquals(Http.MediaType.JSON, cs.getResponseType());
		assertTrue(cs.hasBody());
		assertNotNull(cs.getBody());
	}
	
	@Test
	public void testValidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date(); 
		Schedule c = ScheduleTest.createMockSchedule(now);
		
		String json = new ObjectMapper().writeValueAsString(c);
        UpdateSchedule cc = new UpdateSchedule(c);

        c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                TEST_JSON,
                Status.OK.getStatusCode(),
                new HashMap<String, String>()));
		
		assertNotNull(c);
		assertEquals(new Long(666), c.getId());
		assertEquals("testSchedule", c.getName());
		assertEquals("some neat description", c.getDescription());
		assertEquals(Xplenty.ScheduleStatus.enabled, c.getStatus());
	}
	
	@Test
	public void testInvalidResponseHandling() throws JsonProcessingException, UnsupportedEncodingException {
		Date now = new Date();
        Schedule c = ScheduleTest.createMockSchedule(now);
		
		String json = new ObjectMapper().writeValueAsString(c).replace("{", "one");
        UpdateSchedule cc = new UpdateSchedule(c);
		try {
			c = cc.getResponse(Response.forContentType(Http.MediaType.JSON,
                    json,
                    Status.OK.getStatusCode(),
                    new HashMap<String, String>()));
			assertTrue(false);
		} catch (XplentyAPIException e) {
			assertEquals(Xplenty.Resource.UpdateSchedule.name + ": error parsing response object", e.getMessage());
		}

	}
}
