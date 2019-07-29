package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.User;

/**
 * Get Info for the authenticated user.
 * Author: Xardas
 * Date: 04.01.16
 * Time: 19:23
 */
public class CurrentUserInfo extends AbstractInfoRequest<User> {
    private final String currentPassword;

    public CurrentUserInfo(String currentPassword) {
        super(-1L);
        this.currentPassword = currentPassword;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.User.name;
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.User.value;
    }

    @Override
    public boolean hasBody() {
        return currentPassword != null;
    }

    @Override
    public Object getBody() {
        if (currentPassword != null) {
            return new User(currentPassword);
        }
        return null;
    }
}
