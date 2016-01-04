/**
 * 
 */
package com.xplenty.api.http;

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

    /**
     * Protocol used by Xplenty API Server
     */
	public static enum Protocol {
		Http("http"),
		Https("https");
		
		public final String value;
		
		Protocol(String value) {
			this.value = value;
		}
	}

    /**
     * Http Client Implementation used to connect
     */
    public static enum HttpClientImpl {
        SyncNetty, /* for future impl AsyncNetty, */ Jersey;
    }
    
    public static enum ResponseStatus {
		HTTP_200(200, "OK: Request succeeded."),
		HTTP_201(201, "Created: The requested resource was created successfully."),
		HTTP_204(204, "No Content: Request succeeded. No content is returned."),
		HTTP_304(304, "Not Modified: There was no new data to return."),
		HTTP_400(400, "Bad Request: The request was invalid. An accompanying error message will explain why."),
		HTTP_401(401, "Unauthorized: You are attempting to access the API with invalid credentials."),
		HTTP_402(402, "Payment Required: You must confirm your billing info to use this API."),
		HTTP_403(403, "Forbidden: The request has been refused. An accompanying error message will explain why."),
		HTTP_404(404, "Not Found: The URI requested is invalid or the resource requested does not exist."),
		HTTP_406(406, "Not Acceptable: The requested mime-type is not acceptable."),
		HTTP_415(415, "Unsupported Media Type: The specified media type is not supported."),
		HTTP_422(422, "Unprocessable Entity: You have sent invalid fields."),
		HTTP_429(429, "Too Many Requests: The request exceeded the rate limitations."),
		HTTP_500(500, "Internal Server Error: An internal error occurred in the request."),
		HTTP_502(502, "Bad Gateway: Xplenty is down or being upgraded."),
		HTTP_503(503, "Service Unavailable: The Xplenty servers are up, but overloaded with requests. Try again later."),
		HTTP_505(505, "Version not supported");

        private final int code;
        private final String description;

        ResponseStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static ResponseStatus fromCode(int code) {
            return ResponseStatus.valueOf(String.format("HTTP_%d", code));
        }
    }

}
