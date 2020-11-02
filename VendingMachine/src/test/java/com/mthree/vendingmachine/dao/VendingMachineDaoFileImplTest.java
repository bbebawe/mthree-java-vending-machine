/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author beshoy
 */
@DisplayName("Test Vending Machine File Implementation")
public class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;

    public VendingMachineDaoFileImplTest() {

    }

    @BeforeAll
    public static void classSetUp() {
    }

    @AfterAll
    public static void classCleanUp() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        // setup testDao class
        String testFile = "testProducts.txt";
        Map<Integer, Product> products = new HashMap<>();
        String delimetar = "::";
        // creates new file each time
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(products, testFile, delimetar);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("test dao to return empty list when no products available")
    public void testDao_toReturnEmptyList_whenNoProducts() throws VendingMachinePersistenceException {
        assertTrue(testDao.getAllProducts().isEmpty(), "should be true because no products in file");
    }

    @Test
    @DisplayName("test dao to return products list from file")
    public void testDao_toReturnProductList_fromFile() throws VendingMachinePersistenceException {
        // set up products
        Product testProduct1 = new Product(1, "coco cola", new BigDecimal("100"), 5);
        Product testProduct2 = new Product(2, "pepsi", new BigDecimal("100"), 5);
        Product testProduct3 = new Product(3, "fanta", new BigDecimal("100"), 5);
        // add products to list 
        testDao.addProduct(testProduct1);
        testDao.addProduct(testProduct2);
        testDao.addProduct(testProduct3);
        // save products to file
        testDao.saveProducts();
        // assert 
        assertDoesNotThrow(() -> testDao.saveProducts(), "should save products without throwing exception");
        assertDoesNotThrow(() -> testDao.loadProducts(), "should load products without throwing exception");
        assertFalse(testDao.getAllProducts().isEmpty(), "should be false becuase file has three products");
        assertEquals(3, testDao.getAllProducts().size());
    }

    @Test
    @DisplayName("test dao to save unmarshall products correctly to hashmap")
    public void testDao_toUnmarshallLoadedProducts_toHashmap() throws VendingMachinePersistenceException {
        // set up products
        Product testProduct1 = new Product(1, "coco cola", new BigDecimal("100"), 5);
        Product testProduct2 = new Product(2, "pepsi", new BigDecimal("100"), 5);
        Product testProduct3 = new Product(3, "fanta", new BigDecimal("100"), 5);
        // add products to list 
        testDao.addProduct(testProduct1);
        testDao.addProduct(testProduct2);
        testDao.addProduct(testProduct3);
        // save products to file
        testDao.saveProducts();
        // assert 
        assertNotNull(testDao.getAllProducts().get(0), "should not be null because file has 3 products");
        assertEquals(testProduct1, testDao.getAllProducts().get(0), "products should match as they have same data");
        assertEquals(testProduct2, testDao.getAllProducts().get(1), "products should match as they have same data");
        assertEquals(testProduct3, testDao.getAllProducts().get(2), "products should match as they have same data");
    }

    @Test
    @DisplayName("test dao to update product stock")
    public void testDao_toUpdateProductStock() throws VendingMachinePersistenceException {
        // set up products
        Product testProduct1 = new Product(1, "coco cola", new BigDecimal("100"), 5);
        Product testProduct2 = new Product(2, "pepsi", new BigDecimal("100"), 5);
        // add products to list 
        testDao.addProduct(testProduct1);
        testDao.addProduct(testProduct2);
        // save products to file
        testDao.saveProducts();

        // change product data 
        testProduct1.setStock(4);
        testDao.updateProductStock(testProduct1);
        testDao.saveProducts();

        // assert 
        assertDoesNotThrow(() -> testDao.getAllProducts().get(0), "should not throw exception because file has products");
        assertEquals(4, testDao.getAllProducts().get(0).getStock(), "should be 4 because product stock was updated from 5 to 4");
        assertEquals(5, testDao.getAllProducts().get(1).getStock(), "should be 5 becuase the stock was never updated");
    }
}
