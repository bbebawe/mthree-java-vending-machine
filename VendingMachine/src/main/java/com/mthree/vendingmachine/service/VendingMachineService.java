/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 *
 * @author beshoy
 */
public interface VendingMachineService {

    /**
     * return list of products in stock
     *
     * @return list of products in stock
     * @throws VendingMachinePersistenceException
     */
    public List<Product> getAllAvailableProducts() throws
            VendingMachinePersistenceException;

    /**
     * get all products
     *
     * @throws VendingMachinePersistenceException
     */
    public void getAllData() throws VendingMachinePersistenceException;

    /**
     * save products
     *
     * @throws VendingMachinePersistenceException
     */
    public void writeData() throws VendingMachinePersistenceException;

    /**
     * return list of accepted coins
     *
     * @return list of accepted coins
     */
    public List<Coin> getCoinsValues();

    /**
     * check if coin value if accepted by vending machine
     *
     * @param coinValue coin value
     * @throws CoinValidationException
     */
    public void isValidCoinValue(BigDecimal coinValue) throws CoinValidationException;

    /**
     * checks if product has a valid id
     *
     * @param productId product id
     * @throws VendingMachinePersistenceException
     * @throws ProductValidationException
     */
    public void isValidProduct(int productId) throws VendingMachinePersistenceException, ProductValidationException;

    /**
     * adds coin value to user balance
     *
     * @param coin coin value
     */
    public void addBalance(BigDecimal coin);

    /**
     * return user balance
     *
     * @return
     */
    public BigDecimal getCoinsBalance();

    /**
     * resets user balance to zero
     */
    public void resetBalance();

    /**
     *  return list of all available products ids
     * @return
     * @throws VendingMachinePersistenceException
     * @throws VendingMachinePersistenceException 
     */
    public List<Integer> getAllProductsIds() throws
            VendingMachinePersistenceException, VendingMachinePersistenceException;

    /**
     * checks if product is in stock
     * @param productId product id
     * @throws VendingMachinePersistenceException
     * @throws NoItemInventoryException 
     */
    public void isProductInStock(int productId) throws VendingMachinePersistenceException, NoItemInventoryException;

    /**
     * get product using id 
     * @param productId product id
     * @return product matching id
     * @throws VendingMachinePersistenceException 
     */
    public Product getProduct(int productId) throws VendingMachinePersistenceException;

    /**
     * checks if user have enough balance to buy a product
     * @param productId product id
     * @throws VendingMachinePersistenceException
     * @throws InsufficientFundsException 
     */
    public void isSuffecientBalance(int productId) throws VendingMachinePersistenceException, InsufficientFundsException;

    /**
     * writes new entry to audit file
     * @param productId product id 
     * @throws VendingMachinePersistenceException 
     */
    public void updateAudit(int productId) throws VendingMachinePersistenceException;
/**
 * updates product stock after successful transaction
 * @param productId
 * @throws VendingMachinePersistenceException 
 */
    public void updateStock(int productId) throws VendingMachinePersistenceException;

    /**
     * calculates and return user change
     * @return map with change coins
     */
    public Map returnChange();

    /**
     * update user balance and subtract product price from it
     * @param productId product id to get product price
     * @throws VendingMachinePersistenceException 
     */
    public void updateBalance(int productId) throws VendingMachinePersistenceException;
}
