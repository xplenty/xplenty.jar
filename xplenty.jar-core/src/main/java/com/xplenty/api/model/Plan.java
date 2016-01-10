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
    protected String id;
    @JsonProperty
    protected String name;
    @JsonProperty
    protected String description;
    @JsonProperty("price_cents")
    protected Long priceCents;
    @JsonProperty("price_currency")
    protected String priceCurrency;
    @JsonProperty("price_unit")
    protected Xplenty.PriceUnit priceUnit;
    @JsonProperty("cluster_node_hours_included")
    protected Integer clusterNodeHoursIncluded;
    @JsonProperty("cluster_node_hours_limit")
    protected Integer clusterNodeHoursLimit;
    @JsonProperty("cluster_node_price_cents")
    protected Long clusterNodePriceCents;
    @JsonProperty("cluster_node_price_currency")
    protected String clusterNodePriceCurrency;
    @JsonProperty("cluster_node_price_unit")
    protected Xplenty.PriceUnit clusterNodePriceUnit;
    @JsonProperty("cluster_nodes_limit")
    protected Integer clusterNodesLimit;
    @JsonProperty("cluster_size_limit")
    protected Integer clusterSizeLimit;
    @JsonProperty("clusters_limit")
    protected Integer clustersLimit;
    @JsonProperty("sandbox_clusters_limit")
    protected Integer sandboxClustersLimit;
    @JsonProperty("sandbox_node_hours_included")
    protected Integer sandboxNodeHoursIncluded;
    @JsonProperty("sandbox_node_hours_limit")
    protected Integer sandboxNodeHoursLimit;
    @JsonProperty("members_limit")
    protected Integer membersLimit;
    @JsonProperty
    protected Integer position;
    @JsonProperty("created_at")
    protected Date createdAt;
    @JsonProperty("updated_at")
    protected Date updatedAt;

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
