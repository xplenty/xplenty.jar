package com.xplenty.api.request.account;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Account;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Information about active account. The authenticated user must be a member of this account (with either admin or member role).
 * Author: Xardas
 * Date: 07.01.16
 * Time: 19:41
 */
public class AccountInfo extends AbstractInfoRequest<Account> {
    private String entityId;

    public AccountInfo(String entityId) {
        super(0);
        this.entityId = entityId;
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.Account.format(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Account.name;
    }
}
