package com.xplenty.api.http;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.InBoundHeaders;
import com.xplenty.api.Xplenty;
import com.xplenty.api.request.user.CurrentUserInfo;
import junit.framework.TestCase;
import org.jboss.netty.handler.codec.http.HttpHeaders;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 24.01.16
 * Time: 9:36
 */
public class JerseyClientTest extends TestCase {
    public void testBuilder() {
        JerseyClient client = new JerseyClient("a", "b", "c", Http.Protocol.Https, Xplenty.Version.V2, 10, true);
        assertEquals("a", client.getAccountName());
        assertEquals("b", client.getApiKey());
        assertEquals("c", client.getHost());
        assertEquals(Http.Protocol.Https, client.getProtocol());
        assertEquals(Xplenty.Version.V2, client.getVersion());
        assertEquals(10, client.getTimeout());
    }

    public void testJerseyClientInnerMethods() throws Exception {
        JerseyClient client = new JerseyClient("a", "b", "c", Http.Protocol.Https, Xplenty.Version.V2, 10, true);
        MultivaluedMap<String, String> jerseyHeaders = new InBoundHeaders();
        jerseyHeaders.add(HttpHeaders.Names.ACCEPT_CHARSET, "utf8");
        jerseyHeaders.add(HttpHeaders.Names.CONTENT_TYPE, "json");
        Map<String, String> headers = client.convertJerseyHeaders(jerseyHeaders);
        assertNotNull(headers);
        assertEquals(2, headers.size());
        assertEquals("utf8", headers.get(HttpHeaders.Names.ACCEPT_CHARSET));
        assertEquals("json", headers.get(HttpHeaders.Names.CONTENT_TYPE));

        assertEquals("https://a/b", client.getMethodURL("a/b"));
        assertEquals("http://a/b", client.getMethodURL("http://a/b"));

        CurrentUserInfo userinfo = new CurrentUserInfo(null);
        WebResource.Builder jerseyBuilder = client.getConfiguredResource(userinfo);
        assertNotNull(jerseyBuilder);

        client.shutdown();
    }

}
