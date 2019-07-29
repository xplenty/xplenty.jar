package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Author: Xardas
 * Date: 06.01.16
 * Time: 22:07
 */
public class RegionTest extends TestCase {
    @Test
    public void testBuilder() {
        Region region = createMockRegion();
        assertNotNull(region);
        assertEquals("gcloud", region.getGroupName());
    }


    public static Region createMockRegion() {
        Region region = new Region();
        region.id = "gcloud::europe-west";
        region.groupName = "gcloud";
        region.name = "West Europe Google Cloud";

        return region;
    }
}
