/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author beshoy
 */
public class VendingMachineAuditFileImpl implements VendingMachineAuditDao {

    private String auditFile;

    public VendingMachineAuditFileImpl(String auditFile) {
        this.auditFile = auditFile;
    }

    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(auditFile, true));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not persist audit information.", e);
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }
}
