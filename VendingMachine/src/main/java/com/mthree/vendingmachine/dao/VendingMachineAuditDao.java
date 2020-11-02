/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

/**
 *
 * @author beshoy
 */
public interface VendingMachineAuditDao {

    /**
     * writes transaction entry to audit report
     *
     * @param entry
     * @throws VendingMachinePersistenceException
     */
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
