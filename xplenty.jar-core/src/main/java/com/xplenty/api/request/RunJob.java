/**
 * 
 */
package com.xplenty.api.request;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Job;
import com.xplenty.api.util.Http;
import com.xplenty.api.util.Http.MediaType;
import com.xplenty.api.util.Http.Method;

/**
 * @author Yuriy Kovalek
 *
 */
public class RunJob implements Request<Job> {
	private final Job job;
	
	public RunJob(Job job) {
		this.job = job;
	}
	
	@Override
	public String getName() {
		return Xplenty.Resource.RunJob.name;
	}

	@Override
	public Method getHttpMethod() {
		return Http.Method.POST;
	}

	@Override
	public MediaType getResponseType() {
		return Http.MediaType.JSON;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.RunJob.value;
	}

	@Override
	public boolean hasBody() {
		return true;
	}

	@Override
	public Object getBody() {
		Map<String, Object> j = new HashMap<String, Object>();
		j.put("job", job);
		return j;
	}

	@Override
	public Job getResponse(ClientResponse response) {
		String json = response.getEntity(String.class);		
		try {
			return new ObjectMapper().readValue(json, new TypeReference<Job>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}
}
