package com.xplenty.api.request.public_key;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * Delete public key
 * Author: Xardas
 * Date: 06.01.16
 * Time: 21:25
 */
public class DeletePublicKey extends AbstractDeleteRequest<PublicKey> {

    public DeletePublicKey(long entityId) {
        super(entityId);
    }


    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.DeletePublicKey.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.DeletePublicKey.name;
    }
}
