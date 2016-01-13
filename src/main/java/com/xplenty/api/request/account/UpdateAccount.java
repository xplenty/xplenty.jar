package com.xplenty.api.request.account;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Account;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * This call updates account.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 18:51
 */
public class UpdateAccount extends AbstractManipulationRequest<Account> {

    public UpdateAccount(Account entity) {
        super(entity);
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
        return Xplenty.Resource.UpdateAccount.format(String.valueOf(entity.getAccountId()));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdateAccount.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
