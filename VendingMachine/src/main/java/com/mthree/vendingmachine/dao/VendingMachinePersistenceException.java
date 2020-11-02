/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

/**
 *
 * @author beshoy
 */
public class VendingMachinePersistenceException extends Exception {

    public VendingMachinePersistenceException(String message) {
        super(message);
    }

    public VendingMachinePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
