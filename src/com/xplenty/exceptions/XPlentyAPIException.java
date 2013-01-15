/**
 * 
 */
package com.xplenty.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyAPIException extends RuntimeException {
	private static final long serialVersionUID = -3832740931698742014L;

	public XplentyAPIException(String msg) {
		super(msg);
	}
	
	public XplentyAPIException(Throwable t) {
		super(t);
	}
	
	public XplentyAPIException(String msg, Throwable t) {
		super(msg, t);
	}
}
