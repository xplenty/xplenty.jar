package com.xplenty.api.request.xpackage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Package;
import com.xplenty.api.request.AbstractSearchRequest;

import java.util.List;
import java.util.Properties;

/**
 * Search packages based on the specified query
 *
 * Author: Xardas
 * Date: 24.04.16
 * Time: 20:56
 */
public class SearchPackages extends AbstractSearchRequest<List<Package>> {
    public SearchPackages(Properties parameters) {
        super(parameters);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SearchPackages.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SearchPackages.name;
    }

    @Override
    public List<Package> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Package>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
