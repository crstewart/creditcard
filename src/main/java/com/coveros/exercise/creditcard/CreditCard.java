package com.coveros.exercise.creditcard;

import com.coveros.exercise.creditcard.constants.CreditCardType;
import com.coveros.exercise.creditcard.exceptions.BadCheckDigitException;
import com.coveros.exercise.creditcard.exceptions.InvalidCardLengthException;
import com.coveros.exercise.creditcard.exceptions.InvalidCardNumberException;

/**
 * com.coveros.exercise.creditcard
 * Created on 1/7/2015
 *
 * @author Craig Stewart
 */
public class CreditCard {

    private String cardNumber;

    public CreditCard(String cardNumber) throws InvalidCardNumberException {
        this.cardNumber = cardNumber;
        validateCardNumber();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardType getCardType() {
        return null;
    }

    public int getCheckDigit() {
        return getIntegerValue(cardNumber.charAt(cardNumber.length() - 1));
    }

    public static int calculateCheckDigit(String numberWithoutCheckDigit) {
        String normalizedNumber = normalizeCardNumber(numberWithoutCheckDigit);
        int normalizedNumberLength = normalizedNumber.length();
        int totalValue = 0;
        for (int i = 1; i <= normalizedNumberLength; i++) {
            char digit = normalizedNumber.charAt(normalizedNumberLength - i);
            int value = getIntegerValue(digit);
            if (i % 2 == 1) {
                value *= 2;
                if (value >= 10) {
                    value = (value % 10) + (value / 10);
                }
            }
            totalValue += value;
        }

        return (totalValue % 10 == 0) ? 0 : 10 - (totalValue % 10);
    }

    private void validateCardNumber() throws BadCheckDigitException {
        String numberWithoutCheckDigit = cardNumber.substring(0, cardNumber.length() - 1);
        if (CreditCard.calculateCheckDigit(numberWithoutCheckDigit) != getCheckDigit()) {
            throw new BadCheckDigitException();
        }
    }

    private static String normalizeCardNumber(String cardNumber) {
        return cardNumber.replaceAll("[\\s\\-]", "");
    }

    private static int getIntegerValue(char digit) {
        return (int) digit - '0';
    }
}
