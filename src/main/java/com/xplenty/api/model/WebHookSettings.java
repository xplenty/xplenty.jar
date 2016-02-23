package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

/**
 * Data model for Xplenty Web Hook settings
 * Author: Xardas
 * Date: 04.01.16
 * Time: 17:52
 */
public class WebHookSettings implements HookSettings {
    @JsonProperty
    private String url;
    @JsonProperty("insecure_ssl")
    private Boolean insecureSSL;
    @JsonProperty("basic_auth")
    private Boolean basicAuth;
    @JsonProperty("basic_auth_data")
    private String basicAuthData;
    @JsonProperty("encrypted_basic_auth_data")
    protected String encryptedBasicAuthData;

    public WebHookSettings() {
    }

    public WebHookSettings(String url, Boolean insecureSSL, Boolean basicAuth, String basicAuthData) {
        this.url = url;
        this.insecureSSL = insecureSSL;
        this.basicAuth = basicAuth;
        this.basicAuthData = basicAuthData;
    }

    /**
     *
     * @return URL of the target server
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @return indicates whether SSL certificate is verified
     */
    public Boolean getInsecureSSL() {
        return insecureSSL;
    }

    /**
     *
     * @return indicates whether the basic authentication is required
     */
    public Boolean getBasicAuth() {
        return basicAuth;
    }

    /**
     *
     * @return data needed for basic authentication (user:password encoded with base64)
     */
    public String getBasicAuthData() {
        return basicAuthData;
    }

    /**
     *
     * @return server encrypted data needed for basic authentication
     */
    public String getEncryptedBasicAuthData() {
        return encryptedBasicAuthData;
    }

    /**
     *
     * @param url URL of the target server
     */
    public WebHookSettings withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     *
     * @param insecureSSL indicates whether SSL certificate is verified
     */
    public WebHookSettings withInsecureSSL(Boolean insecureSSL) {
        this.insecureSSL = insecureSSL;
        return this;
    }

    /**
     *
     * @param basicAuth indicates whether the basic authentication is required
     */
    public WebHookSettings withBasicAuth(Boolean basicAuth) {
        this.basicAuth = basicAuth;
        return this;
    }

    /**
     *
     * @param basicAuthData data needed for basic authentication (user:password encoded with base64)
     */
    public WebHookSettings withBasicAuthData(String basicAuthData) {
        this.basicAuthData = basicAuthData;
        return this;
    }

    @Override
    public Xplenty.HookType getType() {
        return Xplenty.HookType.web;
    }
}
