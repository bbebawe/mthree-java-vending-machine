/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachineAuditDao;
import com.mthree.vendingmachine.dao.VendingMachineDao;
import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import com.mthree.vendingmachine.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author beshoy
 */
public class VendingMachineServiceImpl implements VendingMachineService {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    private BigDecimal balance;

    public VendingMachineServiceImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao, BigDecimal balance) {
        this.dao = dao;
        this.auditDao = auditDao;
        this.balance = balance;
    }

    @Override
    public List<Product> getAllAvailableProducts() throws VendingMachinePersistenceException {
        // filter and return products with sotck > 0
        List<Product> availableProducts = dao.getAllProducts().stream()
                .filter((product) -> product.getStock() > 0)
                .collect(Collectors.toList());
        return availableProducts;
    }

    @Override
    public void getAllData() throws VendingMachinePersistenceException {
        dao.loadProducts();
    }

    @Override
    public void writeData() throws VendingMachinePersistenceException {
        dao.saveProducts();
    }

    @Override
    public List<Coin> getCoinsValues() {
        List<Coin> coinsList = new ArrayList<>();
        Arrays.stream(Coin.values()).forEach(
                (coin) -> coinsList.add(coin)
        );
        return coinsList;
    }

    @Override
    public void isValidCoinValue(BigDecimal coinValue) throws CoinValidationException {
        // checks if value is accepted by machine
        if (coinValue.intValue() == 100
                || coinValue.intValue() == 50
                || coinValue.intValue() == 20
                || coinValue.intValue() == 10
                || coinValue.intValue() == 5
                || coinValue.intValue() == 2
                || coinValue.intValue() == 1) {
        } else {
            throw new CoinValidationException(coinValue + " is invalid coin value");
        }
    }

    @Override
    public void addBalance(BigDecimal coin) {
        balance = balance.add(coin);
    }

    @Override
    public BigDecimal getCoinsBalance() {
        return balance;
    }

    @Override
    public void resetBalance() {
        balance = BigDecimal.ZERO;
    }

    @Override
    public List<Integer> getAllProductsIds() throws VendingMachinePersistenceException {
        List<Integer> productsId;
        productsId = getAllAvailableProducts().stream().map((p) -> p.getId()).collect(Collectors.toList());
        return productsId;
    }

    @Override
    public void isValidProduct(int productId) throws VendingMachinePersistenceException, ProductValidationException {
        if (!getAllProductsIds().contains(productId)) {
            throw new ProductValidationException("Invalid Product Id");
        }
    }

    @Override
    public void isProductInStock(int productId) throws VendingMachinePersistenceException, NoItemInventoryException {
        if (getProduct(productId).getStock() == 0) {
            throw new NoItemInventoryException("Sorry, Product is currenlty out of stock");
        }
    }

    @Override
    public Product getProduct(int productId) throws VendingMachinePersistenceException {
        return dao.getProduct(productId);
    }

    @Override
    public void isSuffecientBalance(int productId) throws VendingMachinePersistenceException, InsufficientFundsException {
        // if price greater than balance throw exception
        if (getProduct(productId).getPrice().compareTo(balance) > 0) {
            throw new InsufficientFundsException("Sorry, You don't have enough balance to buy this product");
        }
    }

    @Override
    public void updateStock(int productId) throws VendingMachinePersistenceException {
        Product updatedProduct = getProduct(productId);
        updatedProduct.setStock(updatedProduct.getStock() - 1);
        dao.updateProductStock(updatedProduct);
        writeData();
    }

    @Override
    public void updateBalance(int productId) throws VendingMachinePersistenceException {
        BigDecimal productPrice = getProduct(productId).getPrice();
        balance = balance.subtract(productPrice);
    }

    @Override
    public Map returnChange() {
        return Change.calculateChange(balance);
    }

    @Override
    public void updateAudit(int productId) throws VendingMachinePersistenceException {
        Product product = getProduct(productId);
        auditDao.writeAuditEntry("Product " + product.getId() + " PURCHASED, Updated Stock " + product.getStock());
    }

}
