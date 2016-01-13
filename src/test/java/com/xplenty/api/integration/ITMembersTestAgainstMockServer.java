package com.xplenty.api.integration;

import com.xplenty.api.Xplenty;
import com.xplenty.api.XplentyAPI;
import com.xplenty.api.http.ClientBuilder;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Member;
import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ITMembersTestAgainstMockServer extends TestCase {
    private final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private XplentyAPI api;
    private String host = "localhost:8080/mock/members";
    private String apiKey = "dsfgsdfh";
    private String accountID = "testerAccount";


    @Override
    public void setUp(){
        ClientBuilder builder = new ClientBuilder().withAccount(accountID).withApiKey(apiKey).
                withHost(host).withProtocol(Http.Protocol.Http).withVersion(Xplenty.Version.V2).
                withClientImpl(Http.HttpClientImpl.SyncNetty).withLogHttpCommunication(true).withTimeout(10);
         api = new XplentyAPI(builder);
    }

    public void testCreateMember() throws ParseException {
        Member c = api.createMember("test@xplenty.com", Xplenty.AccountRole.admin, "member1");
        assertNotNull(c);
        assertEquals(new Long(387), c.getId());
        assertEquals("member1", c.getName());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@xplenty.com", c.getGravatarEmail());
        assertEquals(String.format("https://localhost/%s/settings/members/387", accountID), c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/", c.getAvatarUrl());
        assertFalse(c.getConfirmed());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals(String.format("https://localhost/%s/api/members/387", accountID), c.getUrl());
        assertNull(c.getLocation());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getUpdatedAt());
        assertNull(c.getConfirmedAt());
        assertFalse(c.getOwner());
    }

    public void testMemberInfo() throws ParseException {
        final int memberId = 666;
        Member c = api.getMemberInfo(memberId);
        assertNotNull(c);
        assertEquals(new Long(memberId), c.getId());
        assertEquals("member1", c.getName());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@xplenty.com", c.getGravatarEmail());
        assertEquals(String.format("https://localhost/%s/settings/members/%s", accountID, memberId), c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/", c.getAvatarUrl());
        assertTrue(c.getConfirmed());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals(String.format("https://localhost/%s/api/members/%s", accountID, memberId), c.getUrl());
        assertEquals("Moscow", c.getLocation());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getConfirmedAt());
        assertFalse(c.getOwner());
    }

    public void testDeleteMember() throws ParseException {
        final int memberId = 666;
        Member c = api.deleteMember(memberId);
        assertNotNull(c);
        assertEquals(new Long(memberId), c.getId());
        assertEquals("member1", c.getName());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@xplenty.com", c.getGravatarEmail());
        assertEquals(String.format("https://localhost/%s/settings/members/%s", accountID, memberId), c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/", c.getAvatarUrl());
        assertTrue(c.getConfirmed());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals(String.format("https://localhost/%s/api/members/%s", accountID, memberId), c.getUrl());
        assertEquals("Moscow", c.getLocation());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getConfirmedAt());
        assertFalse(c.getOwner());
    }

    public void testSetMemberRole() throws ParseException {
        final int memberId = 666;
        Member c = api.setMemberRole(memberId, Xplenty.AccountRole.member);
        assertNotNull(c);
        assertEquals(new Long(memberId), c.getId());
        assertEquals("member1", c.getName());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@xplenty.com", c.getGravatarEmail());
        assertEquals(String.format("https://localhost/%s/settings/members/%s", accountID, memberId), c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/", c.getAvatarUrl());
        assertTrue(c.getConfirmed());
        assertEquals(Xplenty.AccountRole.member, c.getRole());
        assertEquals(String.format("https://localhost/%s/api/members/%s", accountID, memberId), c.getUrl());
        assertEquals("Moscow", c.getLocation());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getConfirmedAt());
        assertFalse(c.getOwner());
    }

    public void testListMembers() throws ParseException {
        final int memberId = 666;
        List<Member> list = api.listMembers();
        assertNotNull(list);
        assertTrue(list.size() > 0);
        Member c = list.get(0);
        assertNotNull(c);
        assertEquals(new Long(memberId), c.getId());
        assertEquals("member1", c.getName());
        assertEquals("test@xplenty.com", c.getEmail());
        assertEquals("test@xplenty.com", c.getGravatarEmail());
        assertEquals(String.format("https://localhost/%s/settings/members/%s", accountID, memberId), c.getHtmlUrl());
        assertEquals("https://secure.gravatar.com/", c.getAvatarUrl());
        assertTrue(c.getConfirmed());
        assertEquals(Xplenty.AccountRole.admin, c.getRole());
        assertEquals(String.format("https://localhost/%s/api/members/%s", accountID, memberId), c.getUrl());
        assertEquals("Moscow", c.getLocation());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getCreatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getUpdatedAt());
        assertEquals(dFormat.parse("2016-01-13T16:44:20Z"), c.getConfirmedAt());
        assertFalse(c.getOwner());
    }
}
