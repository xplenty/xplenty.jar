package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xplenty.api.Xplenty;

import java.util.Date;

/**
 * Data model for Xplenty plan
 * An account plan defines price and limits for members, clusters, etc.
 * Author: Xardas
 * Date: 03.01.16
 * Time: 20:57
 */
public class Plan extends XplentyObject<Plan> {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty
    private String description;
    @JsonProperty("price_cents")
    private Long priceCents;
    @JsonProperty("price_currency")
    private String priceCurrency;
    @JsonProperty("price_unit")
    private Xplenty.PriceUnit priceUnit;
    @JsonProperty("cluster_node_hours_included")
    private Integer clusterNodeHoursIncluded;
    @JsonProperty("cluster_node_hours_limit")
    private Integer clusterNodeHoursLimit;
    @JsonProperty("cluster_node_price_cents")
    private Long clusterNodePriceCents;
    @JsonProperty("cluster_node_price_currency")
    private String clusterNodePriceCurrency;
    @JsonProperty("cluster_node_price_unit")
    private Xplenty.PriceUnit clusterNodePriceUnit;
    @JsonProperty("cluster_nodes_limit")
    private Integer clusterNodesLimit;
    @JsonProperty("cluster_size_limit")
    private Integer clusterSizeLimit;
    @JsonProperty("clusters_limit")
    private Integer clustersLimit;
    @JsonProperty("sandbox_clusters_limit")
    private Integer sandboxClustersLimit;
    @JsonProperty("sandbox_node_hours_included")
    private Integer sandboxNodeHoursIncluded;
    @JsonProperty("sandbox_node_hours_limit")
    private Integer sandboxNodeHoursLimit;
    @JsonProperty("members_limit")
    private Integer membersLimit;
    @JsonProperty
    private Integer position;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;

    protected Plan() {
        super(Plan.class);
    }

    /**
     * @return the plan's string identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name given to the plan
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the plan
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return
     */
    public Long getPriceCents() {
        return priceCents;
    }

    /**
     * @return the currency of the plan
     */
    public String getPriceCurrency() {
        return priceCurrency;
    }

    /**
     * @return the price's unit of time.
     */
    public Xplenty.PriceUnit getPriceUnit() {
        return priceUnit;
    }

    /**
     * @return
     */
    public Integer getClusterNodeHoursIncluded() {
        return clusterNodeHoursIncluded;
    }

    /**
     * @return
     */
    public Integer getClusterNodeHoursLimit() {
        return clusterNodeHoursLimit;
    }

    /**
     * @return
     */
    public Long getClusterNodePriceCents() {
        return clusterNodePriceCents;
    }

    /**
     * @return
     */
    public String getClusterNodePriceCurrency() {
        return clusterNodePriceCurrency;
    }

    /**
     * @return
     */
    public Xplenty.PriceUnit getClusterNodePriceUnit() {
        return clusterNodePriceUnit;
    }

    /**
     * @return
     */
    public Integer getClusterNodesLimit() {
        return clusterNodesLimit;
    }

    /**
     * @return
     */
    public Integer getClusterSizeLimit() {
        return clusterSizeLimit;
    }

    /**
     * @return
     */
    public Integer getClustersLimit() {
        return clustersLimit;
    }

    /**
     * @return
     */
    public Integer getSandboxClustersLimit() {
        return sandboxClustersLimit;
    }

    /**
     * @return
     */
    public Integer getSandboxNodeHoursIncluded() {
        return sandboxNodeHoursIncluded;
    }

    /**
     * @return
     */
    public Integer getSandboxNodeHoursLimit() {
        return sandboxNodeHoursLimit;
    }

    /**
     * @return the maximum number of members for an account
     */
    public Integer getMembersLimit() {
        return membersLimit;
    }

    /**
     * @return
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * @return the date and time the plan was created
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the date and time the plan was last updated
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }
}
