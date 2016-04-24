package com.xplenty.api.request.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Schedule;
import com.xplenty.api.request.AbstractSearchRequest;

import java.util.List;
import java.util.Properties;

/**
 * Search schedules based on the specified query
 *
 * Author: Xardas
 * Date: 24.04.16
 * Time: 21:05
 */
public class SearchSchedules extends AbstractSearchRequest<List<Schedule>> {
    public SearchSchedules(Properties parameters) {
        super(parameters);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.SearchSchedules.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.SearchSchedules.name;
    }

    @Override
    public List<Schedule> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Schedule>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
    }
}
