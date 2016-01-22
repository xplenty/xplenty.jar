/**
 * 
 */
package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.Xplenty.ClusterType;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterTest extends TestCase {
	@Test
	public void testBuilder() {
		Cluster c = new Cluster().withDescription("description")
                .named("my cluster")
                .withNodes(2)
                .withAllowFallback(true);
		
		assertEquals("description", c.getDescription());
		assertEquals("my cluster", c.getName());
		assertEquals(new Integer(2), c.getNodes());
        assertTrue(c.getAllowFallback());
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
        c.allowFallback = true;
        c.zone = "eu-west1";
        c.idleSince = now;
        c.htmlUrl = "https://www.xplenty.com/clusters/3";
        c.masterInstanceType = "sometype";
        c.masterSpotPercentage = 0.3;
        c.masterSpotPrice = 42.42;
        c.slaveInstanceType = "sometype1";
        c.slaveSpotPercentage = 0.4;
        c.slaveSpotPrice = 12.12;
        c.region = "eu-west1-amazon:1";
        c.planId = "month-10";
        c.stack = "pinky-everest";

        List<String> argList = new ArrayList<>();
        argList.add("arg1");
        argList.add("some more");
        ClusterBootstrapAction act = new ClusterBootstrapAction("http://xplenty.s3.amazonaws.com/bootstrap-actions/script_path", argList);
        List<ClusterBootstrapAction> cbas = new ArrayList<>();
        cbas.add(act);
        c.bootstrapActions = cbas;
        Creator creator = new Creator();
        creator.displayName = "xardazz";
        creator.htmlUrl = "https://www.xplenty.com/user/3";
        creator.url = "https://www.xplenty.com/api/user/3";
        creator.type = "User";
        creator.id = 666L;
        c.creator = creator;
		return c;
	}

    public static ClusterInstance createMockClusterInstance() {
        ClusterInstance ci = new ClusterInstance();
        ci.instanceId = "i-4d1b39a7";
        ci.privateDns = "ip-10-124-29-23.ec2.internal";
        ci.publicDns = "ec2-55-27-210-201.compute-1.amazonaws.com";
        ci.status = Xplenty.ClusterInstanceStatus.available;
        ci.master = true;
        ci.spot = false;
        ci.vpc = false;
        ci.instanceType = "sometype";
        ci.zone ="eu-west1";
        ci.url = "https://testapi.xplenty.com/api/clusters/5/instances/i-4d1b39a7";
        return ci;
    }
}
