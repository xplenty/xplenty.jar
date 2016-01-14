package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Stack;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * This call returns information for the list of supported Stacks.
 * Author: Xardas
 * Date: 08.01.16
 * Time: 20:37
 */
public class ListStacks extends AbstractListRequest<List<Stack>> {

    public ListStacks() {
        super(null, false);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Stacks.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Stacks.name;
    }

    @Override
    public List<Stack> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Stack>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
