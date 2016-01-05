package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.request.AbstractRequest;

/**
 * Reset salt for web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 18:07
 */
public class WebHookResetSalt extends AbstractRequest<String> {
    private final Long webHookId;

    public WebHookResetSalt(Long webHookId) {
        this.webHookId = webHookId;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.WebHookResetSalt.format(webHookId.toString());
    }

    @Override
    public String getName() {
        return Xplenty.Resource.WebHookResetSalt.name;
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
            WebHook webHook = response.getContent(WebHook.class);
            return webHook.getSalt();
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
