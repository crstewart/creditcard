package com.coveros.exercise.creditcard;

import com.coveros.exercise.creditcard.constants.CreditCardType;

/**
 * com.coveros.exercise.creditcard
 * Created on 1/7/2015
 *
 * @author Craig Stewart
 */
public class CreditCard {

    private String cardNumber;
    private CreditCardType cardType;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardType getCardType() {
        return cardType;
    }

    public static int calculateCheckDigit(String numberWithoutCheckDigit) {
        String normalizedNumber = normalizeCardNumber(numberWithoutCheckDigit);
        int normalizedNumberLength = normalizedNumber.length();
        int totalValue = 0;
        for (int i = 1; i < normalizedNumber.length(); i++) {
            char digit = normalizedNumber.charAt(normalizedNumberLength - i);
            int value = (int) digit - '0';
            if (i % 2 == 1) {
                value *= 2;
                if (value >= 10) {
                    value = (value % 10) + (value / 10);
                }
            }
            totalValue += value;
        }

        return 10 - (totalValue % 10);
    }

    private static String normalizeCardNumber(String cardNumber) {
        return cardNumber.replaceAll("\\w-", "");
    }
}
