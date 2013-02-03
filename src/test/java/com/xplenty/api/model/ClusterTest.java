/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterTest extends TestCase {
	@Test
	public void testBuilder() {
		Cluster c = new Cluster().withDescription("description")
									.named("my cluster")
									.onPlan(1);
		
		assertEquals("description", c.getDescription());
		assertEquals("my cluster", c.getName());
		assertEquals("description", c.getDescription());
		assertEquals(new Long(1), c.getPlanId());
	}
	
	public static Cluster createMockCluster(Date now) {
		Cluster c = new Cluster();
		c.createdAt = now;
		c.updatedAt = now;
		c.name = "my cluster";
		c.description = "description";
		c.ownerId = (long)1;
		c.planId = (long)2;
		c.id = (long)3;
		c.runningJobsCount = (long)0;
		c.status = ClusterStatus.available;
		c.url = "https://www.xplenty.com/api/" + Xplenty.Resource.Cluster.format(Long.toString(3));
		return c;
	}
}
