package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Hook;
import com.xplenty.api.request.AbstractRequest;

/**
 * Reset salt for hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 18:07
 */
public class HookResetSalt extends AbstractRequest<String> {
    private final long webHookId;

    public HookResetSalt(long webHookId) {
        this.webHookId = webHookId;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.HookResetSalt.format(String.valueOf(webHookId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.HookResetSalt.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public Object getBody() {
        return null;
    }

    @Override
    public String getResponse(Response response) {
        try {
            Hook webHook = response.getContent(Hook.class);
            return webHook.getSalt();
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
