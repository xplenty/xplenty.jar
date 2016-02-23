package com.xplenty.api.request.hook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Hook;
import com.xplenty.api.model.HookEvent;
import com.xplenty.api.model.HookSettings;
import com.xplenty.api.request.AbstractManipulationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Update existing Web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:47
 */
public class UpdateHook extends AbstractManipulationRequest<Hook> {

    public UpdateHook(Long id, String name, HookSettings settings, List<String> events) {
        super(null);
        List<com.xplenty.api.model.HookEvent> convEvents = events.size() > 0 ? new ArrayList<HookEvent>(events.size()) : null;
        for (String event : events) {
            com.xplenty.api.model.HookEvent hookEvent = new com.xplenty.api.model.HookEvent(event);
            convEvents.add(hookEvent);
        }
        Hook entity = new Hook(id, name, settings, convEvents);
        setEntity(entity);
    }

    public UpdateHook(Long id, String name, List<Xplenty.HookEvent> events, HookSettings settings) {
        super(null);
        List<HookEvent> convEvents = events.size() > 0 ? new ArrayList<HookEvent>(events.size()) : null;
        for (Xplenty.HookEvent event : events) {
            com.xplenty.api.model.HookEvent hookEvent = new com.xplenty.api.model.HookEvent(event.toString());
            convEvents.add(hookEvent);
        }

        Hook entity = new Hook(id, name, settings, convEvents);
        setEntity(entity);
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
