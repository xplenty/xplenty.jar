package com.xplenty.api.request.user;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.User;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Author: Xardas
 * Date: 04.01.16
 * Time: 20:01
 */
public class UpdateCurrentUser extends AbstractManipulationRequest<User> {

    public UpdateCurrentUser(User entity) {
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
        return Xplenty.Resource.UpdateUser.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdateUser.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
