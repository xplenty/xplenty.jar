/**
 * 
 */
package com.xplenty.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class XPlentyInternalServerException extends RequestFailedException {
	private static final long serialVersionUID = -2671281886144379940L;

	public XPlentyInternalServerException(String msg, int status, String response) {
		super(msg, status, response);
	}
}
