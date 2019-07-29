package com.xplenty.api.request.webhook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List web hooks
 * Author: Xardas
 * Date: 05.01.16
 * Time: 17:38
 */
public class ListWebHooks extends AbstractListRequest<List<WebHook>> {


    public ListWebHooks(Properties parameters) {
        super(parameters, true);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.WebHooks.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.WebHooks.name;
    }

    @Override
    public List<WebHook> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<WebHook>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
