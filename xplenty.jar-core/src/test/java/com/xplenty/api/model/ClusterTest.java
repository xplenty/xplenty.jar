/**
 * 
 */
package com.xplenty.api.model;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.Xplenty.ClusterType;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterTest extends TestCase {
	@Test
	public void testBuilder() {
		Cluster c = new Cluster().withDescription("description")
									.named("my cluster")
									.withNodes(2);
		
		assertEquals("description", c.getDescription());
		assertEquals("my cluster", c.getName());
		assertEquals("description", c.getDescription());
		assertEquals(new Integer(2), c.getNodes());
	}
	
	public static Cluster createMockCluster(Date now) {
		Cluster c = new Cluster();
		c.createdAt = now;
		c.updatedAt = now;
		c.availableSince = now;
		c.terminatedAt = now;
		c.name = "my cluster";
		c.description = "description";
		c.ownerId = 1L;
		c.nodes = 2;
		c.type = ClusterType.production;
		c.id = 3L;
		c.runningJobsCount = 0L;
		c.status = ClusterStatus.available;
		c.url = "https://www.xplenty.com/api/" + Xplenty.Resource.Cluster.format(Long.toString(3));
		c.terminateOnIdle = true;
		c.timeToIdle = 3600L;
		c.terminatedOnIdle = false;
		return c;
	}
}
