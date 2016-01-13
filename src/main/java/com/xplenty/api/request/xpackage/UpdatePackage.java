package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.Package;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Update an existing package.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 19:03
 */
public class UpdatePackage extends AbstractManipulationRequest<com.xplenty.api.model.Package> {

    public UpdatePackage(Package entity) {
        super(entity);
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.UpdatePackage.format(entity.getId().toString());
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdatePackage.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
