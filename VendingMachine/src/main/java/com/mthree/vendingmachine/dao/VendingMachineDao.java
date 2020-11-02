/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Product;
import java.util.List;

/**
 *
 * @author beshoy
 */
public interface VendingMachineDao {

    /** 
     * return all products from file
     * @return list of products
     * @throws VendingMachinePersistenceException 
     */
    public List<Product> getAllProducts() throws VendingMachinePersistenceException;

    /**
     * adds product to file 
     * @param product product to add
     * @return added product or null
     * @throws VendingMachinePersistenceException 
     */
    public Product addProduct(Product product) throws VendingMachinePersistenceException;

    /**
     *  update product stock
     * @param product product to update stock
     * @throws VendingMachinePersistenceException 
     */
    public void updateProductStock(Product product) throws VendingMachinePersistenceException;

    /**
     * get product using its id
     * @param productId product id
     * @return product matching id
     * @throws VendingMachinePersistenceException 
     */
    public Product getProduct(int productId) throws VendingMachinePersistenceException;

    /**
     * load all products from file to memory
     * @throws VendingMachinePersistenceException 
     */
    public void loadProducts() throws VendingMachinePersistenceException;

    /**
     * create product from string
     * @param productAsText product string
     * @return product object
     * @throws VendingMachinePersistenceException 
     */
    public Product unmarshallProduct(String productAsText) throws VendingMachinePersistenceException;

    /**
     * create string from product object
     * @param product product object
     * @return product as string
     * @throws VendingMachinePersistenceException 
     */
    public String marshallProduct(Product product) throws VendingMachinePersistenceException;

    /**
     * save all products to file
     * @throws VendingMachinePersistenceException 
     */
    public void saveProducts() throws VendingMachinePersistenceException;
}
