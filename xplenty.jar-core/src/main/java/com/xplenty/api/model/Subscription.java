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
    private Integer trialPeriodDays;
    @JsonProperty("plan_id")
    private Long planId;
    @JsonProperty("trial_start")
    private Date trialStart;
    @JsonProperty("trial_end")
    private Date trialEnd;
    @JsonProperty("trialling")
    private Boolean isTrial;
    @JsonProperty
    private String url;

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
    public Long getPlanId() {
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
