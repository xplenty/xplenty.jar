/*
 * CopyRight Alexey Gromov 2012-...
 * You can freely improve this code
 */

package com.xplenty.api.http;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


/**
 *
 * @author xardas
 */
public class SSLEngineDefaultImpl {
    private static SSLContext wc;
    private static HostnameVerifier hv;
    static {
      TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
          public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
          }
          public void checkClientTrusted(X509Certificate[] certs, String authType) {}
          public void checkServerTrusted(X509Certificate[] certs, String authType) {}
      }};

      // Ignore differences between given hostname and certificate hostname
      hv = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) { return true; }
      };

      try {
        wc = SSLContext.getInstance("TLS");
        wc.init(null, trustAllCerts, new SecureRandom());
      } catch (Exception ex) { }

    }

    public static SSLContext getSSLContext() {
        return wc;
    }
    


}
