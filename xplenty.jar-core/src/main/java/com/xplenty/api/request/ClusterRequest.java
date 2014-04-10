/**
 * 
 */
package com.xplenty.api.request;

import java.util.HashMap;
import java.util.Map;

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
public abstract class ClusterRequest implements Request<Cluster> {
	protected final Cluster cluster;

	public ClusterRequest(Cluster cluster) {
		this.cluster = cluster;
	}
	
	@Override
	public abstract String getName();

	@Override
	public abstract Method getHttpMethod();

	@Override
	public MediaType getResponseType() {
		return Http.MediaType.JSON;
	}

	@Override
	public abstract String getEndpoint();

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
		return true;
	}

	@Override
	public Object getBody() {
		Map<String, Object> j = new HashMap<String, Object>();
		j.put("cluster", cluster);
		return j;
	}
}
