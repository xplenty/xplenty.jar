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
import com.xplenty.api.model.Package;
import com.xplenty.api.util.Http.MediaType;
import com.xplenty.api.util.Http.Method;

import java.util.List;
import java.util.Properties;


/**
 * Request for retrieval of all available packages
 * Author: Xardas
 * Date: 16.12.15
 * Time: 18:08
 */
public class ListPackages extends AbstractParametrizedRequest<List<Package>> {

	public ListPackages(Properties params) {
		super(params, false);
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
    protected String getEndpointRoot() {
        return Xplenty.Resource.Packages.value;
    }

    @Override
	public List<Package> getResponse(ClientResponse response) {
		String json = response.getEntity(String.class);
		try {
			return new ObjectMapper().readValue(json, new TypeReference<List<Package>>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Packages.name;
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
