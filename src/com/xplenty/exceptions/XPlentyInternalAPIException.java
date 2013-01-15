/**
 * 
 */
package com.xplenty.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class XPlentyInternalAPIException extends XPlentyAPIException {
	private static final long serialVersionUID = -3711963956067858478L;

	public XPlentyInternalAPIException(String msg) {
		super(msg);
	}
	
	public XPlentyInternalAPIException(Throwable t) {
		super(t);
	}
	
	public XPlentyInternalAPIException(String msg, Throwable t) {
		super(msg, t);
	}
}
