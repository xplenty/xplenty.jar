package com.xplenty.api.request.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.ConnectionType;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List all connection types that are available with related groups.
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:16
 */
public class ListConnectionTypes extends AbstractListRequest<List<ConnectionType>> {

    public ListConnectionTypes() {
        super(new Properties(), false);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.ConnectionTypes.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.ConnectionTypes.name;
    }

    @Override
    public List<ConnectionType> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<ConnectionType>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
