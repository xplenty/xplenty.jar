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
    private String instanceId;
    @JsonProperty("private_dns")
    private String privateDns;
    @JsonProperty("public_dns")
    private String publicDns;
    @JsonProperty
    private Xplenty.ClusterInstanceStatus status;
    @JsonProperty
    private Boolean master;
    @JsonProperty
    private Boolean spot;
    @JsonProperty
    private Boolean vpc;
    @JsonProperty
    private String zone;
    @JsonProperty("instance_type")
    private String instanceType;

    private ClusterInstance() {
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
    public String getpublicDns() {
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
}
