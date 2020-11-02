/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

/**
 *
 * @author beshoy
 */
public class CoinValidationException extends Exception {

    public CoinValidationException(String message) {
        super(message);
    }

    public CoinValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
