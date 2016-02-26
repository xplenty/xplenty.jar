package com.xplenty.api.request.connection;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Connection;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List connections that are accessible by the authenticated user.
 * Optionally, you can supply the input parameters to filter the connection list so that it contains only connections
 * with specific types and to determine the order by which the list will be sorted.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 20:44
 */
public class ListConnections extends AbstractListRequest<List<Connection>> {
    public static String PARAMETER_TYPE = "type";

    public ListConnections(Properties parameters) {
        super(parameters, true);
        validateParameters(parameters);
    }

    private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_TYPE)
                && !(params.get(PARAMETER_TYPE) instanceof Xplenty.ConnectionType)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of ConnectionType values", PARAMETER_TYPE));
        }
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Connections.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Connections.name;
    }

    @Override
    public List<Connection> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Connection>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
