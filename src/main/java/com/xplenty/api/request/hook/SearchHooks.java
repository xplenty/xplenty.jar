package com.xplenty.api.request.hook;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractSearchRequest;

import java.util.List;
import java.util.Properties;

/**
 * Search hooks based on the specified query
 *
 * Author: Xardas
 * Date: 24.04.16
 * Time: 13:50
 */
public class SearchHooks extends AbstractSearchRequest<List<Hook>> {
    public SearchHooks(Properties parameters) {
        super(parameters);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SearchHooks.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SearchHooks.name;
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
