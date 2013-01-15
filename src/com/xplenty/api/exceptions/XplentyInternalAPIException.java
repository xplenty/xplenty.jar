/**
 * 
 */
package com.xplenty.api.exceptions;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyInternalAPIException extends XplentyAPIException {
	private static final long serialVersionUID = -3711963956067858478L;

	public XplentyInternalAPIException(String msg) {
		super(msg);
	}
	
	public XplentyInternalAPIException(Throwable t) {
		super(t);
	}
	
	public XplentyInternalAPIException(String msg, Throwable t) {
		super(msg, t);
	}
}
