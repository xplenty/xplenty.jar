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
}
