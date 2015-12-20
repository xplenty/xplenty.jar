/**
 * 
 */
package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Cluster;

/**
 * @author Yuriy Kovalek
 *
 */
public class TerminateCluster extends AbstractDeleteRequest<Cluster> {


    protected TerminateCluster(Long entityId) {
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
