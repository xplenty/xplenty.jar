package com.xplenty.api.http;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;

/**
 * Builder for Http client
 * Author: Xardas
 * Date: 02.01.16
 * Time: 15:32
 */
public class HttpClientBuilder {

    private int timeout = SyncNettyClient.DEFAULT_TIMEOUT;
    private String host = "api.xplenty.com";
    private String apiKey;
    private Http.Protocol protocol = Http.Protocol.Https;
    private Http.HttpClientImpl clientImpl = Http.HttpClientImpl.Jersey;
    private boolean logHttpCommunication = false;
    private String accountName;
    private Xplenty.Version version = Xplenty.Version.V1;

    /**
     *
     * @param timeout timeout for connection/reading
     * @return builder
     */
    public HttpClientBuilder withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     *
     * @param apiKey User's API key found at https://www.xplenty.com/settings/edit
     * @return builder
     */
    public HttpClientBuilder withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    /**
     *
     * @param protocol protocol to use for connection
     * @return builder
     */
    public HttpClientBuilder withProtocol(Http.Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     *
     * @param logHttpCommunication Log raw packets transfered? (before encryption / after decryption)
     * @return builder
     */
    public HttpClientBuilder withLogHttpCommunication(boolean logHttpCommunication) {
        this.logHttpCommunication = logHttpCommunication;
        return this;
    }

    /**
     *
     * @param accountName account name used for Xplenty sign-up
     * @return builder
     */
    public HttpClientBuilder withAccount(String accountName) {
        this.accountName = accountName;
        return this;
    }

    /**
     *
     * @param host API hostname
     * @return builder
     */
    public HttpClientBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    /**
     *
     * @param impl client implementation to use
     * @return builder
     */
    public HttpClientBuilder withClientImpl(Http.HttpClientImpl impl) {
        this.clientImpl = impl;
        return this;
    }

    /**
     *
     * @param version API version to use
     * @return builder
     */
    public HttpClientBuilder withVersion(Xplenty.Version version) {
        this.version = version;
        return this;
    }

    /**
     * Creates configured Http client
     * @return Configured Http Client
     * @throws XplentyAPIException if API KEY or Account Name is missing
     */
    public HttpClient build() throws XplentyAPIException {
        if (apiKey == null) {
            throw new XplentyAPIException("Api Key not set!");
        }
        if (accountName == null) {
            throw new XplentyAPIException("Account name not set!");
        }
        switch (clientImpl) {
            case SyncNetty:
                return new SyncNettyClient(accountName, apiKey, host, protocol, version, timeout, logHttpCommunication);
            default:
                return new JerseyClient(accountName, apiKey, host, protocol, version, timeout, logHttpCommunication);
        }
    }

}
