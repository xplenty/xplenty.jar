/**
 * 
 */
package com.xplenty.api.request;

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
public class JobInfo implements Request<Job> {
	
	private long jobId;
	
	public JobInfo(long jobId) {
		
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Job.name;
	}

	@Override
	public Method getHttpMethod() {
		return Http.Method.GET;
	}

	@Override
	public MediaType getResponseType() {
		return Http.MediaType.JSON;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.Job.format(Long.toString(jobId));
	}

	@Override
	public Job getResponse(ClientResponse response) {
		try {
			return response.getEntity(Job.class);
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

}
