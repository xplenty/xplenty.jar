package com.xplenty.api.request.watching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.JobWatchingLogEntry;
import com.xplenty.api.request.Request;
import com.xplenty.api.util.Http;

import static com.xplenty.api.util.Http.Method.POST;

public class AddJobWatcher implements Request<JobWatchingLogEntry> {
    Long _jobId;

    public AddJobWatcher(Long subjectId) {
        _jobId = subjectId;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.JobWatcher.name;
    }

    @Override
    public Http.Method getHttpMethod() { return POST; }

    @Override
    public Http.MediaType getResponseType()  { return Http.MediaType.JSON; }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.JobWatcher.format(Long.toString( _jobId) );
    }

    @Override
    public boolean hasBody() { return true; }

    @Override
    public Object getBody() { return null; }

    @Override
    public JobWatchingLogEntry getResponse(ClientResponse response) {
        String json = response.getEntity(String.class);
        try {
            return new ObjectMapper().readValue(json, new TypeReference<JobWatchingLogEntry>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
