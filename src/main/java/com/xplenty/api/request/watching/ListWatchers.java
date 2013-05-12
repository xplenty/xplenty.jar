package com.xplenty.api.request.watching;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Watcher;
import com.xplenty.api.request.Request;
import com.xplenty.api.util.Http;
import static com.xplenty.api.util.Http.Method.*;
import static com.xplenty.api.Xplenty.*;

import java.util.List;

public class ListWatchers implements Request<List<Watcher>> {
    private SubjectType _kind = null;
    private Long _subjectId = null;

    public ListWatchers(SubjectType kind, Long clusterId){
        _kind = kind;
        _subjectId = clusterId;
    }

    @Override
    public String getName() {
        switch (_kind) {
            case JOB: return Xplenty.Resource.JobWatcher.name;
            case CLUSTER: return Resource.ClusterWatcher.name;
            default: throw new AssertionError("Unreachable case statement");
        }
    }

    @Override
    public Http.Method getHttpMethod() { return GET; }

    @Override
     public Http.MediaType getResponseType() { return Http.MediaType.JSON; }

    @Override
    public String getEndpoint() {
        switch (_kind){
            case CLUSTER: return Resource.ClusterWatcher.format(Long.toString(_subjectId));
            case JOB    : return Resource.JobWatcher.format(Long.toString(_subjectId));
            default     : throw new AssertionError("Unreachable code : default case");
        }
    }

    @Override
    public boolean hasBody(){ return false; }

    @Override
    public Object getBody() { return null; }

    @Override
    public List<Watcher> getResponse(ClientResponse response) {
        String json = response.getEntity(String.class);
        try {
            return new ObjectMapper().readValue(json, new TypeReference<List<Watcher>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
