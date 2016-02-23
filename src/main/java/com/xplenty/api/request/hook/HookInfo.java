package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Get hook information
 * Author: Xardas
 * Date: 05.01.16
 * Time: 18:26
 */
public class HookInfo extends AbstractInfoRequest<Hook> {

    public HookInfo(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.Hook.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Hook.name;
    }
}
