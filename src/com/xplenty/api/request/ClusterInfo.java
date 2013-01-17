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
import com.xplenty.api.util.Http;
import com.xplenty.api.util.Http.MediaType;
import com.xplenty.api.util.Http.Method;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterInfo implements Request<Cluster> {

	private long clusterId;
	
	public ClusterInfo(long clusterId) {
		this.clusterId = clusterId;
	}
	
	@Override
	public String getName() {
		return Xplenty.Resource.Cluster.name;
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
		return Xplenty.Resource.Cluster.format(Long.toString(clusterId));
	}

	@Override
	public Cluster getResponse(ClientResponse response) {
		String json = response.getEntity(String.class);		
		try {
			return new ObjectMapper().readValue(json, new TypeReference<Cluster>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

	@Override
	public boolean hasBody() {
		return false;
	}

	@Override
	public Cluster getBody() {
		return null;
	}

}
