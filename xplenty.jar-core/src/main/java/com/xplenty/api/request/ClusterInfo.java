/**
 * 
 */
package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Cluster;

/**
 * 
 * @author Yuriy Kovalek
 *
 */
public class ClusterInfo extends AbstractInfoRequest<Cluster> {

    public ClusterInfo(long entityId) {
        super(entityId);
    }

    @Override
	public String getName() {
		return Xplenty.Resource.Cluster.name;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.Cluster.format(Long.toString(entityId));
	}

}
