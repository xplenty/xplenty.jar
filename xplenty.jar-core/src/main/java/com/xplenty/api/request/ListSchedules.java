/**
 * 
 */
package com.xplenty.api.request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.util.Http.MediaType;
import com.xplenty.api.util.Http.Method;

import java.util.List;
import java.util.Properties;


/**
 * Request for retrieval of all available schedules
 * Author: Xardas
 * Date: 16.12.15
 * Time: 18:08
 */
public class ListSchedules implements Request<List<Schedule>> {
    public static final String PARAMETER_STATUS = "status";
    public static final String PARAMETER_SORT = "sort";
    public static final String PARAMETER_DIRECTION = "direction";


	private Properties parameters;

	public ListSchedules(Properties params) {
		validateParameters(params);
		parameters = params;
	}

    @SuppressWarnings("unchecked")
	private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_STATUS)
                && !(params.get(PARAMETER_STATUS) instanceof Xplenty.ScheduleStatus)) {
            throw new XplentyAPIException("Invalid 'status' parameter");
        }

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

        if (params.containsKey(PARAMETER_LIMIT) && !(params.get(PARAMETER_LIMIT) instanceof Number)) {
            throw new XplentyAPIException("Invalid 'limit' parameter");
        }

        // we've already checked if it's number, so it's safe to cast
        if (((Number) params.get(PARAMETER_LIMIT)).intValue() > Xplenty.MAX_LIMIT) {
            throw new XplentyAPIException(String.format("'limit' parameter should be less or equal to %s", Xplenty.MAX_LIMIT));
        }

        if (params.containsKey(PARAMETER_OFFSET) && !(params.get(PARAMETER_OFFSET) instanceof Number)) {
            throw new XplentyAPIException("Invalid 'offset' parameter");
        }

	}

	@Override
	public Method getHttpMethod() {
		return Method.GET;
	}

	@Override
	public MediaType getResponseType() {
		return MediaType.JSON;
	}

	@Override
	public String getEndpoint() {
		if (parameters.isEmpty()) {
            return Xplenty.Resource.Schedules.value;
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
		return params.insert(0, Xplenty.Resource.Schedules.value).toString();
	}

	@Override
	public List<Schedule> getResponse(ClientResponse response) {
		String json = response.getEntity(String.class);
		try {
			return new ObjectMapper().readValue(json, new TypeReference<List<Schedule>>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Schedules.name;
	}

	@Override
	public boolean hasBody() {
		return false;
	}

	@Override
	public List<Cluster> getBody() {
		return null;
	}

}
