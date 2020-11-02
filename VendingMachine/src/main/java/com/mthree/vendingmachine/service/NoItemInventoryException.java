/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

/**
 *
 * @author beshoy
 */
public class NoItemInventoryException extends Exception {

    public NoItemInventoryException(String message) {
        super(message);
    }

    public NoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }

}
