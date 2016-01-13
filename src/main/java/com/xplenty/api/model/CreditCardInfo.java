package com.xplenty.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for credit card info
 * Author: Xardas
 * Date: 10.01.16
 * Time: 16:53
 */
public class CreditCardInfo extends XplentyObject<CreditCardInfo> {
    @JsonProperty("card_last_4")
    protected Integer cardLast4;
    @JsonProperty("card_number")
    protected String cardNumber;
    @JsonProperty("expiration_date")
    protected String expirationDate;
    @JsonProperty("card_type")
    protected String cardType;
    @JsonProperty
    protected String url;


    protected CreditCardInfo() {
        super(CreditCardInfo.class);
    }

    /**
     *
     * @return last four digits of credit card number
     *
     */
    public Integer getCardLast4() {
        return cardLast4;
    }

    /**
     *
     * @return  obfuscated credit card number with last 4 digits being visible
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     *
     * @return credit card expiration date
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     *
     * @return credit card type e.g. Visa
     */
    public String getCardType() {
        return cardType;
    }

    /**
     *
     * @return API resource url
     */
    public String getUrl() {
        return url;
    }
}
