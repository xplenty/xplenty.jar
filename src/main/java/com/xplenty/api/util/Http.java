/**
 * 
 */
package com.xplenty.api.util;

/**
 * Convenience structures for HTTP communication
 * 
 * @author Yuriy Kovalek
 *
 */
public class Http {

	/**
	 * Media types supported by Xplenty API
	 */
	public static enum MediaType {
		JSON("application/vnd.xplenty+json");
		
		public final String value;
		
		MediaType(String type) {
			value = type;
		}
	}

	/**
	 * HTTP methods supported by Xplenty API
	 */
	public enum Method {
		GET, POST, PUT, DELETE
	}

}
