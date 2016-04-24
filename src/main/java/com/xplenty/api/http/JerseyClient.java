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
    private final Version version;
    private final int timeout;

	private final Client client;


    /**
     * Construct a new instance for given account and API key
     * @param accountName name of the associated account, used in URL's
     * @param apiKey used for authentication
     * @param host host to connect
     * @param protocol protocol to use
     * @param timeout timeout for response.
     * @param logHttpCommunication enables logging of requests and responses
     */
	JerseyClient(String accountName, String apiKey, String host, Http.Protocol protocol, Version version, int timeout, boolean logHttpCommunication) {
		this.accountName = accountName;
		this.apiKey = apiKey;
        this.host = host;
        this.protocol = protocol;
        this.version = version;
        this.timeout = timeout;

		ClientConfig config = new DefaultClientConfig();
        config.getProperties().put(ClientConfig.PROPERTY_READ_TIMEOUT, timeout * 1000);
        config.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, timeout * 1000);
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
        final String entity = response.hasEntity() && response.getStatus() != 204 ? response.getEntity(String.class) : null;

        Response processedResponse = Response.forContentType(request.getResponseType(), entity,
                response.getStatus(), convertJerseyHeaders(response.getHeaders()));
        processedResponse.validate(request.getName());
		return request.getResponse(processedResponse);
	}

    protected Map<String, String> convertJerseyHeaders(MultivaluedMap<String, String> headers) {
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
    protected <T> WebResource.Builder getConfiguredResource(Request<T> request) {
		WebResource.Builder b = client.resource(getMethodURL(request.getEndpoint(host, accountName)))
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
			b.entity(sw.toString()).type(Http.MediaType.PURE_JSON.value);
		}

		return b;
	}

	/**
	 * Constructs the actual URL
	 * @param methodEndpoint - describes the action type
	 * @return filly qualified URL
	 */
    protected String getMethodURL(String methodEndpoint) {
        if (methodEndpoint.startsWith("http")) {
            return methodEndpoint;
        }
        return String.format("%s://%s", protocol, methodEndpoint);
	}

	public String getAccountName() {
		return accountName;
	}

	public String getApiKey() {
		return apiKey;
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
    public int getTimeout() {
        return timeout;
    }

    @Override
    public void shutdown() {
        if (client != null) {
            client.destroy();
        }
    }
}
