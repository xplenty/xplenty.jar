/**
 * 
 */
package com.xplenty.api.request.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;


/**
 * Request for retrieval of all available schedules
 * Author: Xardas
 * Date: 16.12.15
 * Time: 18:08
 */
public class ListSchedules extends AbstractListRequest<List<Schedule>> {


	public ListSchedules(Properties params) {
		super(params, true);
        validateParameters(params);
	}


	private void validateParameters(Properties params) {
        if (params.containsKey(PARAMETER_STATUS)
                && !(params.get(PARAMETER_STATUS) instanceof Xplenty.ScheduleStatus)) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of ScheduleStatus values", PARAMETER_STATUS));
        }
	}

	@Override
	public List<Schedule> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Schedule>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Schedules.name;
	}

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Schedules.value;
    }
}
