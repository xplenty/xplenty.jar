/**
 *
 */
package com.xplenty.api;

import com.xplenty.api.Xplenty.ClusterType;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.HttpClient;
import com.xplenty.api.http.HttpClientBuilder;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.Request;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.joda.time.DateTime;

import java.util.HashMap;

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
        HttpClientBuilder builder = new HttpClientBuilder().withAccount("testAcc").withApiKey("testKey").
                withHost("www.example.com").withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V1).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
        XplentyAPI api = new XplentyAPI(builder);

        assertNotNull(api);
        assertEquals("www.example.com", api.getHost());
        assertEquals(Http.Protocol.Http, api.getProtocol());
        assertEquals(Xplenty.Version.V1, api.getVersion());
        assertEquals("testAcc", api.getAccountName());
        assertEquals(10, api.getTimeout());


    }

    public void testListClusters() {
        HttpClient client = new HttpClient() {
            @Override
            public <T> T execute(Request<T> xplentyRequest) throws XplentyAPIException {
                Response mockedResponse = Response.forContentType(Http.MediaType.JSON,
                        "[{\"nodes\":\"1\", " +
                                "\"type\": \"sandbox\", " +
                                "\"available_since\":\"2000-01-01T00:00:00Z\"," +
                                "\"terminated_at\":\"2000-01-01T00:00:00Z\", " +
                                "\"nodes\":\"1\","+
                                "\"type\":\"sandbox\"," +
                                "\"available_since\":\"2000-01-01T02:00:00Z\"," +
                                "\"terminated_at\":\"2000-01-01T02:00:00Z\"}]",
                        200, new HashMap<String, String>());

                return xplentyRequest.getResponse(mockedResponse);
            }

            @Override
            public void shutdown() {

            }

            @Override
            public String getAccountName() {
                return null;
            }

            @Override
            public String getApiKey() {
                return null;
            }

            @Override
            public String getHost() {
                return null;
            }

            @Override
            public Http.Protocol getProtocol() {
                return null;
            }

            @Override
            public Xplenty.Version getVersion() {
                return null;
            }

            @Override
            public int getTimeout() {
                return 0;
            }

        };
        XplentyAPI api = new XplentyAPI(client);
        Cluster res = api.listClusters().get(0);
        Assert.assertEquals(res.getNodes(), new Integer(1));
        Assert.assertEquals(res.getType(), ClusterType.sandbox);
        Assert.assertEquals(res.getAvailableSince(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
        Assert.assertEquals(res.getTerminatedAt(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
    }

    public void testListJobs() {
        HttpClient client = new HttpClient() {
            @Override
            public <T> T execute(Request<T> xplentyRequest) throws XplentyAPIException {
                Response mockedResponse = Response.forContentType(Http.MediaType.JSON,
                        "[{\"failed_at\":\"2000-01-01T02:00:00Z\"," +
                                "\"completed_at\":\"2000-01-01T02:00:00Z\"}]",
                        200, new HashMap<String, String>());
                return xplentyRequest.getResponse(mockedResponse);
            }

            @Override
            public void shutdown() {

            }

            @Override
            public String getAccountName() {
                return null;
            }

            @Override
            public String getApiKey() {
                return null;
            }

            @Override
            public String getHost() {
                return null;
            }

            @Override
            public Http.Protocol getProtocol() {
                return null;
            }

            @Override
            public Xplenty.Version getVersion() {
                return null;
            }

            @Override
            public int getTimeout() {
                return 0;
            }
        };
        XplentyAPI api = new XplentyAPI(client);
        Job res = api.listJobs().get(0);
        Assert.assertEquals(res.getFailedAt(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
        Assert.assertEquals(res.getCompletedAt(), new DateTime(2000, 1, 1, 2, 0, 0).toDate());
    }
}