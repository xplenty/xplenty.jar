package com.xplenty.api.request.user;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.request.AbstractRequest;

/**
 * Marks the authenticated user's notifications as read. This call returns empty response.
 * Author: Xardas
 * Date: 09.01.16
 * Time: 16:40
 */
public class MarkNotificationsRead extends AbstractRequest<Void> {

    public MarkNotificationsRead() {
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public Object getBody() {
        return null;
    }

    @Override
    public Void getResponse(Response response) {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.MarkUserNotificationRead.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.MarkUserNotificationRead.name;
    }
}
