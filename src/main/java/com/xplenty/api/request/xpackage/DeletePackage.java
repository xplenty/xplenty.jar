package com.xplenty.api.request.xpackage;

import com.xplenty.api.Xplenty;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete an existing package.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 20:10
 */
public class DeletePackage extends AbstractDeleteRequest<com.xplenty.api.model.Package> {
    public DeletePackage(long entityId) {
        super(entityId);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.DeletePackage.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeletePackage.name;
    }
}
