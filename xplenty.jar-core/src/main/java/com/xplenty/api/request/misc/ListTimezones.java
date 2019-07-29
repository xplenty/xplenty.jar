package com.xplenty.api.request.misc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Timezone;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * This call returns list of supported Time Zones.
 * Author: Xardas
 * Date: 08.01.16
 * Time: 20:37
 */
public class ListTimezones extends AbstractListRequest<List<Timezone>> {

    public ListTimezones() {
        super(null, false);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Timezones.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Timezones.name;
    }

    @Override
    public List<Timezone> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Timezone>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
