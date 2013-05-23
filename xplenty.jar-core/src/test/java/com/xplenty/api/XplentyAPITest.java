/**
 *
 */
package com.xplenty.api;

import com.sun.jersey.api.client.ClientResponse;
import com.xplenty.api.model.*;
import com.xplenty.api.request.Request;
import junit.framework.Assert;
import org.joda.time.DateTime;
import com.xplenty.api.util.Http;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Yuriy Kovalek
 *
 */
public class XplentyAPITest extends TestCase {

    public void testConstructor() {
        XplentyAPI api = new XplentyAPI("testAcc", "testKey");

        assertNotNull(api);
        assertEquals("testAcc", api.getAccountName());
        assertEquals("testKey", api.getApiKey());
    }

    public void testBuilder() {
        XplentyAPI api = new XplentyAPI("testAcc", "testKey").withHost("www.example.com")
                .withProtocol(Http.Protocol.Http)
                .withVersion(Xplenty.Version.V1);

        assertNotNull(api);
        assertEquals("www.example.com", api.getHost());
        assertEquals(Http.Protocol.Http, api.getProtocol());
        assertEquals(Xplenty.Version.V1, api.getVersion());
    }

    public void testListClusters() {
        XplentyAPI api = new XplentyAPI("dontcare", "dontcare"){
            @Override
            public <T> T execute(Request<T> request) {
                ClientResponse mockedResponse = mock(ClientResponse.class);
                when(mockedResponse.getEntity(String.class)).thenReturn(
                        "[{\"nodes\":\"1\", " +
                        "\"type\": \"sandbox\", " +
                        "\"available_since\":\"2000-01-01T00:00:00Z\"," +
                        "\"terminated_at\":\"2000-01-01T00:00:00Z\", " +
                        "\"nodes\":\"1\","+
                        "\"type\":\"sandbox\"," +
                        "\"available_since\":\"2000-01-01T00:00:00Z\"," +
                        "\"terminated_at\":\"2000-01-01T00:00:00Z\"}]");

                return request.getResponse(mockedResponse);
            }
        };
        Cluster res = api.listClusters().get(0);
        Assert.assertEquals(res.getNodes(), new Integer(1));
        Assert.assertEquals(res.getType(), "sandbox");
        Assert.assertEquals(res.getAvailableSince(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
        Assert.assertEquals(res.getTerminatedAt(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
    }

    public void testListJobs() {
        XplentyAPI api = new XplentyAPI("don't care", "don't care"){
            @Override
            public <T> T execute(Request<T> request) {
                ClientResponse mockedResponse = mock(ClientResponse.class);
                when(mockedResponse.getEntity(String.class)).thenReturn(
                        "[{\"failed_at\":\"2000-01-01T00:00:00Z\"," +
                          "\"completed_at\":\"2000-01-01T00:00:00Z\"}]");
                return request.getResponse(mockedResponse);
            }
        };
        Job res = api.listJobs().get(0);
        Assert.assertEquals(res.getFailedAt(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
        Assert.assertEquals(res.getCompletedAt(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
    }
}