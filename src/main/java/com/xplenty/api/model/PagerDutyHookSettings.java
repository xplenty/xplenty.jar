package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

/**
 * Settings specific for the PagerDuty hook. 
 * Author: Xardas
 * Date: 24.01.16
 * Time: 20:05
 */
public class PagerDutyHookSettings implements HookSettings {
    @JsonProperty("pd_account")
    private String pdAccount;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_key")
    private String serviceKey;

    public PagerDutyHookSettings() {
    }

    public PagerDutyHookSettings(String pdAccount, String serviceName, String serviceKey) {
        this.pdAccount = pdAccount;
        this.serviceName = serviceName;
        this.serviceKey = serviceKey;
    }

    /**
     *
     * @param pdAccount PagerDuty account
     * @return this instance
     */
    public PagerDutyHookSettings withPdAccount(String pdAccount) {
        this.pdAccount = pdAccount;
        return this;
    }

    /**
     *
     * @param serviceName name of the PagerDuty service
     * @return this instance
     */
    public PagerDutyHookSettings withServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    /**
     *
     * @param serviceKey key of the PagerDuty service
     * @return this instance
     */
    public PagerDutyHookSettings withServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
        return this;
    }

    /**
     * 
     * @return PagerDuty account
     */
    public String getPdAccount() {
        return pdAccount;
    }

    /**
     * 
     * @return name of the PagerDuty service
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 
     * @return key of the PagerDuty service
     */
    public String getServiceKey() {
        return serviceKey;
    }

    @Override
    public Xplenty.HookType getType() {
        return Xplenty.HookType.pagerduty;
    }
}
