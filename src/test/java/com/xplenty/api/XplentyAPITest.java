/**
 * 
 */
package com.xplenty.api;

import org.junit.Test;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;

import junit.framework.TestCase;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyAPITest extends TestCase {

	@Test
	public void testConstructor() {
		XplentyAPI api = new XplentyAPI("testAcc", "testKey");
		
		assertNotNull(api);
		assertEquals("testAcc", api.getAccountName());
		assertEquals("testKey", api.getApiKey());
	}
	
	@Test
	public void testBuilder() {
		XplentyAPI api = new XplentyAPI("testAcc", "testKey").withHost("www.example.com")
																.withProtocol(Xplenty.Protocol.Http)
																.withVersion(Xplenty.Version.V1);
		
		assertNotNull(api);
		assertEquals("www.example.com", api.getHost());
		assertEquals(Xplenty.Protocol.Http, api.getProtocol());
		assertEquals(Xplenty.Version.V1, api.getVersion());
	}
}
