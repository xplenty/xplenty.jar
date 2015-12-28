package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Schedule;

/**
 * Author: Xardas
 * Date: 20.12.15
 * Time: 19:54
 */
public class ScheduleInfo extends AbstractInfoRequest<Schedule> {

    public ScheduleInfo(long entityId) {
        super(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Schedule.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.Schedule.format(String.valueOf(entityId));
    }
}
