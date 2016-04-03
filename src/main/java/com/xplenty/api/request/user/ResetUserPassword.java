package com.xplenty.api.request.user;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.User;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Sends user password reset instructions
 *
 * Author: Xardas
 * Date: 23.02.16
 * Time: 18:40
 */
public class ResetUserPassword extends AbstractManipulationRequest<User> {

    public ResetUserPassword(String email) {
        super(null);
        User user = new User();
        user.setEmail(email);
        setEntity(user);
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
        return Xplenty.Resource.ResetUserPassword.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.ResetUserPassword.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }

    @Override
    public User getResponse(Response response) {
        return null;
    }
}
