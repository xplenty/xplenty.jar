package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Author: Xardas
 * Date: 20.12.15
 * Time: 20:07
 */
public class PackageInfo extends AbstractInfoRequest<com.xplenty.api.model.Package> {
    private final boolean includeDataFlow;

    public PackageInfo(long entityId, boolean includeDataFlow) {
        super(entityId);
        this.includeDataFlow = includeDataFlow;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Package.name;
    }

    @Override
    public String getEndpoint() {
        return Xplenty.Resource.Package.format(String.valueOf(entityId) + (includeDataFlow ? "?include=flow" : ""));
    }
}
