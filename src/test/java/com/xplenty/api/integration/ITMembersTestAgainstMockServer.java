package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Member;
import com.xplenty.api.request.member.ListMembers;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

public class ITMembersTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/members";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";
    private final Long entityId = 666L;

    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testCreateMember() throws ParseException {
        Member c = api.createMember("test@xplenty.com", Xplenty.AccountRole.admin, "member1");
        checkEntity(c, Xplenty.AccountRole.admin);
    }

    public void testMemberInfo() throws ParseException {
        Member c = api.getMemberInfo(entityId);
        checkEntity(c, Xplenty.AccountRole.admin);
    }

    public void testDeleteMember() throws ParseException {
        final int memberId = 666;
        Member c = api.deleteMember(memberId);
        checkEntity(c, Xplenty.AccountRole.admin);
    }

    public void testSetMemberRole() throws ParseException {
        Member c = api.setMemberRole(entityId, Xplenty.AccountRole.member);
        checkEntity(c, Xplenty.AccountRole.member);
    }

    public void testListMembers() throws ParseException {
        List<Member> list = api.listMembers();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Member c = list.get(0);
        checkEntity(c, Xplenty.AccountRole.admin);

        list = api.listMembers(5, 89);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        c = list.get(0);
        checkEntity(c, Xplenty.AccountRole.admin);

        Properties props = new Properties();
        props.put(ListMembers.PARAMETER_EMAIL, "test@test.com");
        props.put(ListMembers.PARAMETER_ROLE, Xplenty.AccountRole.member);
        list = api.listMembers(props);
        assertNotNull(list);
        assertTrue(list.size() > 0);
        c = list.get(0);
        checkEntity(c, Xplenty.AccountRole.admin);
    }

    private void checkEntity(Member c, Xplenty.AccountRole expected) throws ParseException {
        assertNotNull(c);
        assertEquals(entityId, c.getId());
        assertEquals("member1", c.getName());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@xplenty.com", c.getGravatarEmail());
        assertEquals(String.format("https://localhost/%s/settings/members/%s", accountID, entityId), c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/", c.getAvatarUrl());
        assertTrue(c.getConfirmed());
        assertEquals(expected, c.getRole());
        assertEquals(String.format("https://localhost/%s/api/members/%s", accountID, entityId), c.getUrl());
        assertEquals("Moscow", c.getLocation());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getConfirmedAt());
        assertFalse(c.getOwner());
    }
}
