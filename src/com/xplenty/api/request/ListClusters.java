/**
 * 
 */
package com.xplenty.api.request;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.Xplenty;
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
		return Xplenty.Resource.Clusters.value;
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
