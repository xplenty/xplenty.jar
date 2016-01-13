package com.xplenty.api.request.member;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Member;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Information about member of the account.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 16:43
 */
public class MemberInfo extends AbstractInfoRequest<Member> {

    public MemberInfo(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.Member.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Member.name;
    }
}
