package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Enable/Disable Web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 17:28
 */
public class ToggleHook extends AbstractManipulationRequest<Hook> {

    public ToggleHook(Long id, Boolean active) {
        super(new Hook(id, active));
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.UpdateHook.format(entity.getId().toString());
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdateHook.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
