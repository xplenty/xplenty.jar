package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Xardas
 * Date: 09.01.16
 * Time: 16:55
 */
public class MiscTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        Timezone timezone = createMockTimeZone();
        assertNotNull(timezone);
        assertEquals("St. Petersburg", timezone.getId());

        Stack stack = createMockStack();
        assertNotNull(stack);
        assertEquals("blue-everest", stack.getId());

        Map<String, String> systemVars = createMockSystemVars();
        assertNotNull(systemVars);
        assertTrue(systemVars.size() > 0);

        ProductUpdate pu = createMockProductUpdate(now);
        assertNotNull(pu);
        assertEquals(now.getTime(), pu.getCreatedAt().getTime());
    }

    public static Timezone createMockTimeZone() {
        Timezone timezone = new Timezone();
        timezone.id = "St. Petersburg";
        timezone.name = "(GMT+03:00) St. Petersburg";
        return timezone;
    }

    public static Stack createMockStack() {
        Stack stack = new Stack();
        stack.id = "blue-everest";
        stack.name = "Blue Everest";
        return stack;
    }

    public static Map<String, String> createMockSystemVars() {
        Map<String, String> systemVars = new HashMap<>();
        systemVars.put("_MAX_COMBINED_SPLIT_SIZE", "777777");
        systemVars.put("_BYTES_PER_REDUCER", "666666");
        return systemVars;
    }

    public static ProductUpdate createMockProductUpdate(Date now) {
        ProductUpdate pu = new ProductUpdate();
        pu.id = 666L;
        pu.title = "Breaking news";
        pu.body = "Now you can cross-join!";
        pu.bodyHtml = "<b>Now you can cross-join</b>";
        pu.bodyText = "now you can cross-join\n";
        pu.createdAt = now;
        pu.likes = 15L;
        pu.liked = true;
        return pu;
    }
}
