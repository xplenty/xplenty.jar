/**
 * 
 */
package com.xplenty;

import com.xplenty.exceptions.RequestFailedException;

/**
 * @author Yuriy Kovalek
 *
 */
public class PaymentRequiredException extends RequestFailedException {
	private static final long serialVersionUID = 8773155164134746770L;

	public PaymentRequiredException(String msg, int status, String response) {
		super(msg, status, response);
	}
}
