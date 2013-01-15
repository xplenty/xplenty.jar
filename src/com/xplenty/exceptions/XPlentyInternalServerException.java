/**
 * 
 */
package com.xplenty.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyInternalServerException extends RequestFailedException {
	private static final long serialVersionUID = -2671281886144379940L;

	public XplentyInternalServerException(String msg, int status, String response) {
		super(msg, status, response);
	}
}
