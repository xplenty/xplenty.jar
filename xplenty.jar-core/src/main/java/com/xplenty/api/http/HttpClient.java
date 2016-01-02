package com.xplenty.api.http;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.request.Request;

/**
 * Author: Xardas
 * Date: 02.01.16
 * Time: 15:36
 */
public interface HttpClient {
    static final int DEFAULT_TIMEOUT = 30;
    static final int DEFAULT_HTTPS_PORT = 443;
    static final int DEFAULT_HTTP_PORT = 80;
    static final String API_PATH = "api";

    <T> T execute(Request<T> xplentyRequest) throws XplentyAPIException;

    void shutdown();

    String getAccountName();

    String getApiKey();

    String getHost();

    Http.Protocol getProtocol();

    Xplenty.Version getVersion();
}
