package com.xplenty.api.model;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Xardas
 * Date: 10.01.16
 * Time: 17:27
 */
public class CreditCardInfoTest extends TestCase {
    @Test
    public void testBuilder() {
        final Date now = new Date();
        CreditCardInfo ccc = createMockCreditCardInfo();
        assertNotNull(ccc);
        assertEquals("xxxx-xxxx-xxxx-" + String.valueOf(ccc.getCardLast4()), ccc.getCardNumber());
    }


    public static CreditCardInfo createMockCreditCardInfo() {
        CreditCardInfo ccc = new CreditCardInfo();
        ccc.cardLast4 = 9876;
        ccc.cardNumber = "xxxx-xxxx-xxxx-9876";
        ccc.cardType = "MasterCard";
        ccc.expirationDate = "06/66";
        ccc.url = "https://testapi.xplenty.com/api/payment_method";
        return ccc;
    }
}
