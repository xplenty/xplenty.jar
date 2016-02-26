package com.xplenty.api.request.account;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Account;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * List active accounts in which the authenticated user is a member of (with either admin or member role).
 * Optionally, you can supply the input parameters to filter the account list so that it contains only accounts with a
 * specific role or id, and to determine the order by which the list will be sorted.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 18:24
 */
public class ListAccounts extends AbstractListRequest<List<Account>> {
    public static final String PARAMETER_ROLE = "role";

    public ListAccounts(Properties parameters) {
        super(parameters, true);
        validateParameters(parameters);
    }


    private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_ROLE)
                && !(params.get(PARAMETER_ROLE) instanceof Xplenty.AccountRole)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of AccountRole values", PARAMETER_ROLE));
        }
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Accounts.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Accounts.name;
    }

    @Override
    public List<Account> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Account>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
