package com.xplenty.api.request.job;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.JobOutputPreview;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * The calls returns up to 100 lines raw preview of a job output.
 * Author: Xardas
 * Date: 11.01.16
 * Time: 18:24
 */
public class JobPreviewOutput extends AbstractInfoRequest<JobOutputPreview> {
    private final long jobId;

    public JobPreviewOutput(long jobId, long outputId) {
        super(outputId);
        this.jobId = jobId;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.JobPreviewOutput.format(String.valueOf(jobId), String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.JobPreviewOutput.name;
    }
}
