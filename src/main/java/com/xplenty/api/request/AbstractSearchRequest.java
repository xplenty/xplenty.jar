package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;

import java.util.Properties;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 20:46
 */
public abstract class AbstractSearchRequest<T> extends AbstractRequest<T> {

    public static final String PARAMETER_SORT = "sort";
    public static final String PARAMETER_DIRECTION = "direction";
    public static final String PARAMETER_OFFSET = "offset";
    public static final String PARAMETER_LIMIT = "limit";

    protected Properties parameters;

    protected AbstractSearchRequest(Properties parameters) {
        validateParameters(parameters);
        this.parameters = parameters;
    }

    @SuppressWarnings("unchecked")
    private void validateParameters(Properties params) {
        if (params == null) {
            return;
        }

        if (params.containsKey(PARAMETER_SORT)
                && !(params.get(PARAMETER_SORT) instanceof Xplenty.Sort || params.get(PARAMETER_SORT) instanceof Xplenty.SearchSort)) {
            throw new XplentyAPIException(String.format("Invalid '%s' parameter, should be one of Sort values", PARAMETER_SORT));
        }

        if (!params.containsKey(PARAMETER_SORT)
                && params.containsKey(PARAMETER_DIRECTION)) {
            throw new XplentyAPIException(String.format("Missing the '%s' parameter", PARAMETER_SORT));
        }

        if (params.containsKey(PARAMETER_DIRECTION)
                && !(params.get(PARAMETER_DIRECTION) instanceof Xplenty.SortDirection)) {
            throw new XplentyAPIException(String.format("Invalid '%s' parameter, should be one of SortDirection values", PARAMETER_DIRECTION));
        }

        if (params.containsKey(PARAMETER_LIMIT) && !(params.get(PARAMETER_LIMIT) instanceof Number)) {
            throw new XplentyAPIException(String.format("Invalid '%s' parameter, should be number", PARAMETER_LIMIT));
        } else if (params.containsKey(PARAMETER_LIMIT) && ((((Number) params.get(PARAMETER_LIMIT)).intValue() > Xplenty.MAX_LIMIT) ||
                ((Number) params.get(PARAMETER_LIMIT)).intValue() < 0)) {
            // we've already checked if it's number, so it's safe to cast
            throw new XplentyAPIException(String.format("'%s' parameter should be less or equal to %s and greater than 0",
                    PARAMETER_LIMIT, Xplenty.MAX_LIMIT));
        }

        if (params.containsKey(PARAMETER_OFFSET) && !(params.get(PARAMETER_OFFSET) instanceof Number)) {
            throw new XplentyAPIException(String.format("Invalid '%s' parameter, should be number", PARAMETER_OFFSET));
        } else if (params.containsKey(PARAMETER_OFFSET) && ((Number) params.get(PARAMETER_OFFSET)).intValue() < 0) {
            // we've already checked if it's number, so it's safe to cast
            throw new XplentyAPIException(String.format("'%s' parameter should be greater than 0", PARAMETER_OFFSET));
        }
    }

    @Override
    public boolean hasBody() {
        return true;
    }

    @Override
    public Object getBody() {
        return parameters;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.GET;
    }

}
