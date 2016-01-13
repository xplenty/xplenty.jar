/**
 * 
 */
package com.xplenty.api.exceptions;

import com.xplenty.api.http.Http;

/**
 * @author Yuriy Kovalek
 *
 */
public class RequestFailedException extends XplentyAPIException {
	private static final long serialVersionUID = -456749863406425145L;
	
	private final int status;
	private final String response;
    private final String statusDescription;
	
	public RequestFailedException(String msg, Http.ResponseStatus responseStatus, String response) {
		super(String.format("%s, HTTP status code: %s[%s], server response: [%s]" , msg, responseStatus.getCode(), responseStatus.getDescription(), response));
		this.status = responseStatus.getCode();
		this.response = response;
        this.statusDescription = responseStatus.getDescription();
	}
	
	public int getStatus() {
		return status;
	}
	
	public String getResponse() {
		return response;
	}

    public String getStatusDescription() {
        return statusDescription;
    }
}
