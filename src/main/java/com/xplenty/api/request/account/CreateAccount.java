package com.xplenty.api.request.account;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Account;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Creates a new account. This operation is possible only by confirmed users.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 18:02
 */
public class CreateAccount extends AbstractManipulationRequest<Account> {

    public CreateAccount(String name, String region, String accountId) {
        super(new Account(name, region, accountId));
    }

    @Override
    protected String getPackKey() {
        return null;
    }


    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.CreateAccount.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreateAccount.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
