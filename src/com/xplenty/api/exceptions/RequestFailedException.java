/**
 * 
 */
package com.xplenty.api.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class RequestFailedException extends XplentyAPIException {
	private static final long serialVersionUID = -456749863406425145L;
	
	private int status;
	private String response;
	
	public RequestFailedException(String msg, int status, String response) {
		super(msg + "HTTP status code: " + status + ", server response: " + response);
		this.status = status;
		this.response = response;
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getResponse() {
		return response;
	}
}
