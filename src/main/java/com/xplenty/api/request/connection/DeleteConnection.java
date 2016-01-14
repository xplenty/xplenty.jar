package com.xplenty.api.request.connection;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Connection;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete an existing connection.
 * Please note that deleting the connection will invalidate all items referencing it.
 * Author: Xardas
 * Date: 08.01.16
 * Time: 17:23
 */
public class DeleteConnection extends AbstractDeleteRequest<Connection> {
    private final Xplenty.ConnectionType type;

    public DeleteConnection(long entityId, Xplenty.ConnectionType type) {
        super(entityId);
        this.type = type;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.DeleteConnection.format(type.toString(), String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeleteConnection.name;
    }
}
