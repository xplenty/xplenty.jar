package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.ProductUpdate;
import com.xplenty.api.model.Region;
import com.xplenty.api.model.Stack;
import com.xplenty.api.model.Timezone;
import com.xplenty.api.request.misc.ListRegions;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ITMiscTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/misc";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 9L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testListProductUpdates() throws Exception {
        List<ProductUpdate> list = api.listProductUpdates();

        assertNotNull(list);
        assertTrue(list.size() > 0);

        ProductUpdate c = list.get(0);
        checkProductUpdate(c);
    }

    public void testLikeProductUpdate() throws Exception {
        ProductUpdate c = api.likeProductUpdate(entityId);
        checkProductUpdate(c);
    }

    public void testListRegions() throws Exception {
        List<Region> list = api.listRegions();

        assertNotNull(list);
        assertTrue(list.size() > 0);

        Region c = list.get(0);
        checkRegion(c);

        Properties props = new Properties();
        props.put(ListRegions.PARAMETER_BRAND_ID, 3);
        list = api.listRegions(props);

        assertNotNull(list);
        assertTrue(list.size() > 0);

        c = list.get(0);
        checkRegion(c);
    }

    public void testListStacks() throws Exception {
        List<Stack> list = api.listStacks();

        assertNotNull(list);
        assertTrue(list.size() > 0);

        Stack c = list.get(0);
        checkStack(c);
    }

    public void testListSystemVariables() throws Exception {
        Map<String, String> c = api.listSystemVariables();
        checkSystemVariables(c);
    }

    public void testListTimezones() throws Exception {
        List<Timezone> list = api.listTimezones();

        assertNotNull(list);
        assertTrue(list.size() > 0);

        Timezone c = list.get(0);
        checkTimezone(c);
    }


    private void checkProductUpdate(ProductUpdate c) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals("na na ", c.getTitle());
        assertEquals("momo", c.getBody());
        assertEquals("<p>momo</p>", c.getBodyHtml());
        assertEquals("momo", c.getBodyText());
        assertEquals(3, c.getLikes().intValue());
        assertFalse(c.getLiked());
        assertEquals(dFormat.parse("2014-03-27T09:40:28Z"), c.getCreatedAt());
    }

    private void checkRegion(Region c) {
        assertNotNull(c);
        assertEquals("AWS - US East (N. Virginia)", c.getName());
        assertEquals("Amazon Web Services", c.getGroupName());
        assertEquals("amazon-web-services::us-east-1", c.getId());
    }

    private void checkStack(Stack c) {
        assertNotNull(c);
        assertEquals("mint-everest", c.getId());
        assertEquals("Mint Everest", c.getName());
    }

    private void checkSystemVariables(Map<String, String> c) {
        assertNotNull(c);
        assertEquals("67108864", c.get("_MAX_COMBINED_SPLIT_SIZE"));
        assertEquals("209715200", c.get("_BYTES_PER_REDUCER"));
        assertEquals("1048576", c.get("_LINE_RECORD_READER_MAX_LENGTH"));
        assertEquals("'+00:00'", c.get("_DEFAULT_TIMEZONE"));
    }

    private void checkTimezone(Timezone c) {
        assertNotNull(c);
        assertEquals("St. Petersburg", c.getId());
        assertEquals("(GMT+03:00) St. Petersburg", c.getName());
    }
}
