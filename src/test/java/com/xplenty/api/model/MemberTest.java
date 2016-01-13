package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 06.01.16
 * Time: 22:07
 */
public class MemberTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Member member = createMockMember(now);
        assertNotNull(member);
        assertEquals(now.getTime(), member.getCreatedAt().getTime());
        member = createMockMemberForCreation();
        assertNotNull(member);
    }

    public static Member createMockMemberForCreation() {
        Member mem = new Member("xardazz@github.com", Xplenty.AccountRole.admin, "testuser");
        return mem;
    }

    public static Member createMockMember(Date now) {
        Member mem = new Member();
        mem.id = 666L;
        mem.email = "xardazz@github.com";
        mem.name = "test";
        mem.createdAt = now;
        mem.updatedAt = now;
        mem.url = "https://testapi.xplenty.com/api/members/666";
        mem.avatarUrl = "https://secure.gravatar.com/avatar";
        mem.confirmed = true;
        mem.confirmedAt = now;
        mem.gravatarEmail = "gravatar@gravatar.com";
        mem.location = "Colorado";
        mem.role = Xplenty.AccountRole.admin;
        mem.owner = true;
        mem.htmlUrl = "https://testapi.xplenty.com/settings/members/666";
        return mem;
    }
}
