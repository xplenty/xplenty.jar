package com.xplenty.api.request;

/**
 * Author: Xardas
 * Date: 04.01.16
 * Time: 19:41
 */
public abstract class AbstractRequest<T> implements Request<T> {
    static final String API_PATH = "api";

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s/%s/%s", apiHost, accountName, API_PATH, getEndpoint());
    }

    protected abstract String getEndpoint();
}
