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

	public AuthFailedException(int status, String response) {
		super("Server declined authorization", status, response);
	}
}
