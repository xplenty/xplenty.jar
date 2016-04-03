package com.xplenty.api.request.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Notification;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * This call returns a list of notifications of the authenticated user.
 * Optionally, you can supply the input parameters to filter the list so that it contains only unread notifications
 * or all notifications, and to determine the order by which the list will be sorted.
 * Author: Xardas
 * Date: 07.01.16
 * Time: 17:51
 */
public class ListNotifications extends AbstractListRequest<List<Notification>> {
    /**
     * 	The call will return only unread notifications if the value is set to 'false'.
     */
    public static final String PARAMETER_LIST_ALL = "all";

    public ListNotifications(Properties parameters) {
        super(parameters, false);
        validateParameters(parameters);
    }

    private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_LIST_ALL)
                && !(params.get(PARAMETER_LIST_ALL) instanceof Boolean)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be a boolean!", PARAMETER_LIST_ALL));
        }
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.UserNotifications.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UserNotifications.name;
    }

    @Override
    public List<Notification> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Notification>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
