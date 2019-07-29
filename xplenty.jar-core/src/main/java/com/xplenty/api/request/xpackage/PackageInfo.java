package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.*;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Author: Xardas
 * Date: 20.12.15
 * Time: 20:07
 */
public class PackageInfo extends AbstractInfoRequest<com.xplenty.api.model.Package> {
    public PackageInfo(long entityId) {
        super(entityId);
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Package.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.Package.format(String.valueOf(entityId));
    }
}
