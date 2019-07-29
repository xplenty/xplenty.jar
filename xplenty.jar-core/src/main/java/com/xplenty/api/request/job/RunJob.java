/**
 * 
 */
package com.xplenty.api.request.job;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Job;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Http.Method;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * @author Yuriy Kovalek
 *
 */
public class RunJob extends AbstractManipulationRequest<Job> {
    public RunJob(Job entity) {
        super(entity);
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
	public String getEndpoint() {
		return Xplenty.Resource.RunJob.value;
	}

    @Override
    protected String getPackKey() {
        return "job";
    }
}
