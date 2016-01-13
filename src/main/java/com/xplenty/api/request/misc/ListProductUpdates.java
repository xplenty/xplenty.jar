package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.ProductUpdate;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * This call returns list of latest product announcements.
 * Author: Xardas
 * Date: 11.01.16
 * Time: 17:48
 */
public class ListProductUpdates extends AbstractListRequest<List<ProductUpdate>> {

    public ListProductUpdates() {
        super(null, false);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.ProductUpdates.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.ProductUpdates.name;
    }

    @Override
    public List<ProductUpdate> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<ProductUpdate>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
