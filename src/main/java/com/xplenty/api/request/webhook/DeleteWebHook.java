package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete web hook
 * Author: Xardas
 * Date: 18.12.15
 * Time: 20:25
 */
public class DeleteWebHook extends AbstractDeleteRequest<WebHook> {

    public DeleteWebHook(long entityId) {
        super(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeleteWebHook.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.DeleteWebHook.format(String.valueOf(entityId));
    }
}
