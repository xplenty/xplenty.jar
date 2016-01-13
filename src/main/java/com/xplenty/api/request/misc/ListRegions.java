package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Region;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * This call returns information for the list of regions supported by Xplenty.
 * You can also select regions for particular Brand. You can use this information to verify the regions in which you can create a cluster.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 17:51
 */
public class ListRegions extends AbstractListRequest<List<Region>> {
    /**
     * 	The Brand's numeric identifier for which you want to list regions.
     */
    public static final String PARAMETER_BRAND_ID = "brand_id";

    public ListRegions(Properties parameters) {
        super(parameters, false);
        validateParameters(parameters);
    }

    private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_BRAND_ID)
                && !(params.get(PARAMETER_BRAND_ID) instanceof Number)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be a number!", PARAMETER_BRAND_ID));
        }
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
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
