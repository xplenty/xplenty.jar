/**
 * 
 */
package com.xplenty.api;

import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.request.Request;
import junit.framework.Assert;
import org.junit.Test;
import com.xplenty.api.util.Http;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
																.withProtocol(Http.Protocol.Http)
																.withVersion(Xplenty.Version.V1);
		
		assertNotNull(api);
		assertEquals("www.example.com", api.getHost());
		assertEquals(Http.Protocol.Http, api.getProtocol());
		assertEquals(Xplenty.Version.V1, api.getVersion());
	}

    private String apiKey = "";
    private String accountID = "";


    @Test
    public void testListClusters() {
        XplentyAPI api = new XplentyAPI(accountID, apiKey){
            @Override
            public <T> T execute(Request<T> request) {
                ClientResponse mockedResponse = mock(ClientResponse.class);
                when(mockedResponse.getEntity(String.class)).thenReturn("[{\"name\":\"Cluster4Alice\"}, {\"name\": \"Cluster4Bob\"}]");
                return request.getResponse(mockedResponse);
            };
        };
        Cluster[] res = api.listClusters().toArray(new Cluster[2]);
        Assert.assertEquals(res[0].getName(), "Cluster4Alice");
        Assert.assertEquals(res[1].getName(), "Cluster4Bob");
    }
}
