/**
 * 
 */
package com.xplenty.api.request;

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
public class StopJob implements Request<Job> {
	private final long jobId;
	
	public StopJob(long jobId) {
		this.jobId = jobId;
	}

	@Override
	public String getName() {
		return Xplenty.Resource.StopJob.name;
	}

	@Override
	public Method getHttpMethod() {
		return Http.Method.DELETE;
	}

	@Override
	public MediaType getResponseType() {
		return Http.MediaType.JSON;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.StopJob.format(Long.toString(jobId));
	}

	@Override
	public boolean hasBody() {
		return false;
	}

	@Override
	public Job getBody() {
		return null;
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
