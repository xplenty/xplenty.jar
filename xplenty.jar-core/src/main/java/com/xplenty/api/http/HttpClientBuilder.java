package com.xplenty.api.http;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;

/**
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


    public HttpClientBuilder withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public HttpClientBuilder withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public HttpClientBuilder withProtocol(Http.Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public HttpClientBuilder withLogHttpCommunication(boolean logHttpCommunication) {
        this.logHttpCommunication = logHttpCommunication;
        return this;
    }

    public HttpClientBuilder withAccount(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public HttpClientBuilder withHost(String host) {
        this.host = host;
        return this;
    }

    public HttpClientBuilder withClientImpl(Http.HttpClientImpl impl) {
        this.clientImpl = impl;
        return this;
    }

    public HttpClientBuilder withVersion(Xplenty.Version version) {
        this.version = version;
        return this;
    }

    public HttpClient build() throws XplentyAPIException {
        if (apiKey == null) {
            throw new XplentyAPIException("Api Key not set!");
        }
        if (accountName == null) {
            throw new XplentyAPIException("Account name not set!");
        }
        switch (clientImpl) {
            case SyncNetty:
                return new SyncNettyClient(accountName, apiKey, host, protocol, timeout, logHttpCommunication);
            default:
                return new JerseyClient(accountName, apiKey, host, protocol, timeout, logHttpCommunication);
        }
    }

}
