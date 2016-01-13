package com.xplenty.api.request.misc;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.ProductUpdate;
import com.xplenty.api.request.AbstractDeleteRequest;

/**
 * This action allows to like product update by authenticated user.
 * Author: Xardas
 * Date: 11.01.16
 * Time: 18:09
 */
public class LikeProductUpdate extends AbstractDeleteRequest<ProductUpdate> {

    public LikeProductUpdate(long entityId) {
        super(entityId);
    }

    @Override
    public String getEndpoint(String apiHost, String accountName) {
        return String.format("%s/%s", apiHost, getEndpoint());
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.LikeProductUpdate.format(String.valueOf(entityId));
    }

    @Override
    public String getName() {
        return Xplenty.Resource.LikeProductUpdate.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.POST;
    }
}
