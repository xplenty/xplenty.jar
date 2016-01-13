package com.xplenty.api.request.connection;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Connection;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Get connection information
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:23
 */
public class ConnectionInfo extends AbstractInfoRequest<Connection> {
    private final Xplenty.ConnectionType type;

    public ConnectionInfo(long entityId, Xplenty.ConnectionType type) {
        super(entityId);
        this.type = type;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.Connection.format(type.toString(), String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Connection.name;
    }
}
