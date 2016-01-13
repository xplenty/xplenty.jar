package com.xplenty.api.request;

import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.request.Request;

/**
 * Get job output log
 * Author: Xardas
 * Date: 08.01.16
 * Time: 19:24
 */
public class RawGetRequest implements Request<String> {
    private final String endpoint;

    public RawGetRequest(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.GET;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return endpoint;
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
    public String getResponse(Response response) {
        return response.getRawContent();
    }
}
