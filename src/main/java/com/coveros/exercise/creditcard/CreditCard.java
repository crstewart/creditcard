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
    private CreditCardType cardType;

    public CreditCard(String cardNumber) throws InvalidCardNumberException {
        this.cardNumber = cardNumber;
        validateCardNumber();
        this.cardType = CreditCard.determineCardType(cardNumber);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public CreditCardType getCardType() {
        return cardType;
    }

    public int getCheckDigit() {
        return getIntegerValue(cardNumber.charAt(cardNumber.length() - 1));
    }

    public static CreditCardType determineCardType(String cardNumber) throws InvalidCardLengthException {
        String normalizedCardNumber = CreditCard.normalizeCardNumber(cardNumber);
        if (normalizedCardNumber.startsWith("4")) {
            validateCardLength(normalizedCardNumber, 13, 16);
            return CreditCardType.VISA;
        } else if (normalizedCardNumber.startsWith("5") && getIntegerValue(normalizedCardNumber.charAt(1)) <= 5) {
            validateCardLength(normalizedCardNumber, 16);
            return CreditCardType.MASTERCARD;
        } else if ((normalizedCardNumber.startsWith("34") || normalizedCardNumber.startsWith("37"))) {
            validateCardLength(normalizedCardNumber, 15);
            return CreditCardType.AMERICAN_EXPRESS;
        } else if (normalizedCardNumber.startsWith("6011") || normalizedCardNumber.startsWith("65")) {
            validateCardLength(normalizedCardNumber, 16);
            return CreditCardType.DISCOVER_CARD;
        }

        validateCardLength(cardNumber, 12, 19);
        return CreditCardType.UNKNOWN;
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

    public static String normalizeCardNumber(String cardNumber) {
        return cardNumber.replaceAll("[\\s\\-]", "");
    }

    private static int getIntegerValue(char digit) {
        return (int) digit - '0';
    }

    private void validateCardNumber() throws BadCheckDigitException {
        String numberWithoutCheckDigit = cardNumber.substring(0, cardNumber.length() - 1);
        if (CreditCard.calculateCheckDigit(numberWithoutCheckDigit) != getCheckDigit()) {
            throw new BadCheckDigitException();
        }
    }

    private static void validateCardLength(String normalizedCardNumber, int validLength)
            throws InvalidCardLengthException {
        validateCardLength(normalizedCardNumber, validLength, validLength);
    }

    private static void validateCardLength(String normalizedCardNumber, int low, int high)
            throws InvalidCardLengthException {
        if (normalizedCardNumber.length() < low || normalizedCardNumber.length() > high) {
            throw new InvalidCardLengthException();
        }
    }
}
