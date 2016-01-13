package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Package;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Create a new package.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 18:32
 */
public class CreatePackage extends AbstractManipulationRequest<com.xplenty.api.model.Package> {
    public CreatePackage(Package entity) {
        super(entity);
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.CreatePackage.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreatePackage.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
