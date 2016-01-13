package com.xplenty.api.http;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.request.Request;

/**
 * Http Client interface for Request processing
 * Author: Xardas
 * Date: 02.01.16
 * Time: 15:36
 */
public interface HttpClient {
    static final int DEFAULT_TIMEOUT = 30;
    static final int DEFAULT_HTTPS_PORT = 443;
    static final int DEFAULT_HTTP_PORT = 80;

    /**
     * Synchronously execute given request
     * @param xplentyRequest request to execute
     * @param <T> XplentyObject itself or a collection of XplentyObjects
     * @return respective response type
     * @throws XplentyAPIException if any error occurs
     */
    <T> T execute(Request<T> xplentyRequest) throws XplentyAPIException;

    /**
     * Some client implementations may create thread pool for socket handling
     * This method terminates these thread pools and frees any resources obtained by the client
     */
    void shutdown();

    /**
     *
     * @return Account name used
     */
    String getAccountName();

    /**
     *
     * @return API Key used
     */
    String getApiKey();

    /**
     *
     * @return Host used
     */
    String getHost();

    /**
     *
     * @return protocol used (usually secure https)
     */
    Http.Protocol getProtocol();

    /**
     *
     * @return Xplenty API Version
     */
    Xplenty.Version getVersion();

    /**
     *
     * @return timeout for connection/reading
     */
    int getTimeout();
}
