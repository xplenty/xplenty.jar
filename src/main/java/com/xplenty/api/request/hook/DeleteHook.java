package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete hook
 * Author: Xardas
 * Date: 18.12.15
 * Time: 20:25
 */
public class DeleteHook extends AbstractDeleteRequest<Hook> {

    public DeleteHook(long entityId) {
        super(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeleteHook.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.DeleteHook.format(String.valueOf(entityId));
    }
}
