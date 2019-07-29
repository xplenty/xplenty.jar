package com.xplenty.api.request.watching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.JobWatchingLogEntry;
import com.xplenty.api.request.AbstractRequest;

import static com.xplenty.api.http.Http.Method.POST;

public class AddJobWatcher extends AbstractRequest<JobWatchingLogEntry> {
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
    public JobWatchingLogEntry getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<JobWatchingLogEntry>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
