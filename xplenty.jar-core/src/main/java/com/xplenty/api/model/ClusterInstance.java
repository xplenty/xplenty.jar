package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

/**
 * Data model for cluster instance
 * An Xplenty cluster instance is a machine (node) that were allocated for a given cluster.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 19:41
 */
public class ClusterInstance extends XplentyObject<ClusterInstance> {
    @JsonProperty("instance_id")
    protected String instanceId;
    @JsonProperty("private_dns")
    protected String privateDns;
    @JsonProperty("public_dns")
    protected String publicDns;
    @JsonProperty
    protected Xplenty.ClusterInstanceStatus status;
    @JsonProperty
    protected Boolean master;
    @JsonProperty
    protected Boolean spot;
    @JsonProperty
    protected Boolean vpc;
    @JsonProperty
    protected String zone;
    @JsonProperty("instance_type")
    protected String instanceType;
    @JsonProperty
    protected String url;

    protected ClusterInstance() {
        super(ClusterInstance.class);
    }


    /**
     * @return the provider specific identifier of the instance
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     * @return the private fully qualified DNS of the instance
     */
    public String getPrivateDns() {
        return privateDns;
    }

    /**
     * @return the public fully qualified DNS of the instance
     */
    public String getPublicDns() {
        return publicDns;
    }

    /**
     * @return the instance's status. Possible values are:
     *  available - the instance is available
     *  terminated - the instance is no longer accessible
     */
    public Xplenty.ClusterInstanceStatus getStatus() {
        return status;
    }

    /**
     * @return indicates whether the instance is the master of the cluster
     */
    public Boolean getMaster() {
        return master;
    }

    /**
     * @return indicates whether the instance is a spot instance
     */
    public Boolean getSpot() {
        return spot;
    }

    /**
     * @return indicates whether the instance is part of a vpc
     */
    public Boolean getVpc() {
        return vpc;
    }

    /**
     * @return the provider specific zone the instance was provisioned at. Returns 'Unsupported' for regions with no support for zones.
     */
    public String getZone() {
        return zone;
    }

    /**
     * @return the instance type
     */
    public String getInstanceType() {
        return instanceType;
    }

    /**
     *
     * @return API resource url
     */
    public String getUrl() {
        return url;
    }
}
