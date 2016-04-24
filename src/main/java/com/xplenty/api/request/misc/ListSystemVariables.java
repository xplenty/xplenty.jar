package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.request.AbstractListRequest;

import java.util.Map;

/**
 * List public system variables
 * Author: Xardas
 * Date: 08.01.16
 * Time: 20:37
 */
public class ListSystemVariables extends AbstractListRequest<Map<String, String>> {

    public ListSystemVariables() {
        super(null, false);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.SystemVariables.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SystemVariables.name;
    }

    @Override
    public Map<String, String> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
