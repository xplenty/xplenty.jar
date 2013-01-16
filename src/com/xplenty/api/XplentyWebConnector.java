/**
 * 
 */
package com.xplenty.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.xplenty.api.exceptions.AuthFailedException;
import com.xplenty.api.exceptions.RequestFailedException;
import com.xplenty.api.request.Request;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyWebConnector {
	private static final String BASE_URL = "https://api-staging.xplenty.com";	
	private static final String API_PATH = "api";
	
	private final String ACCOUNT_NAME;
	private final String API_KEY;
	
	private final Client client;

	public XplentyWebConnector(String accountName, String apiKey) {
		ACCOUNT_NAME = accountName;
		API_KEY = apiKey;
		
		ClientConfig config = new DefaultClientConfig();
		client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(apiKey, ""));
	}

	public <T> T execute(Request<T> request) {
		WebResource.Builder builder = getConfiguredResource(request);
		ClientResponse response = null;
		switch (request.getHttpMethod()) {
			case GET: 		response = builder.get(ClientResponse.class); break;
			case POST:		response = builder.post(ClientResponse.class); break;
			case PUT: 		response = builder.put(ClientResponse.class); break;
			case DELETE: 	response = builder.delete(ClientResponse.class); break;
		}
		validate(request, response);
		return request.getResponse(response);		
	};
	
	private <T> WebResource.Builder getConfiguredResource(Request<T> request) {
		return client.resource(getMethodURL(request.getEndpoint()))
					.accept(request.getResponseType().value);
	}
	
	private String getMethodURL(String methodEndpoint) {
		return BASE_URL + "/" + ACCOUNT_NAME + "/" + API_PATH + "/" + methodEndpoint;
	}
	
	private <T> void validate(Request<T> request, ClientResponse response) {
		if (Status.OK == response.getClientResponseStatus())
			return;
		if (Status.UNAUTHORIZED == response.getClientResponseStatus())
			throw new AuthFailedException(response.getStatus(), response.getEntity(String.class));
		
		throw new RequestFailedException(request.getName() + " failed", response.getStatus(), response.getEntity(String.class));	
	}

	String getAccountName() {
		return ACCOUNT_NAME;
	}

	String getApiKey() {
		return API_KEY;
	}
}
