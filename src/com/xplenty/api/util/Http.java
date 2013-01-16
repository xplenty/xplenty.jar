/**
 * 
 */
package com.xplenty.api.util;

/**
 * @author Yuriy Kovalek
 *
 */
public class Http {

	public static enum MediaType {
		JSON("application/vnd.xplenty+json");
		
		public final String value;
		
		MediaType(String type) {
			value = type;
		}
	}

	public enum Method {
		GET, POST, PUT, DELETE, OPTIONS
	}

}
