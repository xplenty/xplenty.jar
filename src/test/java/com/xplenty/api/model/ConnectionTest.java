package com.xplenty.api.model;

import com.xplenty.api.Xplenty;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:49
 */
public class ConnectionTest extends TestCase {

    @Test
    public void testBuilder() {
        final Date now = new Date();
        Connection connection = createMockConnection(now);
        assertNotNull(connection);
        assertEquals(now.getTime(), connection.getCreatedAt().getTime());
    }

    public static Connection createMockConnection(Date now) {
        Connection connection = new Connection();
        connection.id = 666L;
        connection.name = "test";
        connection.uniqueId = "MYSQL_CONNECTION_666";
        connection.type = Xplenty.ConnectionType.mysql;
        connection.createdAt = now;
        connection.updatedAt = now;
        connection.url = "https://testapi.xplenty.com/api/connections/mysql/666";
        return connection;
    }
}
