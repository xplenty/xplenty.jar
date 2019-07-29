package com.xplenty.api.request.webhook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.HookEvent;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * List supported Hook events
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:12
 */
public class ListHookEvents extends AbstractListRequest<List<HookEvent>> {

    public ListHookEvents() {
        super(null, false);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpointRoot());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.HookEvents.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.HookEvents.name;
    }

    @Override
    public List<HookEvent> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<HookEvent>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
