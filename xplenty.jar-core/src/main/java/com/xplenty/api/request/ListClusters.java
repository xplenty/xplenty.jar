/**
 * 
 */
package com.xplenty.api.request;

import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.Xplenty.Sort;
import com.xplenty.api.Xplenty.SortDirection;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.util.Http;
import com.xplenty.api.util.Http.MediaType;
import com.xplenty.api.util.Http.Method;

/**
 * @author Yuriy Kovalek
 *
 */
public class ListClusters implements Request<List<Cluster>> {
	public static final String PARAMETER_STATUS = "status";
	public static final String PARAMETER_SORT = "sort";
	public static final String PARAMETER_DIRECTION = "direction";
	
	private Properties parameters;
	
	public ListClusters(Properties params) {
		validateParameters(params);
		parameters = params;
	}

	private void validateParameters(Properties params) {
		if (	params.containsKey(PARAMETER_STATUS)
				&& !(params.get(PARAMETER_STATUS) instanceof ClusterStatus) 
				&& !(params.get(PARAMETER_STATUS) instanceof String && "all".equals(params.get(PARAMETER_STATUS)))
			)
			throw new XplentyAPIException("Invalid 'status' parameter");
		
		if (
				params.containsKey(PARAMETER_SORT)
				&& !(params.get(PARAMETER_SORT) instanceof Sort)
			)
			throw new XplentyAPIException("Invalid 'sort' parameter");
		
		if (
				!params.containsKey(PARAMETER_SORT)
				&& params.containsKey(PARAMETER_DIRECTION)
			)
			throw new XplentyAPIException("Missing the 'sort' parameter");
		
		if (
				params.containsKey(PARAMETER_DIRECTION)
				&& !(params.get(PARAMETER_DIRECTION) instanceof SortDirection)
			)
			throw new XplentyAPIException("Invalid 'direction' parameter");
	}

	@Override
	public Method getHttpMethod() {
		return Http.Method.GET;
	}
	
	@Override
	public MediaType getResponseType() {
		return Http.MediaType.JSON;
	}

	@Override
	public String getEndpoint() {
		if (parameters.isEmpty())
			return Xplenty.Resource.Clusters.value;
		String params = "?";
		for (Object var: parameters.keySet()) {
			params += (String)var + "=" + parameters.get(var).toString() + "&";
		}
		return Xplenty.Resource.Clusters.value + params.substring(0, params.length()-1);
	}

	@Override
	public List<Cluster> getResponse(ClientResponse response) {
		String json = response.getEntity(String.class);
		try {
			return new ObjectMapper().readValue(json, new TypeReference<List<Cluster>>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Clusters.name;
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
