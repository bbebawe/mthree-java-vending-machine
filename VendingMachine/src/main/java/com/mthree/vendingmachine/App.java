/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine;

import com.mthree.vendingmachine.controller.VendingMachineController;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import com.mthree.vendingmachine.service.ProductValidationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author beshoy
 */
public class App {

    public static void main(String[] args) throws VendingMachinePersistenceException, ProductValidationException, NoItemInventoryException, InsufficientFundsException {
        // call to app context to inject project dependecines
        ApplicationContext appContext
                = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        VendingMachineController controller = appContext.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
