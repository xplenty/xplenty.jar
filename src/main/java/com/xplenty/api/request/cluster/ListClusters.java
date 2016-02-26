/**
 * 
 */
package com.xplenty.api.request.cluster;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.Xplenty.ClusterStatus;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;
import java.util.Properties;

/**
 * @author Yuriy Kovalek
 *
 */
public class ListClusters extends AbstractListRequest<List<Cluster>> {
	
	public ListClusters(Properties params) {
        super(params, true);
        validateParameters(params);
	}

	private void validateParameters(Properties params) {
		if (	params.containsKey(PARAMETER_STATUS)
				&& !(params.get(PARAMETER_STATUS) instanceof ClusterStatus)
			)
			throw new XplentyAPIException(String.format("Invalid %s parameter, should be one of ClusterStatus values", PARAMETER_STATUS));
	}

    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.Clusters.value;
    }

    @Override
	public List<Cluster> getResponse(Response response) {
		try {
			return response.getContent(new TypeReference<List<Cluster>>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

	@Override
	public String getName() {
		return Xplenty.Resource.Clusters.name;
	}

}
