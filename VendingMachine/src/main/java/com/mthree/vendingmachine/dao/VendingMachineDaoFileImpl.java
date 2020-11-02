/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.dao;

import com.mthree.vendingmachine.dto.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author beshoy
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    private Map<Integer, Product> products;
    private String productsFile;
    private String delimiter;

    public VendingMachineDaoFileImpl(Map<Integer, Product> products, String productsFile, String delimiter) {
        this.products = products;
        this.productsFile = productsFile;
        this.delimiter = delimiter;
    }
    
    
    @Override
    public void loadProducts() throws VendingMachinePersistenceException {
        Scanner scanner;
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(productsFile)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load products data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // holds the most recent product unmarshalled
        Product currentProduct;
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a product
            currentProduct = unmarshallProduct(currentLine);
            // Put product into the map using product id as the key
            products.put(currentProduct.getId(), currentProduct);
        }
        // close scanner
        scanner.close();
    }

    @Override
    public Product unmarshallProduct(String productAsText) throws VendingMachinePersistenceException {
        String[] productTokens = productAsText.split(delimiter);
        Product productFromFile = new Product();
        // index 0 - id
        productFromFile.setId(Integer.parseInt(productTokens[0]));
        // index 1 - name
        productFromFile.setName(productTokens[1]);
        // index 2 - price
        productFromFile.setPrice(new BigDecimal(productTokens[2]));
        // index 3 stock
        productFromFile.setStock(Integer.parseInt(productTokens[3]));
        // We have now created a student! Return it!
        return productFromFile;
    }

    @Override
    public String marshallProduct(Product product) throws VendingMachinePersistenceException {
        // create String to represnt dvd object
        String productAsText = product.getId() + delimiter;
        productAsText += product.getName() + delimiter;
        productAsText += product.getPrice() + delimiter;
        productAsText += product.getStock() + delimiter;
        // dvd object as string
        return productAsText;
    }

    @Override
    public void saveProducts() throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            File file = new File(productsFile);
            out = new PrintWriter(file);
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save products data.", e);
        }
        // create list of products objects to be wrriten to the file
        List<Product> productsList = new ArrayList(products.values());
        productsList.stream().forEach((p) -> {
            try {
                String productAsText = marshallProduct(p);
                out.println(productAsText);
                out.flush();
            } catch (VendingMachinePersistenceException ex) {
                System.out.println("Could not save products data.");
            }
        });
        // Clean up
        out.close();
    }

    @Override
    public List<Product> getAllProducts() throws VendingMachinePersistenceException {
        loadProducts();
        return new ArrayList<Product>(products.values());
    }

    @Override
    public Product addProduct(Product product) throws VendingMachinePersistenceException {
        Product newProduct = products.put(product.getId(), product);
        saveProducts();
        return newProduct;
    }

    @Override
    public void updateProductStock(Product product) throws VendingMachinePersistenceException {
        products.put(product.getId(), product);
    }

    @Override
    public Product getProduct(int productId) throws VendingMachinePersistenceException {
        return products.get(productId);
    }
}
