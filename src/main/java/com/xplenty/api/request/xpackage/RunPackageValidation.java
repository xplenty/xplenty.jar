package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.PackageValidation;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Runs new validation process for the package and returns information about status and tracking url.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 19:11
 */
public class RunPackageValidation extends AbstractDeleteRequest<PackageValidation> {

    public RunPackageValidation(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.RunPackageValidation.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.RunPackageValidation.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
