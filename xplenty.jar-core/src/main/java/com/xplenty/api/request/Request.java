/**
 * 
 */
package com.xplenty.api.request;

import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.util.Http;

/**
 * @author Yuriy Kovalek
 *
 */
public interface Request<T> {
    public static final String PARAMETER_OFFSET = "offset";
    public static final String PARAMETER_LIMIT = "limit";
	
	String getName();
	
	/**
     * HTTP method. e.g. GET, POST, PUT, DELETE
     * @return The HTTP method the request uses.
     */
	Http.Method getHttpMethod();
	
	/**
     * Path and query parameters of a URL.
     * @return The path and query parameters as a String.
     */
	Http.MediaType getResponseType();
	
	String getEndpoint();
	
	boolean hasBody();
	
	Object getBody();
	
	T getResponse(ClientResponse response);
}
