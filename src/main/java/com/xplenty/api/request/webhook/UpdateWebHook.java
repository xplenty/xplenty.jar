package com.xplenty.api.request.webhook;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.WebHook;
import com.xplenty.api.model.WebHookSettings;
import com.xplenty.api.request.AbstractManipulationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Update existing Web hook
 * Author: Xardas
 * Date: 05.01.16
 * Time: 15:47
 */
public class UpdateWebHook extends AbstractManipulationRequest<WebHook> {

    public UpdateWebHook(Long id, WebHookSettings settings, List<String> addEvents, List<String> removeEvents) {
        super(new WebHook(id, settings, addEvents, removeEvents));
    }

    public UpdateWebHook(Long id, List<Xplenty.WebHookEvent> addEvents, List<Xplenty.WebHookEvent> removeEvents, WebHookSettings settings) {
        super(null);
        List<String> convAddEvents = null;
        List<String> convRemoveEvents = null;
        if (addEvents != null) {
            convAddEvents = new ArrayList<>(addEvents.size());
            for (Xplenty.WebHookEvent event : addEvents) {
                convAddEvents.add(event.toString());
            }
        }
        if (removeEvents != null) {
            convRemoveEvents = new ArrayList<>(removeEvents.size());
            for (Xplenty.WebHookEvent event : removeEvents) {
                convRemoveEvents.add(event.toString());
            }
        }

        WebHook entity = new WebHook(id, settings, convAddEvents, convRemoveEvents);
        setEntity(entity);
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
