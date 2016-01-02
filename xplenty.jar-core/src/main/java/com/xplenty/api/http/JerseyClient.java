/**
 * 
 */
package com.xplenty.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.xplenty.api.Xplenty.Version;
import com.xplenty.api.exceptions.AuthFailedException;
import com.xplenty.api.exceptions.RequestFailedException;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.request.Request;

import javax.ws.rs.core.MultivaluedMap;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Proxy for connecting to the XplentyAPI over HTTP
 * 
 * @author Yuriy Kovalek and Xardas
 *
 */
public class JerseyClient implements HttpClient {

	private final String host;
	private final Http.Protocol protocol;
	private final String accountName;
	private final String apiKey;

	private final Client client;
	private Version version = null;

    /**
     * Construct a new instance for given account and API key
     * @param accountName name of the associated account, used in URL's
     * @param apiKey used for authentication
     * @param host host to connect
     * @param protocol protocol to use
     * @param timeout timeout for response.
     * @param logHttpCommunication enables logging of requests and responses
     */
	JerseyClient(String accountName, String apiKey, String host, Http.Protocol protocol, int timeout, boolean logHttpCommunication) {
		this.accountName = accountName;
		this.apiKey = apiKey;
        this.host = host;
        this.protocol = protocol;

		ClientConfig config = new DefaultClientConfig();
		client = Client.create(config);
		client.addFilter(new HTTPBasicAuthFilter(apiKey, ""));
        if (logHttpCommunication) {
            client.addFilter(new LoggingFilter());
        }
	}

	/**
	 * Synchronously execute given request
	 * @param request request to execute
	 * @return respective response type
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
        Response processedResponse = Response.forContentType(request.getResponseType(), response.getEntity(String.class),
                response.getStatus(), convertJerseyHeaders(response.getHeaders()));
		return request.getResponse(processedResponse);
	}

    private Map<String, String> convertJerseyHeaders(MultivaluedMap<String, String> headers) {
        final Map<String, String> convertedHeaders = new HashMap<>();
        for (String header : headers.keySet()) {
            convertedHeaders.put(header, headers.getFirst(header));
        }
        return convertedHeaders;
    }

	/**
	 * Convenience method for getting a configured {@link com.sun.jersey.api.client.WebResource.Builder} for given request
	 * @param request that would be submitted to the XPlenty Server
	 * @return  builder
	 */
	private <T> WebResource.Builder getConfiguredResource(Request<T> request) {
		WebResource.Builder b = client.resource(getMethodURL(request.getEndpoint()))
										.accept(request.getResponseType().value
													+ (version == null ? "" : "; " + version.format())
												);
		if (request.hasBody()) {
			StringWriter sw = new StringWriter();
			try {
                ObjectMapper objectMapper = JsonMapperFactory.getInstance();
                objectMapper.writeValue(sw, request.getBody());
			} catch (Exception e) {
				throw new XplentyAPIException(e);
			}
			b.entity(sw.toString()).type(Http.MediaType.JSON.value);
		}

		return b;
	}

	/**
	 * Constructs the actual URL
	 * @param methodEndpoint - describes the action type
	 * @return filly qualified URL
	 */
	private String getMethodURL(String methodEndpoint) {
		return protocol + "://" + host + "/" + accountName + "/" + API_PATH + "/" + methodEndpoint;
	}

	/**
	 * Check the response status and throws exception on errors
	 * @param request used request
	 * @param response received response
	 * @throws com.xplenty.api.exceptions.AuthFailedException
	 * @throws com.xplenty.api.exceptions.RequestFailedException
	 */
	private <T> void validate(Request<T> request, ClientResponse response) {
		if (response.getClientResponseStatus() != null)
			switch (response.getClientResponseStatus()){
	            case OK : case CREATED : case NO_CONTENT : return;
	            case UNAUTHORIZED : throw new AuthFailedException(response.getStatus(), response.getEntity(String.class));
	            default: break;
	        }
		throw new RequestFailedException(request.getName() + " failed", response.getStatus(), response.getEntity(String.class));
	}

	public String getAccountName() {
		return accountName;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setVersion(Version ver) {
		version = ver;
	}

	public Http.Protocol getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public Version getVersion() {
		return version;
	}

    @Override
    public void shutdown() {
        if (client != null) {
            client.destroy();
        }
    }
}
