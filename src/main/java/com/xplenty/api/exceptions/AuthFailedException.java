/**
 * 
 */
package com.xplenty.api.exceptions;


import com.xplenty.api.http.Http;

/**
 * @author Yuriy Kovalek
 *
 */
public class AuthFailedException extends RequestFailedException {
	private static final long serialVersionUID = 3015805619788286689L;

	public AuthFailedException(Http.ResponseStatus status, String response) {
		super("Server declined authorization", status, response);
	}
}
