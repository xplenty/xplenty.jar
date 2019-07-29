/**
 * 
 */
package com.xplenty.api.request;

import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;

/**
 * @author Yuriy Kovalek
 *
 */
public interface Request<T> {
	
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
	
	T getResponse(Response response);
}
