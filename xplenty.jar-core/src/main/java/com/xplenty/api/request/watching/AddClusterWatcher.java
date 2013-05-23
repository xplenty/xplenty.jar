package com.xplenty.api.request.watching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.ClusterWatchingLogEntry;
import com.xplenty.api.request.Request;
import com.xplenty.api.util.Http;

import static com.xplenty.api.util.Http.Method.POST;
import static com.xplenty.api.Xplenty.*;

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
    public ClusterWatchingLogEntry getResponse(ClientResponse response) {
        String json = response.getEntity(String.class);
        try {
            return new ObjectMapper().readValue(json, new TypeReference<ClusterWatchingLogEntry>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
