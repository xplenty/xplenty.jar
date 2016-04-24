package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.AbstractSearchRequest;

import java.util.List;
import java.util.Properties;

/**
 * Author: Xardas
 * Date: 24.04.16
 * Time: 14:14
 */
public class SearchJobs extends AbstractSearchRequest<List<Job>> {
    public SearchJobs(Properties parameters) {
        super(parameters);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SearchJobs.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SearchJobs.name;
    }

    @Override
    public List<Job> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Job>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
