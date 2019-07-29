package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.http.Http;

/**
 * Author: Xardas
 * Date: 17.12.15
 * Time: 22:07
 */
public class CreateSchedule extends AbstractManipulationRequest<Schedule> {


    public CreateSchedule(Schedule schedule) {
        super(schedule);
    }

    @Override
    protected String getPackKey() {
        return "schedule";
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreateSchedule.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.CreateSchedule.value;
    }

}
