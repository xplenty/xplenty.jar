package com.xplenty.api.request.member;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Member;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Set existing account member's role
 * Author: Xardas
 * Date: 07.01.16
 * Time: 16:27
 */
public class SetMemberRole extends AbstractManipulationRequest<Member> {

    public SetMemberRole(long id, Xplenty.AccountRole role) {
        super(new Member(id, role));
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SetMemberRole.format(entity.getId().toString());
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SetMemberRole.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
