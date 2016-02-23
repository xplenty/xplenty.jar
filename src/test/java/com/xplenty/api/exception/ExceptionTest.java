package com.xplenty.api.exception;

import com.xplenty.api.exceptions.AuthFailedException;
import com.xplenty.api.exceptions.RequestFailedException;
import com.xplenty.api.http.Http;
import junit.framework.TestCase;

/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 6:44
 */
public class ExceptionTest extends TestCase {
    public void testAuthFailedException() {
        AuthFailedException afe = new AuthFailedException(Http.ResponseStatus.HTTP_401, "bad auth");
        assertEquals("bad auth", afe.getResponse());
        assertEquals(Http.ResponseStatus.HTTP_401.getCode(), afe.getStatus());
        assertEquals(Http.ResponseStatus.HTTP_401.getDescription(), afe.getStatusDescription());
    }

    public void testRequestFailedException() {
        RequestFailedException afe = new RequestFailedException("Bad things really happen", Http.ResponseStatus.HTTP_406, "can't accept your request");
        assertEquals("can't accept your request", afe.getResponse());
        assertEquals(Http.ResponseStatus.HTTP_406.getCode(), afe.getStatus());
        assertEquals(Http.ResponseStatus.HTTP_406.getDescription(), afe.getStatusDescription());
    }

}
