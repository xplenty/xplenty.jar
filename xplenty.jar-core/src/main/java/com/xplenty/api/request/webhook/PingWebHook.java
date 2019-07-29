package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Ping (fire test notification) web hook and return web hook info
 * Author: Xardas
 * Date: 05.01.16
 * Time: 18:19
 */
public class PingWebHook extends AbstractInfoRequest<WebHook> {
    public PingWebHook(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.PingWebHook.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PingWebHook.name;
    }
}
