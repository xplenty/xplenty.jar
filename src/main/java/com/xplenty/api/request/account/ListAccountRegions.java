package com.xplenty.api.request.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Region;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * This call returns information for the list of regions that are available for your account.
 * You can use this information to verify the regions in which you can create a cluster
 * Author: Xardas
 * Date: 07.01.16
 * Time: 17:51
 */
public class ListAccountRegions extends AbstractListRequest<List<Region>> {

    public ListAccountRegions(Properties parameters) {
        super(parameters, false);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Regions.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Regions.name;
    }

    @Override
    public List<Region> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Region>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
