/**
 * 
 */
package com.xplenty.api.exceptions;


/**
 * @author Yuriy Kovalek
 *
 */
public class AuthFailedException extends RequestFailedException {
	private static final long serialVersionUID = 3015805619788286689L;

	public AuthFailedException(String msg, int status, String response, String apiKey) {
		super("API key <" + apiKey + ">: " + msg, status, response);
	}
}
