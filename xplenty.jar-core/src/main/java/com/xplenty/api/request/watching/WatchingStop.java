package com.xplenty.api.request.watching;

import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.RequestFailedException;
import com.xplenty.api.request.Request;
import com.xplenty.api.util.Http;

import static com.xplenty.api.util.Http.Method.DELETE;
import static com.xplenty.api.Xplenty.*;

public class WatchingStop implements Request<Boolean> {
    private Long _subjectId;
    private SubjectType _kind;

    public WatchingStop(SubjectType kind, Long subjectId ) {
        _kind = kind;
        _subjectId = subjectId;
    }

    @Override
    public String getName() {
        return Resource.ClusterWatcher.name;
    }

    @Override
    public Http.Method getHttpMethod() { return DELETE; }

    @Override
    public Http.MediaType getResponseType()  { return Http.MediaType.JSON; }

    @Override
    public String getEndpoint() {
        switch (_kind){
            case CLUSTER: return Xplenty.Resource.ClusterWatcher.format(Long.toString(_subjectId));
            case JOB    : return Xplenty.Resource.JobWatcher.format(Long.toString(_subjectId));
            default     : throw new AssertionError("Unreachable code : default case");
        }
    }

    @Override
    public boolean hasBody() { return false;   }

    @Override
    public Object getBody() {  return null;    }

    @Override
    public Boolean getResponse(ClientResponse response) {
        int code = response.getStatus();
        if (code==204)
            return true;
        else
            throw new RequestFailedException("204 expected, but something went wrong", code, "");
    }
}
