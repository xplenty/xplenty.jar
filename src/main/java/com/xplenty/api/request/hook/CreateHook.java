package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Hook;
import com.xplenty.api.model.HookSettings;
import com.xplenty.api.request.AbstractManipulationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Create new Web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:47
 */
public class CreateHook extends AbstractManipulationRequest<Hook> {

    public CreateHook(String name, HookSettings settings, List<String> events) {
        super(null);
        if (events == null || events.size() == 0) {
            throw new XplentyAPIException("You must subscribe to at least 1 event!");
        }
        List<com.xplenty.api.model.HookEvent> convEvents = new ArrayList<>(events.size());
        for (String event : events) {
            com.xplenty.api.model.HookEvent hookEvent = new com.xplenty.api.model.HookEvent(event);
            convEvents.add(hookEvent);
        }
        Hook entity = new Hook(name, settings, convEvents);
        setEntity(entity);
    }

    public CreateHook(String name, List<Xplenty.HookEvent> events, HookSettings settings) {
        super(null);
        if (events == null || events.size() == 0) {
            throw new XplentyAPIException("You must subscribe to at least 1 event!");
        }
        List<com.xplenty.api.model.HookEvent> convEvents = new ArrayList<>(events.size());
        for (Xplenty.HookEvent event : events) {
            com.xplenty.api.model.HookEvent hookEvent = new com.xplenty.api.model.HookEvent(event.toString());
            convEvents.add(hookEvent);
        }
        Hook entity = new Hook(name, settings, convEvents);
        setEntity(entity);
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.CreateHook.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreateHook.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
