/*
 * CreditCard-Java - CreditCardTest.java - Copyright 2014 Coveros, Inc.
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-ShareAlike 
 * 4.0 International License. http://creativecommons.org/licenses/by-nc-sa/4.0/
 */
package com.coveros.exercise.creditcard;

import static org.junit.Assert.*;

import com.coveros.exercise.creditcard.constants.CreditCardType;
import com.coveros.exercise.creditcard.exceptions.BadCheckDigitException;
import com.coveros.exercise.creditcard.exceptions.InvalidCardLengthException;
import com.coveros.exercise.creditcard.exceptions.InvalidCardNumberException;
import org.junit.Test;

/**
 * These are unit tests for a coding exercise to implement a library to handle
 * some simple checks against the format of a credit card number. No GUI or
 * command-line interface is necessary. The following tests specify how the
 * library needs to work.
 * <p>
 * Your implementation must pass all of these existing tests, which should not
 * be changed. For behavior that is not explicitly specified, feel free to add
 * additional tests.
 * <p>
 * Implementations should exhibit best practices and common coding conventions.
 * When you are brought in for a face-to-face interview, we will discuss your
 * implementation and possibly make modifications.
 * <p>
 * These unit tests use the <a href="http://www.junit.org/">JUnit</a> unit
 * testing framework.
 * 
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Bank_card_number">http://en.wikipedia.org/wiki/Bank_card_number</a>
 * @author Gene Gotimer <gene.gotimer@coveros.com>
 */
public class CreditCardTest {

  @Test
  public void testCalculateCheckDigit() {
    assertEquals(1, CreditCard.calculateCheckDigit("4111 1111 1111 111"));
  }

  @Test(expected = BadCheckDigitException.class)
  public void testBadCheckDigit() throws InvalidCardNumberException {
    new CreditCard("4111 1111 1111 1110");
  }

  @Test(expected = InvalidCardLengthException.class)
  public void testShortValidCheckDigit() throws InvalidCardNumberException {
    new CreditCard("411-111-111-117");
  }

  @Test(expected = InvalidCardLengthException.class)
  public void testLongValidCheckDigit() throws InvalidCardNumberException {
    new CreditCard("4111 1111 1111 1111 3");
  }

  @Test(expected = BadCheckDigitException.class)
  public void testLongInvalidCheck() throws InvalidCardNumberException {
    new CreditCard("4111 1111 1111 1111 9");
  }

  @Test
  public void testGetCardTypeVisa() throws InvalidCardNumberException {
    final CreditCard creditCard = new CreditCard("4111 1111 1111 1111");
    assertEquals(CreditCardType.VISA, creditCard.getCardType());
  }

  @Test
  public void testGetCardMasterCard() throws InvalidCardNumberException {
    final CreditCard creditCard = new CreditCard("5555 5555 5555 4444");
    assertEquals(CreditCardType.MASTERCARD, creditCard.getCardType());
  }

  @Test
  public void testGetCardAmericanExpress34() throws InvalidCardNumberException {
    final CreditCard creditCard = new CreditCard("3411 111111 11111");
    assertEquals(CreditCardType.AMERICAN_EXPRESS, creditCard.getCardType());
  }

  @Test
  public void testGetCardAmericanExpress37() throws InvalidCardNumberException {
    final CreditCard creditCard = new CreditCard("3700-000000-00002");
    assertEquals(CreditCardType.AMERICAN_EXPRESS, creditCard.getCardType());
  }

  @Test
  public void testGetCardDiscover() throws InvalidCardNumberException {
    final CreditCard creditCard = new CreditCard("6011111111111117");
    assertEquals(CreditCardType.DISCOVER_CARD, creditCard.getCardType());
  }

  @Test
  public void testGetCardUnknown() throws InvalidCardNumberException {
    final CreditCard creditCard = new CreditCard("9999 9999 9999 9995");
    assertEquals(CreditCardType.UNKNOWN, creditCard.getCardType());
  }

}
