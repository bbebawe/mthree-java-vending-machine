/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.ui;

import com.mthree.vendingmachine.dto.Product;
import com.mthree.vendingmachine.service.Coin;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author beshoy
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayVendingMachineBanner() {
        io.print("----- Welcome to BB Vending Machine -----");
    }

    public void displayProducts(List<Product> products) {
        // use stream to call displayPorudct for each product
        products.stream().forEach((product) -> displayProduct(product));
    }

    public void displayProduct(Product product) {
        io.print("Id: " + product.getId()
                + " Name: " + product.getName()
                + " Price: " + product.getPrice()
                + " Stock: " + product.getStock());
        io.print("-----------------------");
    }

    public String printMenuAndGetSelection() {
        io.print("----- Available Operations -----");
        io.print("+. Add Coins to Buy Product");
        io.print("0. Exit");
        return io.readMenuOption("Please choose + or 0");
    }

    public void displayExitMessage() {
        io.print("----- Thank You for using BB Vending Machine -----");
        io.print("--------------------- Goodbye ---------------------");
    }

    public void displayUnknownCommandMessage() {
        io.print("----- Unknown Command -----");
    }

    public void displayCoinsOptions(List<Coin> coins) {
        io.print("----- You can add one of the following coins -----");
        coins.stream().forEach((coin) -> displayCoin(coin));
    }

    public void displayCoin(Coin coin) {
        io.print(coin.name() + " -----> " + coin.getValue());
    }

    public int readCoinValue(String prompt) {
        return io.readInt(prompt);
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public void displayAddCoinSuccessBanner(BigDecimal coinValue, BigDecimal balance) {
        Coin coin = null;
        switch (coinValue.intValue()) {
            case 1:
                coin = Coin._1P;
                break;
            case 2:
                coin = Coin._2P;
                break;
            case 5:
                coin = Coin._5P;
                break;
            case 10:
                coin = Coin._10P;
                break;
            case 20:
                coin = Coin._20P;
                break;
            case 50:
                coin = Coin._50P;
                break;
            case 100:
                coin = Coin._POUND;
        }
        io.print(coin + " added");
    }

    public int displayNextOperation() {
        String display = "----- Please choose what to do next -----"
                + "\n1. Add more coins"
                + "\n2. Buy a Product"
                + "\n0. Return Balance and Exit";
        return io.readInt(display, 0, 2);
    }

    public void displayReturnBalance() {
        io.print("----- Balance Returned -----");
        io.print("----- Please do not forget to take your coins -----");
    }

    public void displayMessage(String message) {
        io.print(message);
    }

    public void displayProductsIds(List<Integer> productsIds) {
        productsIds.stream().forEach((productId) -> io.printSameLine(productId.toString()));
    }

    public int readProductId() {
        return io.readInt("\nchoose from the above Ids");
    }

    public void displayInsufficientFunds(BigDecimal productPrice, BigDecimal balance) {
        io.print("Product price: " + productPrice + " Your Balance: " + balance);
    }

    public void displayChange(Map returnChange) {
        if (!returnChange.isEmpty()) {
            io.print("----- Please take your change -----");
            returnChange.forEach((key, value) -> {
                io.print(value + " ---> " + key);
            });
        } else {
            io.print("----- No Change Due -----");
        }
    }

    public void displayEnjoyDrink() {
        System.out.println("----- Please take your drink -----");
        System.out.println("----- Enjoy -----");
    }

    public void displayBalance(Map returnChange) {
        io.print("----- Your Balance is -----");
        returnChange.forEach((key, value) -> {
            io.print(value + " ---> " + key);
        });
    }

}
