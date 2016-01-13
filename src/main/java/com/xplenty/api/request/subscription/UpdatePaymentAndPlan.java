package com.xplenty.api.request.subscription;

import com.xplenty.api.Xplenty;
import com.xplenty.api.http.Http;
import com.xplenty.api.model.CreditCardInfo;
import com.xplenty.api.request.AbstractManipulationRequest;

import java.util.HashMap;

/**
 * This call updates the payment method or plan or both.
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:01
 */
public class UpdatePaymentAndPlan extends AbstractManipulationRequest<CreditCardInfo> {
    private final String billingPaymentToken;
    private final String planId;

    public UpdatePaymentAndPlan(String billingPaymentToken, String planId) {
        super(null);
        this.billingPaymentToken = billingPaymentToken;
        this.planId = planId;
    }

    @Override
    protected String getPackKey() {
        return null;
    }

    @Override
    public Object getBody() {
        HashMap<String, String> requestEntity = new HashMap<>();
        requestEntity.put("billing_payment_token", billingPaymentToken);
        requestEntity.put("plan_id", planId);
        return requestEntity;
    }

    @Override
    protected String getEndpoint() {
        return Xplenty.Resource.UpdatePaymentMethodAndPlan.value;
    }

    @Override
    public String getName() {
        return Xplenty.Resource.UpdatePaymentMethodAndPlan.name;
    }

    @Override
    public Http.Method getHttpMethod() {
        return Http.Method.PUT;
    }
}
