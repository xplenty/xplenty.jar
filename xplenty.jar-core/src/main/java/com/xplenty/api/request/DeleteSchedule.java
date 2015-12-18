package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Schedule;

/**
 * Author: Xardas
 * Date: 18.12.15
 * Time: 20:25
 */
public class DeleteSchedule extends AbstractDeleteRequest<Schedule> {

    public DeleteSchedule(Long entityId) {
        super(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.RemoveSchedule.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.RemoveSchedule.format(String.valueOf(entityId));
    }
}
