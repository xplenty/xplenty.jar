/**
 * 
 */
package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Job;

/**
 * @author Yuriy Kovalek
 *
 */
public class StopJob extends AbstractDeleteRequest<Job> {

    protected StopJob(Long entityId) {
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
