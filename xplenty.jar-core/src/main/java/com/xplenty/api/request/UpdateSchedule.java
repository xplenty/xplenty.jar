package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.util.Http;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 22:07
 */
public class UpdateSchedule extends AbstractManipulationRequest<Schedule> {

    public UpdateSchedule(Schedule schedule) {
        super(schedule);
    }

    @Override
    protected String getPackKey() {
        return "schedule";
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdateSchedule.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.UpdateSchedule.format(String.valueOf(entity.getId()));
    }

}
