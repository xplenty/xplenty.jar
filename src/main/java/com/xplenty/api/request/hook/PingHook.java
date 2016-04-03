package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Ping (fire test notification) hook and return hook info
 * Author: Xardas
 * Date: 05.01.16
 * Time: 18:19
 */
public class PingHook extends AbstractInfoRequest<Hook> {
    public PingHook(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.PingHook.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PingHook.name;
    }
}
