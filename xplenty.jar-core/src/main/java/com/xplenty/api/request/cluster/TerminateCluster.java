/**
 * 
 */
package com.xplenty.api.request.cluster;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * @author Yuriy Kovalek
 *
 */
public class TerminateCluster extends AbstractDeleteRequest<Cluster> {


    public TerminateCluster(Long entityId) {
        super(entityId);
    }

    @Override
	public String getName() {
		return Xplenty.Resource.TerminateCluster.name;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.TerminateCluster.format(Long.toString(entityId));
	}

}
