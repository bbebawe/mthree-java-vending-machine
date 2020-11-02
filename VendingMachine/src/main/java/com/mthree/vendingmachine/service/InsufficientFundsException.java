/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

/**
 *
 * @author beshoy
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

}
