package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 20:46
 */
public abstract class AbstractListRequest<T> extends AbstractRequest<T> {

    public static final String PARAMETER_STATUS = "status";
    public static final String PARAMETER_SINCE = "since";
    public static final String PARAMETER_SORT = "sort";
    public static final String PARAMETER_DIRECTION = "direction";
    public static final String PARAMETER_OFFSET = "offset";
    public static final String PARAMETER_LIMIT = "limit";

    protected final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    protected Properties parameters;

    protected AbstractListRequest(Properties parameters, boolean validateSort) {
        validateParameters(parameters, validateSort);
        this.parameters = parameters;
    }

    @SuppressWarnings("unchecked")
    private void validateParameters(Properties params, boolean validateSort) {
        if (params == null) {
            return;
        }

        if (validateSort) {
            if (params.containsKey(PARAMETER_SORT)
                    && !(params.get(PARAMETER_SORT) instanceof Xplenty.Sort)) {
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
        }

        if (params.contains(PARAMETER_SINCE) && !(params.get(PARAMETER_SINCE) instanceof Date)) {
            throw new XplentyAPIException(String.format("Invalid '%s' parameter, should be date", PARAMETER_SINCE));
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
    public String getEndpoint() {
        if (parameters == null || parameters.isEmpty()) {
            return getEndpointRoot();
        }
        StringBuilder params = new StringBuilder("?");
        for (Object var : parameters.keySet()) {
            params.append(var).append("=");
            final Object param = parameters.get(var);
            if (param instanceof Date) {
                params.append(dateFormat.format(param));
            } else if (param instanceof List) {
                final List listParam = (List) param;
                for (Object obj : listParam) {
                    params.append(obj.toString()).append(",");
                }
                if (listParam.size() > 0) {
                    params.setLength(params.length() - 1);
                }
            } else {
                params.append(param.toString());
            }
            params.append("&");
        }
        if (params.length() > 1) {
            params.setLength(params.length() - 1);
        } else {
            params.setLength(0);
        }
        return params.insert(0, getEndpointRoot()).toString();
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public T getBody() {
        return null;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.GET;
    }

    protected abstract String getEndpointRoot();


}
