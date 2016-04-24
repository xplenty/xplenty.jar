/**
 * 
 */
package com.xplenty.api.request.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * @author Yuriy Kovalek
 *
 */
public class ListJobs extends AbstractListRequest<List<Job>> {
    public static final String PARAMETER_INCLUDE = "include";
	
	public ListJobs(Properties params) {
        super(params, true);
        validateParameters(params);
	}

	private void validateParameters(Properties params) {
		if (params.containsKey(PARAMETER_STATUS)
				&& !(params.get(PARAMETER_STATUS) instanceof Xplenty.JobStatus)
			) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of JobStatus values", PARAMETER_STATUS));
        }
        if (params.containsKey(PARAMETER_INCLUDE)
                && !(params.get(PARAMETER_INCLUDE) instanceof Xplenty.ListJobInclude)
                ) {
            throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of ListJobInclude values", PARAMETER_INCLUDE));
        }
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Jobs.name;
	}


    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Jobs.value;
    }

    @Override
	public List<Job> getResponse(Response response) {
        try {
            return response.getContent(new TypeReference<List<Job>>() {});
        } catch (Exception e) {
            throw new XplentyAPIException(getName() + ": error parsing response object", e);
        }
	}

}
