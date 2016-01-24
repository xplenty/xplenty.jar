package com.xplenty.api.request.subscription;

import com.xplenty.api.Xplenty;
import com.xplenty.api.model.CreditCardInfo;
import com.xplenty.api.request.AbstractInfoRequest;

/**
 * Get payment method on file, for an existing account. If there is no payment method on file, returns Resource not found (404).
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:18
 */
public class PaymentMehodInfo extends AbstractInfoRequest<CreditCardInfo> {
    public PaymentMehodInfo() {
        super(0);
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.PaymentMethod.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.PaymentMethod.name;
    }
}
