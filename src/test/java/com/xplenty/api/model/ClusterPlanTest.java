/**
 * 
 */
package com.xplenty.api.model;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * @author Yuriy Kovalek
 *
 */
public class ClusterPlanTest extends TestCase {
	@Test
	public void testIntegrity() {
		ClusterPlan cp = new ClusterPlan();
		assertNotNull(cp);
	}
	
	public static ClusterPlan createMockClusterPlan() {
		ClusterPlan cp = new ClusterPlan();
		cp.id = (long) 1;
		cp.name = "test plan";
		return cp;
	}
}
