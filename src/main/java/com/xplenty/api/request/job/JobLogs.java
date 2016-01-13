package com.xplenty.api.request.job;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.JobLog;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Log summary for the job.
 * Author: Xardas
 * Date: 08.01.16
 * Time: 19:55
 */
public class JobLogs extends AbstractInfoRequest<JobLog> {

    public JobLogs(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.JobLog.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.JobLog.name;
    }
}
