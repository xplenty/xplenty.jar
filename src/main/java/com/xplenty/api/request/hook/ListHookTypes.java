package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.AvailableHookType;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * List all hook types that are available with related groups.
 *
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:12
 */
public class ListHookTypes extends AbstractListRequest<List<AvailableHookType>> {

    public ListHookTypes() {
        super(null, false);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.HookTypes.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.HookTypes.name;
    }

    @Override
    public List<AvailableHookType> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<AvailableHookType>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
