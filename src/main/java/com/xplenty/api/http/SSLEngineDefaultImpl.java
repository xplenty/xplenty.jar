/*
 * CopyRight Alexey Gromov 2012-...
 * You can freely improve this code
 */

package com.xplenty.api.http;

import javax.net.ssl.SSLContext;
import java.security.SecureRandom;


/**
 *
 * @author xardas
 */
public class SSLEngineDefaultImpl {
    private static SSLContext wc;
    static {
      try {
        wc = SSLContext.getInstance("TLS");
        // use default implementations
        wc.init(null, null, new SecureRandom());
      } catch (Exception ex) { }

    }

    public static SSLContext getSSLContext() {
        return wc;
    }

}
