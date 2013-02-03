/**
 * 
 */
package com.xplenty.api.model;

import org.junit.Test;

import com.xplenty.api.XplentyAPI;

import junit.framework.TestCase;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyObjectTest extends TestCase {
	@Test
	public void testParentApiInstance() {
		Cluster c = new Cluster().withParentApiInstance(new XplentyAPI("testAcc", "testKey"));
		assertNotNull(c.getParentApiInstance());
		
	}
	
	@Test
	public void testInheritanceTypeChecking() {
		try {
			@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
			XplentyObject obj = new XplentyObject(String.class) {};
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}
