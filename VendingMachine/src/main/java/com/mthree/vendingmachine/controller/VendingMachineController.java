/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.controller;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Product;
import com.mthree.vendingmachine.service.Coin;
import com.mthree.vendingmachine.service.CoinValidationException;
import com.mthree.vendingmachine.service.InsufficientFundsException;
import com.mthree.vendingmachine.service.NoItemInventoryException;
import com.mthree.vendingmachine.service.ProductValidationException;
import com.mthree.vendingmachine.service.VendingMachineServiceImpl;
import com.mthree.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author beshoy
 */
public class VendingMachineController {

    private VendingMachineView view;
    private VendingMachineServiceImpl service;
    private boolean keepGoing;

    public VendingMachineController(VendingMachineView view, VendingMachineServiceImpl service, boolean keeoGoing) {
        this.view = view;
        this.service = service;
        this.keepGoing = keeoGoing;
    }

    public void run() throws VendingMachinePersistenceException, ProductValidationException, NoItemInventoryException, InsufficientFundsException {
        String menuSelection = null;
        loadData();
        while (keepGoing) {
            menuSelection = getMenuSelection();
            switch (menuSelection) {
                case "+":
                    addCoins();
                    break;
                case "0":
                    exitVendingMachine();
                    break;
                default:
                    unknownCommand();
            }
        }
    }

    private String getMenuSelection() throws VendingMachinePersistenceException {
        view.displayVendingMachineBanner();
        view.displayMessage("----- Available Products -----");
        displayProducts();
        return view.printMenuAndGetSelection();
    }

    private void displayProducts() throws VendingMachinePersistenceException {
        List<Product> products = service.getAllAvailableProducts();
        view.displayProducts(products);
    }

    private void loadData() throws VendingMachinePersistenceException {
        service.getAllData();
    }

    private void saveData() throws VendingMachinePersistenceException {
        service.writeData();
    }

    private void exitVendingMachine() throws VendingMachinePersistenceException {
        keepGoing = false;
        saveData();
        exitMessage();
    }

    private void exitMessage() {
        view.displayExitMessage();
    }

    private void unknownCommand() {
        view.displayUnknownCommandMessage();
    }

    private void addCoins() throws VendingMachinePersistenceException, ProductValidationException, NoItemInventoryException, InsufficientFundsException {
        boolean hasErrors = true;
        // get list of accepted coins and print them to user
        List<Coin> coins = service.getCoinsValues();
        view.displayCoinsOptions(coins);
        BigDecimal coinValue = null;
        do {
            int coinStringValue = view.readCoinValue("Enter coin value: ");
            coinValue = new BigDecimal(Integer.toString(coinStringValue));
            try {
                // if coin value not accepted by vending maching throw exception
                service.isValidCoinValue(coinValue);
                hasErrors = false;
            } catch (CoinValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
        service.addBalance(coinValue);
        BigDecimal balance = service.getCoinsBalance();
        view.displayAddCoinSuccessBanner(coinValue, balance);
        // display current balance to user
        view.displayBalance(service.returnChange());
        // handles if user want to add more coins, buy or exit
        handleNextOperation();
    }

    private void handleNextOperation() throws VendingMachinePersistenceException, ProductValidationException, NoItemInventoryException, InsufficientFundsException {
        int next = view.displayNextOperation();
        switch (next) {
            case 1:
                addCoins();
                break;
            case 2:
                buyProduct();
                break;
            case 0:
                service.resetBalance();
                view.displayReturnBalance();
                exitVendingMachine();
        }
    }

    private void buyProduct() throws ProductValidationException, VendingMachinePersistenceException, NoItemInventoryException, InsufficientFundsException {
        boolean invalidProduct = true;
        int productId = 0;

        view.displayMessage("----- Please choose your product -----");
        // display product with stock > 0
        displayProducts();
        List<Integer> productsIds = service.getAllProductsIds();

        do {
            view.displayProductsIds(productsIds);
            productId = view.readProductId();
            try {
                service.isValidProduct(productId);
                service.isSuffecientBalance(productId);
                service.updateStock(productId);
                service.updateBalance(productId);
                view.displayChange(service.returnChange());
                service.updateAudit(productId);
                view.displayEnjoyDrink();
                invalidProduct = false;
                exitVendingMachine();
            } catch (ProductValidationException e) {
                invalidProduct = true;
                view.displayErrorMessage(e.getMessage());
            } catch (InsufficientFundsException e) {
                view.displayErrorMessage(e.getMessage());
                handleInsufficientFunds(productId);
                invalidProduct = false;
            }
        } while (invalidProduct);
    }

    private void handleInsufficientFunds(int productId) throws VendingMachinePersistenceException, ProductValidationException, NoItemInventoryException, InsufficientFundsException {
        BigDecimal balance = service.getCoinsBalance();
        BigDecimal productPrice = service.getProduct(productId).getPrice();
        view.displayInsufficientFunds(productPrice, balance);
        handleNextOperation();
    }

}
