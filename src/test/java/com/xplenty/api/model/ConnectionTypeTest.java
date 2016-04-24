package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:54
 */
public class ConnectionTypeTest extends TestCase {

    @Test
    public void testBuilder() {
        final Date now = new Date();
        ConnectionType connectionType = createMockConnectionType();
        assertNotNull(connectionType);
        assertEquals("MySQL", connectionType.getName());
    }

    public static ConnectionType createMockConnectionType() {
        ConnectionType connectionType = new ConnectionType();
        connectionType.name = "MySQL";
        connectionType.type = "mysql";
        connectionType.description = "The world's greatest open source database";
        connectionType.iconUrl = "https://assets.xplenty.com/assets/vendor/mysql.png";
        ConnectionTypeGroup conTypeGroup = new ConnectionTypeGroup();
        conTypeGroup.groupName = "Relational Databases";
        conTypeGroup.groupType = "relational";
        List<ConnectionTypeGroup> groupList = new ArrayList<>();
        groupList.add(conTypeGroup);
        connectionType.groups = groupList;
        return connectionType;
    }
}
