/**
 * 
 */
package com.xplenty.api;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.xplenty.api.Xplenty.Protocol;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.exceptions.AuthFailedException;
import com.xplenty.api.exceptions.RequestFailedException;
import com.xplenty.api.request.Request;
import com.xplenty.api.util.Http;

/**
 * Proxy for connecting to the XplentyAPI over HTTP
 * 
 * @author Yuriy Kovalek
 *
 */
class XplentyWebConnector {	
	private static final String API_PATH = "api";
	
	private String HOST = "api-staging.xplenty.com";
	private Protocol PROTOCOL = Protocol.Https;
	private final String ACCOUNT_NAME;
	private final String API_KEY;
	
	private final Client client;
	private Version version = null;

	/**
	 * Construct a new instance for given account and API key
	 * @param accountName name of the associated account, used in URL's
	 * @param apiKey used for authentication
	 */
	XplentyWebConnector(String accountName, String apiKey) {
		ACCOUNT_NAME = accountName;
		API_KEY = apiKey;
		
		ClientConfig config = new DefaultClientConfig();
		client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(apiKey, ""));
	}

	/**
	 * Synchronously execute given request
	 * @param request request to execute
	 * @return
	 */
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
	
	/**
	 * Convenience method for getting a configured {@link WebResource.Builder} for given request
	 * @param request
	 * @return
	 */
	private <T> WebResource.Builder getConfiguredResource(Request<T> request) {
		WebResource.Builder b = client.resource(getMethodURL(request.getEndpoint()))
										.accept(request.getResponseType().value
													+ (version == null ? "" : "; " + version.format())
												);
		if (request.hasBody()) {
			StringWriter sw = new StringWriter();
			try {
				new ObjectMapper().writeValue(sw, request.getBody());
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.entity(sw.toString()).type(Http.MediaType.JSON.value);
		}
		
		return b;
	}
	
	/**
	 * Constructs the actual URL
	 * @param methodEndpoint
	 * @return
	 */
	private String getMethodURL(String methodEndpoint) {
		return PROTOCOL + "://" + HOST + "/" + ACCOUNT_NAME + "/" + API_PATH + "/" + methodEndpoint;
	}
	
	/**
	 * Check the response status and throws exception on errors
	 * @param request used request
	 * @param response received response
	 * @throws AuthFailedException
	 * @throws RequestFailedException
	 */
	private <T> void validate(Request<T> request, ClientResponse response) {
		if (Status.OK == response.getClientResponseStatus()
				|| Status.CREATED == response.getClientResponseStatus())
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

	public void setVersion(Version ver) {
		version = ver;
	}

	public void setHost(String host) {
		HOST = host;
	}

	public void setProtocol(Protocol proto) {
		PROTOCOL = proto;
	}
}
