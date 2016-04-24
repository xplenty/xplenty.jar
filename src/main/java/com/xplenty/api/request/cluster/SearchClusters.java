package com.xplenty.api.request.cluster;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.request.AbstractSearchRequest;

import java.util.List;
import java.util.Properties;

/**
 * Search clusters based on the specified query
 *
 * Author: Xardas
 * Date: 24.04.16
 * Time: 12:52
 */
public class SearchClusters extends AbstractSearchRequest<List<Cluster>> {

    public SearchClusters(Properties parameters) {
        super(parameters);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SearchClusters.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SearchClusters.name;
    }

    @Override
    public List<Cluster> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Cluster>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
