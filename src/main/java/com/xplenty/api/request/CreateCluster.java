package com.xplenty.api.request;

import com.xplenty.api.model.Cluster;
import com.xplenty.api.util.Http;
import com.xplenty.api.util.Http.Method;

public class CreateCluster extends ClusterRequest {

	public CreateCluster(Cluster cluster) {
		super(cluster);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Method getHttpMethod() {
		return Http.Method.POST;
	}

}
