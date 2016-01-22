package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Get web hook information
 * Author: Xardas
 * Date: 05.01.16
 * Time: 18:26
 */
public class WebHookInfo extends AbstractInfoRequest<WebHook> {

    public WebHookInfo(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.WebHook.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.WebHook.name;
    }
}
