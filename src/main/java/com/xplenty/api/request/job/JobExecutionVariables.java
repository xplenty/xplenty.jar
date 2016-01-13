package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.request.AbstractListRequest;

import java.util.Map;
import java.util.Properties;

/**
 * List all job variables that were used during job runtime.
 * Author: Xardas
 * Date: 08.01.16
 * Time: 18:57
 */
public class JobExecutionVariables extends AbstractListRequest<Map<String, String>> {
    private final long jobId;

    public JobExecutionVariables(long jobId) {
        super(new Properties(), false);
        this.jobId = jobId;
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.JobExecVars.format(String.valueOf(jobId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.JobExecVars.name;
    }

    @Override
    public Map<String, String> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
