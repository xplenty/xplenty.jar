/**
 * 
 */
package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Job;
import com.xplenty.api.util.Http;
import com.xplenty.api.util.Http.Method;

/**
 * @author Yuriy Kovalek
 *
 */
public class RunJob extends AbstractManipulationRequest<Job> {
    protected RunJob(Job entity) {
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
