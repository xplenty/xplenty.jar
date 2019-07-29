package com.xplenty.api.request.member;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Member;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete an existing member from an account. This call does not delete the user, just the account membership.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 16:48
 */
public class DeleteMember extends AbstractDeleteRequest<Member> {

    public DeleteMember(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.DeleteMember.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeleteMember.name;
    }
}
