package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Enable/Disable Web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 17:28
 */
public class ToggleWebHook extends AbstractManipulationRequest<WebHook> {

    public ToggleWebHook(Long id, Boolean active) {
        super(new WebHook(id, active));
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.UpdateWebHook.format(entity.getId().toString());
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdateWebHook.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
