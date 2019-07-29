package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Schedule;

/**
 * Author: Xardas
 * Date: 18.12.15
 * Time: 20:25
 */
public class CloneSchedule implements Request<Schedule> {
    private final Long entityId;


    public CloneSchedule(long entityId) {
        this.entityId = entityId;
    }

    @Override
    public Schedule getResponse(Response response) {
        try {
            final Schedule value = response.getContent(Schedule.class);
            return value;
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CloneSchedule.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.CloneSchedule.format(String.valueOf(entityId));
    }

    @Override
    public boolean hasBody() {
        return false;
    }

    @Override
    public Http.MediaType getResponseType() {
        return Http.MediaType.JSON;
    }


    @Override
    public Object getBody() {
        return null;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
