package com.xplenty.api.request.subscription;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Plan;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * List plans that are available for an account.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 16:35
 */
public class ListPlans extends AbstractListRequest<List<Plan>> {

    public ListPlans() {
        super(null, false);
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Plans.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Plans.name;
    }

    @Override
    public List<Plan> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Plan>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
