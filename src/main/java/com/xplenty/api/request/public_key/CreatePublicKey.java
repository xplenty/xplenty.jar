package com.xplenty.api.request.public_key;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.PublicKey;
import com.xplenty.api.request.AbstractManipulationRequest;

/**
 * Create new public key
 * Author: Xardas
 * Date: 06.01.16
 * Time: 20:49
 */
public class CreatePublicKey extends AbstractManipulationRequest<PublicKey> {

    public CreatePublicKey(String name, String publicKey) {
        super(new PublicKey(name, publicKey));
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.CreatePublicKey.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.CreatePublicKey.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
