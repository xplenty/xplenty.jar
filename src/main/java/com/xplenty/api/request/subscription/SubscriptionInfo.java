package com.xplenty.api.request.subscription;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.Subscription;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Information about current account subscription.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 16:45
 */
public class SubscriptionInfo extends AbstractInfoRequest<Subscription> {
    public SubscriptionInfo() {
        super(0);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.Subscription.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.Subscription.name;
    }
}
