/**
 * 
 */
package com.xplenty.api.request;

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
		try {
			return response.getEntity(Cluster.class);
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

}
