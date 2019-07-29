package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 06.01.16
 * Time: 22:07
 */
public class PublicKeyTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        PublicKey publicKey = createMockPublicKey(now);
        assertNotNull(publicKey);
        assertEquals(now.getTime(), publicKey.getCreatedAt().getTime());
        publicKey = createMockPublicKeyForCreation();
        assertNotNull(publicKey);
    }

    public static PublicKey createMockPublicKeyForCreation() {
        PublicKey pk = new PublicKey("test", "ssh-rsa AAAAB3NzaC1yc2EAAAABIwAA...AAA xardazz@github.com");
        return pk;
    }

    public static PublicKey createMockPublicKey(Date now) {
        PublicKey pk = new PublicKey();
        pk.id = 666L;
        pk.comment = "xardazz@github.com";
        pk.name = "test";
        pk.fingerprint = "ff:ff:ff:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:ff:ff:ff";
        pk.createdAt = now;
        pk.updatedAt = now;
        pk.url = "https://testapi.xplenty.com/user/keys/666";
        return pk;
    }
}
