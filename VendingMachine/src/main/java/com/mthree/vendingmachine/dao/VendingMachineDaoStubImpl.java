/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author beshoy
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    private Map<Integer, Product> products = new HashMap<>();

    ;

    public VendingMachineDaoStubImpl() {
        Product testProduct1 = new Product(1, "coco cola", new BigDecimal("100"), 1);
        Product testProduct2 = new Product(2, "pepsi", new BigDecimal("100"), 5);
        Product testProduct3 = new Product(3, "fanta", new BigDecimal("100"), 5);
        products.put(testProduct1.getId(), testProduct1);
        products.put(testProduct2.getId(), testProduct3);
        products.put(testProduct3.getId(), testProduct3);
    }

    @Override
    public List<Product> getAllProducts() throws VendingMachinePersistenceException {
        return new ArrayList<Product>(products.values());
    }

    @Override
    public Product addProduct(Product product) throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateProductStock(Product product) throws VendingMachinePersistenceException {
        products.put(product.getId(), product);
    }

    @Override
    public Product getProduct(int productId) throws VendingMachinePersistenceException {
        return products.get(productId);
    }

    @Override
    public void loadProducts() throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product unmarshallProduct(String productAsText) throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String marshallProduct(Product product) throws VendingMachinePersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveProducts() throws VendingMachinePersistenceException {
    }

}
