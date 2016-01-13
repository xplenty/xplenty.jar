package com.xplenty.api.request.member;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Member;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List existing account members. Optionally, you can supply the input parameters to filter the member list so that
 * it contains user with specific role or email only and to determine the order by which the list will be sorted.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 15:58
 */
public class ListMembers extends AbstractListRequest<List<Member>> {
    public static final String PARAMETER_ROLE = "role";
    public static final String PARAMETER_EMAIL = "email";

    public ListMembers(Properties parameters) {
        super(parameters, true);
        validateParameters(parameters);
    }


    private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_ROLE)
                && !(params.get(PARAMETER_ROLE) instanceof Xplenty.AccountRole)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter", PARAMETER_ROLE));
        }
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Members.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Members.name;
    }

    @Override
    public List<Member> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Member>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
