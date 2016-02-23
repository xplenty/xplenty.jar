package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List web hooks
 * Author: Xardas
 * Date: 05.01.16
 * Time: 17:38
 */
public class ListHooks extends AbstractListRequest<List<Hook>> {


    public ListHooks(Properties parameters) {
        super(parameters, true);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Hooks.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Hooks.name;
    }

    @Override
    public List<Hook> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Hook>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
