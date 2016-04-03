package com.xplenty.api.http;

import junit.framework.TestCase;

import javax.net.ssl.SSLContext;

/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 9:53
 */
public class SSLEngineDefaultImplTest extends TestCase {
    public void testEngine() {
        SSLContext context = SSLEngineDefaultImpl.getSSLContext();
        assertNotNull(context);
        assertEquals("TLS", context.getProtocol());
        assertTrue(context.getProvider().entrySet().size() > 0);
    }

}
