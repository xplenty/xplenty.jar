package com.xplenty.api.request.member;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Member;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Creates a new member on an account. The call sends an invitation to join Xplenty in case the user is not yet a user of Xplenty.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 15:38
 */
public class CreateMember extends AbstractManipulationRequest<Member> {


    public CreateMember(String email, Xplenty.AccountRole role) {
        super(new Member(email, role));
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.CreateMember.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreateMember.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
