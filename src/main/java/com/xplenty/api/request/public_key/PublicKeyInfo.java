package com.xplenty.api.request.public_key;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Get public key information
 * Author: Xardas
 * Date: 06.01.16
 * Time: 21:59
 */
public class PublicKeyInfo extends AbstractInfoRequest<PublicKey> {

    public PublicKeyInfo(long entityId) {
        super(entityId);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.PublicKey.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PublicKey.name;
    }
}
