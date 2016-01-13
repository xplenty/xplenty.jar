package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.model.WebHookEvent;
import com.xplenty.api.model.WebHookSettings;
import com.xplenty.api.request.AbstractManipulationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Create new Web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:47
 */
public class CreateWebHook extends AbstractManipulationRequest<WebHook> {

    public CreateWebHook(WebHookSettings settings, List<String> events) {
        super(null);
        if (events == null || events.size() == 0) {
            throw new XplentyAPIException("You must subscribe to at least 1 event!");
        }
        List<WebHookEvent> convEvents = new ArrayList<>(events.size());
        for (String event : events) {
            WebHookEvent webHookEvent = new WebHookEvent(event);
            convEvents.add(webHookEvent);
        }
        WebHook entity = new WebHook(settings, convEvents);
        setEntity(entity);
    }

    public CreateWebHook(List<Xplenty.WebHookEvent> events, WebHookSettings settings) {
        super(null);
        if (events == null || events.size() == 0) {
            throw new XplentyAPIException("You must subscribe to at least 1 event!");
        }
        List<WebHookEvent> convEvents = new ArrayList<>(events.size());
        for (Xplenty.WebHookEvent event : events) {
            WebHookEvent webHookEvent = new WebHookEvent(event.toString());
            convEvents.add(webHookEvent);
        }
        WebHook entity = new WebHook(settings, convEvents);
        setEntity(entity);
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.CreateWebHook.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreateWebHook.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
