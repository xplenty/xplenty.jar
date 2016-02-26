package com.xplenty.api.request.public_key;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List public keys
 * Author: Xardas
 * Date: 06.01.16
 * Time: 21:07
 */
public class ListPublicKeys extends AbstractListRequest<List<PublicKey>> {

    public ListPublicKeys(Properties parameters) {
        super(parameters, true);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.PublicKeys.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PublicKeys.name;
    }

    @Override
    public List<PublicKey> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<PublicKey>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
