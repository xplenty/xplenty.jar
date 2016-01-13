package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Data model for Xplenty subscription
 * An Xplenty subscription contains information about current acount plan.
 * Author: Xardas
 * Date: 04.01.16
 * Time: 17:32
 */
public class Subscription extends XplentyObject<Subscription> {
    @JsonProperty("trial_period_days")
    protected Integer trialPeriodDays;
    @JsonProperty("plan_id")
    protected String planId;
    @JsonProperty("trial_start")
    protected Date trialStart;
    @JsonProperty("trial_end")
    protected Date trialEnd;
    @JsonProperty("trialling")
    protected Boolean isTrial;
    @JsonProperty
    protected String url;

    protected Subscription() {
        super(Subscription.class);
    }

    /**
     *
     * @return the period of the trial plan
     */
    public Integer getTrialPeriodDays() {
        return trialPeriodDays;
    }

    /**
     *
     * @return the numeric identifier of the current plan
     */
    public String getPlanId() {
        return planId;
    }

    /**
     *
     * @return the date and time the trial plan was started
     */
    public Date getTrialStart() {
        return trialStart;
    }

    /**
     *
     * @return the date and time the trial plan was ended
     */
    public Date getTrialEnd() {
        return trialEnd;
    }

    /**
     *
     * @return indicates if current plan is trial
     */
    public Boolean isTrial() {
        return isTrial;
    }

    /**
     *
     * @return the subscription resource URL
     */
    public String getUrl() {
        return url;
    }
}
