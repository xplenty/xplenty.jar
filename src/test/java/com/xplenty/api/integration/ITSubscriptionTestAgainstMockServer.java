package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.CreditCardInfo;
import com.xplenty.api.model.Plan;
import com.xplenty.api.model.Subscription;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ITSubscriptionTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/subscription";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private Long entityId = 666L;


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testListPlans() throws Exception {
        List<Plan> list = api.listPlans();

        assertNotNull(list);
        assertTrue(list.size() > 0);

        Plan c = list.get(0);
        checkEntity(c);
    }

    public void testGetPaymentMethodInfo() throws Exception {
        CreditCardInfo c = api.getPaymentMethodInfo();
        checkCreditCardInfo(c);
    }

    public void testGetSubscriptionInfo() throws Exception {
        Subscription c = api.getSubscriptionInfo();
        checkSubscription(c);
    }

    public void testUpdatePaymentAndPlan() throws Exception {
        CreditCardInfo c = api.updatePaymentAndPlan("supersecrettoken", "shai-shy");
        checkCreditCardInfo(c);
    }

    private void checkEntity(Plan c) throws ParseException {
        assertNotNull(c);
        assertEquals("Expert", c.getName());
        assertEquals("Expert", c.getDescription());
        assertEquals("USD", c.getPriceCurrency());
        assertEquals("USD", c.getClusterNodePriceCurrency());
        assertEquals(Xplenty.PriceUnit.month, c.getPriceUnit());
        assertEquals(Xplenty.PriceUnit.hour, c.getClusterNodePriceUnit());
        assertEquals(199900, c.getPriceCents().longValue());
        assertEquals(71, c.getClusterNodePriceCents().longValue());
        assertEquals(0, c.getClusterNodeHoursIncluded().intValue());
        assertEquals(-1, c.getClusterNodeHoursLimit().intValue());
        assertEquals(32, c.getClusterNodesLimit().intValue());
        assertEquals(0, c.getClusterSizeLimit().intValue());
        assertEquals(4096, c.getClustersLimit().intValue());
        assertEquals(1, c.getSandboxClustersLimit().intValue());
        assertEquals(0, c.getSandboxNodeHoursIncluded().intValue());
        assertEquals(-1, c.getSandboxNodeHoursLimit().intValue());
        assertEquals(4096, c.getMembersLimit().intValue());
        assertEquals(7, c.getPosition().intValue());
        assertEquals(dFormat.parse("2016-02-26T04:51:47Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-02-26T04:51:47Z"), c.getUpdatedAt());
        assertEquals("expert--7", c.getId());
    }

    private void checkCreditCardInfo(CreditCardInfo c) {
        assertNotNull(c);
        assertEquals("https://testapi.xplenty.com/api/payment_method", c.getUrl());
        assertEquals(9876, c.getCardLast4().intValue());
        assertEquals("xxxx-xxxx-xxxx-9876", c.getCardNumber());
        assertEquals("06/66", c.getExpirationDate());
        assertEquals("MasterCard", c.getCardType());
    }

    private void checkSubscription(Subscription c) throws ParseException {
        assertNotNull(c);
        assertEquals(14, c.getTrialPeriodDays().longValue());
        assertEquals("shai-shy", c.getPlanId());
        assertEquals(dFormat.parse("2015-11-05T15:12:00Z"), c.getTrialStart());
        assertEquals(dFormat.parse("2015-11-19T15:12:00Z"), c.getTrialEnd());
        assertEquals("https://xplenty.com/xardazz/api/subscription", c.getUrl());
        assertFalse(c.isTrial());
    }
}
