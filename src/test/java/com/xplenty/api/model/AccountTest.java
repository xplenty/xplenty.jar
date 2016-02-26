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
public class AccountTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Account account = createMockAccount(now);
        assertNotNull(account);
        assertEquals(now.getTime(), account.getCreatedAt().getTime());
        account = createMockAccountForCreation();
        assertNotNull(account);
    }

    public static Account createMockAccountForCreation() {
        Account account = new Account("test", "gcloud::europe-west", "superunique");
        return account;
    }

    public static Account createMockAccount(Date now) {
        Account account = new Account();
        account.id = 666L;
        account.name = "test";
        account.accountId = "superunique";
        account.region = "gcloud::europe-west";
        account.avatarUrl = "https://secure.gravatar.com";
        account.billingEmail = "xardazz@github.com";
        account.gravatarEmail = "gravatar@gravatar.com";
        account.connectionsCount = 123;
        account.jobsCount = 1234;
        account.membersCount = 12345;
        account.packagesCount = 123456;
        account.runningJobsCount = 1234567;
        account.schedulesCount = 12345678;
        account.hooksCount = 123456789;
        account.publicKey = "ssh-rsa AAAAAAA....AAAAAA Xplenty/superunique";
        account.createdAt = now;
        account.updatedAt = now;
        account.ownerId = 111L;
        account.location = "Private Drive";
        account.uname = "u_666";
        account.role = Xplenty.AccountRole.admin;
        account.url = "https://testapi.xplenty.com/accounts/superunique";
        return account;
    }
}
