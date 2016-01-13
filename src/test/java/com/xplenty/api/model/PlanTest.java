package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:39
 */
public class PlanTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Plan plan = createMockPlan(now);
        assertNotNull(plan);
        assertEquals(now.getTime(), plan.getCreatedAt().getTime());
    }


    public static Plan createMockPlan(Date now) {
        Plan plan = new Plan();
        plan.name = "Expert";
        plan.description = "Expert";
        plan.priceCents = 199900L;
        plan.priceCurrency = "USD";
        plan.priceUnit = Xplenty.PriceUnit.month;
        plan.clusterNodeHoursIncluded = 0;
        plan.clusterNodeHoursLimit = -1;
        plan.clusterNodePriceCents = 71L;
        plan.clusterNodePriceCurrency = "USD";
        plan.clusterNodePriceUnit = Xplenty.PriceUnit.hour;
        plan.clusterNodesLimit = 32;
        plan.clusterSizeLimit = 0;
        plan.clustersLimit = 4096;
        plan.sandboxClustersLimit = 1;
        plan.sandboxNodeHoursIncluded = 0;
        plan.sandboxNodeHoursLimit = -1;
        plan.membersLimit = 4096;
        plan.position = 7;
        plan.createdAt = now;
        plan.updatedAt = now;
        plan.id = "expert--7";
        return plan;
    }
}
