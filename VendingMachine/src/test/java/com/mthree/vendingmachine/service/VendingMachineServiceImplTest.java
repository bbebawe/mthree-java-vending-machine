/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.service;

import com.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author beshoy
 */
public class VendingMachineServiceImplTest {

    VendingMachineService service;

    public VendingMachineServiceImplTest() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        service
                = ctx.getBean("service", VendingMachineService.class);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("Test service to only return list of avialable products with stock > 0")
    public void testGetAllAvailableProducts_toReturnOnlyAvailableProducts() throws VendingMachinePersistenceException {
        assertEquals(3, service.getAllAvailableProducts().size(),
                "should be 3 becasue the test file has 3 products in stock");
    }

    @Test
    @DisplayName("test service to update prodcut stock")
    public void testUpdateStock() throws VendingMachinePersistenceException {
        // load all producst, product id 1 has stock = 1 
        service.getAllAvailableProducts();
        // updateStock is called to reduce stock by 1
        service.updateStock(1);
        assertEquals(2, service.getAllAvailableProducts().size(), "should be 2 because product 1 is now out of stock");
        assertEquals(0, service.getProduct(1).getStock(), "should be 0 because product is out of stock");
    }

    @Test
    @DisplayName("test service to add balance when user puts coins")
    public void testService_toAddBalance() {
        // balance should be 0 when program starts
        assertEquals(new BigDecimal("0"), service.getCoinsBalance(), "should be zero");
        // add balance 
        service.addBalance(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), service.getCoinsBalance(), "should be 100");
    }

    @Test
    @DisplayName("test service to reset balance when program exits")
    public void testService_toResetBalance_whenProgramExits() {
        // balance should be 0 when program start
        service.addBalance(new BigDecimal("100"));
        service.resetBalance();
        assertEquals(new BigDecimal("0"), service.getCoinsBalance(), "should be 0");
    }

    @Test
    @DisplayName("test service to validate entered coin value")
    public void testService_toValidateCoinValue_orThrowException() {
        try {
            service.isValidCoinValue(new BigDecimal("4"));
            fail("should have throw CoinValidationException because machine does not accept 4 as coin value");
        } catch (CoinValidationException e) {
            return;
        }

        try {
            service.isValidCoinValue(new BigDecimal("5"));
        } catch (CoinValidationException e) {
            fail("should have not throw CoinValidationException because machine accept 5 as coin value");
        }
    }

    @Test
    @DisplayName("test service to check if product is in stock")
    public void testService_toCheckIfProductInStock_orThrowException() throws VendingMachinePersistenceException {
        service.updateStock(1);
        try {
            service.isProductInStock(1);
            fail("should have throw NoItemInventoryException because product not in stock");
        } catch (NoItemInventoryException e) {
            return;
        }

        try {
            service.isProductInStock(2);
        } catch (NoItemInventoryException e) {
            fail("should have not throw NoItemInventoryException because product is in stock");
        }
    }

    @Test
    @DisplayName("test service to check if user have enougt balance to buy")
    public void testService_toThrowException_WhenNotEnoughBalance() throws VendingMachinePersistenceException, InsufficientFundsException {
        // add 50p balance 
        service.addBalance(new BigDecimal("50"));
        try {
            // check if enough balance to buy product 2 which is price 100
            service.isSuffecientBalance(2);
            fail("should have throw InsufficientFundsException because balance is 50 and product price is 100");
        } catch (InsufficientFundsException e) {
            return;
        }

        try {
            // add another 5p to balance
            service.addBalance(new BigDecimal("50"));
            service.isSuffecientBalance(2);
        } catch (InsufficientFundsException e) {
            fail("should have not throw InsufficientFundsException because balance is equal to product price");
        }
    }

    @Test
    @DisplayName("test service to return change")
    public void testService_toReturnCalcualte_andReturnChange() throws VendingMachinePersistenceException {
        service.resetBalance();
        // set balance to 130 P
        service.addBalance(new BigDecimal("130"));
        // buy product 2 and update balance
        service.updateBalance(2);
        // return change, should be 20p and 10p
        Map<String, Integer> change = service.returnChange();
        // expected change
        Map<String, Integer> expectedChange = new HashMap<>();
        expectedChange.put(Coin._20P.toString(), 1);
        expectedChange.put(Coin._10P.toString(), 1);
        assertTrue(expectedChange.equals(change));
    }

}
