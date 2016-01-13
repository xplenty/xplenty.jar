/**
 * 
 */
package com.xplenty.api.request.cluster;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xplenty.api.Xplenty;
import com.xplenty.api.exceptions.XplentyAPIException;
import com.xplenty.api.http.Response;
import com.xplenty.api.model.ClusterInstance;
import com.xplenty.api.request.AbstractListRequest;

import java.util.List;

/**
 * List existing cluster instances.
 * Note: This endpoint is only available for selected plans.
 * @author xardazz
 *
 */
public class ListClusterInstances extends AbstractListRequest<List<ClusterInstance>> {
    private final long clusterId;

	public ListClusterInstances(long clusterId) {
        super(null, true);
        this.clusterId = clusterId;
    }


    @Override
    protected String getEndpointRoot() {
        return Xplenty.Resource.ClusterInstances.format(String.valueOf(clusterId));
    }

    @Override
	public List<ClusterInstance> getResponse(Response response) {
		try {
			return response.getContent(new TypeReference<List<ClusterInstance>>() {});
		} catch (Exception e) {
			throw new XplentyAPIException(getName() + ": error parsing response object", e);
		}
	}

	@Override
	public String getName() {
		return Xplenty.Resource.ClusterInstances.name;
	}

}
