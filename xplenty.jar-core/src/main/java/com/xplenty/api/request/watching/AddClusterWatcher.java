package com.xplenty.api.request.watching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.ClusterWatchingLogEntry;
import com.xplenty.api.request.Request;

import static com.xplenty.api.Xplenty.Resource;
import static com.xplenty.api.http.Http.Method.POST;

public class AddClusterWatcher implements Request<ClusterWatchingLogEntry> {
    private Long _clusterId;

    public AddClusterWatcher(Long subjectId) {
        _clusterId = subjectId;
    }

    @Override
    public String getName() {
        return Resource.ClusterWatcher.name;
    }

    @Override
    public Http.Method getHttpMethod() {return POST; }

    @Override
    public Http.MediaType getResponseType()  { return Http.MediaType.JSON; }

    @Override
    public String getEndpoint() { return Resource.ClusterWatcher.format(Long.toString(_clusterId)); }

    @Override
    public boolean hasBody() { return true; }

    @Override
    public Object getBody() { return null; }

    @Override
    public ClusterWatchingLogEntry getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<ClusterWatchingLogEntry>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
