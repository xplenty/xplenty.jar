package com.xplenty.api.request.public_key;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.request.AbstractSearchRequest;

import java.util.List;
import java.util.Properties;

/**
 * Search public keys based on the specified query
 *
 * Author: Xardas
 * Date: 24.04.16
 * Time: 20:43
 */
public class SearchPublicKeys extends AbstractSearchRequest<List<PublicKey>> {
    public SearchPublicKeys(Properties parameters) {
        super(parameters);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }


    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SearchPublicKeys.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SearchPublicKeys.name;
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
