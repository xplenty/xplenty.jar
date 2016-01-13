/**
 * 
 */
package com.xplenty.api.request.job;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * @author Yuriy Kovalek
 *
 */
public class StopJob extends AbstractDeleteRequest<Job> {

    public StopJob(Long entityId) {
        super(entityId);
    }

    @Override
	public String getName() {
		return Xplenty.Resource.StopJob.name;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.StopJob.format(Long.toString(entityId));
	}
}
