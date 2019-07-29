package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Http.Method;

public class UpdateCluster extends AbstractManipulationRequest<Cluster> {

    public UpdateCluster(Cluster entity) {
        super(entity);
    }

    @Override
	public Method getHttpMethod()  {
		return Http.Method.PUT;
	}

	@Override
	public String getName()  {
		return Xplenty.Resource.UpdateCluster.name;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.UpdateCluster.format(Long.toString(entity.getId()));
	}

    @Override
    protected String getPackKey() {
        return "cluster";
    }
}
