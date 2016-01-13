package com.xplenty.api.request.account;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Account;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete an existing account. The operation can be executed only by the account owner.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 19:33
 */
public class DeleteAccount extends AbstractDeleteRequest<Account> {
    private String entityId;

    public DeleteAccount(String entityId) {
        super(0);
        this.entityId = entityId;
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.DeleteAccount.format(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeleteAccount.name;
    }
}
