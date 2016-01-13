package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.PackageValidation;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Returns information about progress of the package validation process.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 19:19
 */
public class PackageValidationInfo extends AbstractInfoRequest<PackageValidation> {
    private final long packageId;

    public PackageValidationInfo(long entityId, long packageId) {
        super(entityId);
        this.packageId = packageId;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.PackageValidation.format(String.valueOf(packageId), String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PackageValidation.name;
    }
}
