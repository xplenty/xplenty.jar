/**
 * 
 */
package com.xplenty.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class XPlentyAPIException extends RuntimeException {
	private static final long serialVersionUID = -3832740931698742014L;

	public XPlentyAPIException(String msg) {
		super(msg);
	}
	
	public XPlentyAPIException(Throwable t) {
		super(t);
	}
	
	public XPlentyAPIException(String msg, Throwable t) {
		super(msg, t);
	}
}
