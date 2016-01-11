/**
 * 
 */
package com.xplenty.api.request.job;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Job;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * @author Yuriy Kovalek
 *
 */
public class JobInfo extends AbstractInfoRequest<Job> {
    private final boolean includeCluster;
    private final boolean includePackage;

    public JobInfo(long entityId, boolean includeCluster, boolean includePackage) {
        super(entityId);
        this.includeCluster = includeCluster;
        this.includePackage = includePackage;
    }

    @Override
	public String getName() {
		return Xplenty.Resource.Job.name;
	}

	@Override
	public String getEndpoint() {
        final String include;
        if (!includeCluster && !includePackage) {
            include = "";
        } else if (includeCluster && includePackage) {
            include = "?include=cluster,package";
        } else {
            include = String.format("?include=%s", includePackage ? "package" : "cluster");
        }
		return Xplenty.Resource.Job.format(Long.toString(entityId)) + include;
	}

}
