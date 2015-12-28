package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;

import java.util.Properties;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 20:46
 */
public abstract class AbstractParametrizedRequest<T> implements Request<T> {
    public static final String PARAMETER_STATUS = "status";
    public static final String PARAMETER_SORT = "sort";
    public static final String PARAMETER_DIRECTION = "direction";
    public static final String PARAMETER_OFFSET = "offset";
    public static final String PARAMETER_LIMIT = "limit";

    protected Properties parameters;

    protected AbstractParametrizedRequest(Properties parameters, boolean validateSort) {
        validateParameters(parameters, validateSort);
        this.parameters = parameters;
    }

    @SuppressWarnings("unchecked")
    private void validateParameters(Properties params, boolean validateSort) {

        if (validateSort) {
            if (params.containsKey(PARAMETER_SORT)
                    && !(params.get(PARAMETER_SORT) instanceof Xplenty.Sort)) {
                throw new XplentyAPIException("Invalid 'sort' parameter");
            }

            if (!params.containsKey(PARAMETER_SORT)
                    && params.containsKey(PARAMETER_DIRECTION)) {
                throw new XplentyAPIException("Missing the 'sort' parameter");
            }

            if (params.containsKey(PARAMETER_DIRECTION)
                    && !(params.get(PARAMETER_DIRECTION) instanceof Xplenty.SortDirection)) {
                throw new XplentyAPIException("Invalid 'direction' parameter");
            }
        }

        if (params.containsKey(PARAMETER_LIMIT) && !(params.get(PARAMETER_LIMIT) instanceof Number)) {
            throw new XplentyAPIException("Invalid 'limit' parameter");
        } else if (params.containsKey(PARAMETER_LIMIT) && ((((Number) params.get(PARAMETER_LIMIT)).intValue() > Xplenty.MAX_LIMIT) ||
                ((Number) params.get(PARAMETER_LIMIT)).intValue() < 0)) {
            // we've already checked if it's number, so it's safe to cast
            throw new XplentyAPIException(String.format("'limit' parameter should be less or equal to %s and greater than 0", Xplenty.MAX_LIMIT));
        }

        if (params.containsKey(PARAMETER_OFFSET) && !(params.get(PARAMETER_OFFSET) instanceof Number)) {
            throw new XplentyAPIException("Invalid 'offset' parameter");
        } else if (params.containsKey(PARAMETER_OFFSET) && ((Number) params.get(PARAMETER_OFFSET)).intValue() < 0) {
            // we've already checked if it's number, so it's safe to cast
            throw new XplentyAPIException("'offset' parameter should be greater than 0");
        }
    }

    @Override
    public String getEndpoint() {
        if (parameters.isEmpty()) {
            return getEndpointRoot();
        }
        StringBuilder params = new StringBuilder("?");
        for (Object var: parameters.keySet()) {
            params.append(var).append("=").append(parameters.get(var).toString()).append("&");
        }
        if (params.length() > 1) {
            params.setLength(params.length() - 1);
        } else {
            params.setLength(0);
        }
        return params.insert(0, getEndpointRoot()).toString();
    }


    protected abstract String getEndpointRoot();


}
