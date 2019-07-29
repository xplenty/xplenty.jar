package com.xplenty.api.request;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Cluster;
import com.xplenty.api.http.Http;
import com.xplenty.api.http.Http.Method;

public class CreateCluster extends AbstractManipulationRequest<Cluster> {

    public CreateCluster(Cluster entity) {
        super(entity);
    }

    @Override
	public Method getHttpMethod()  {
		return Http.Method.POST;
	}

	@Override
	public String getName()  {
		return Xplenty.Resource.CreateCluster.name;
	}

	@Override
	public String getEndpoint() {
		return Xplenty.Resource.CreateCluster.value;
	}

    @Override
    protected String getPackKey() {
        return "cluster";
    }
}
