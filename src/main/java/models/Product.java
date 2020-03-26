package models;

import models.serial.Deserializable;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 24/03/2020
 */
public class Product {
    public static final int DEFAULT_PRICE = 0;
    public static final int DEFAULT_MSRP = 0;
    public static final int DEFAULT_QUANTITY = 0;

    @Deserializable
    public double MSRP = DEFAULT_MSRP, buyPrice = DEFAULT_PRICE;

    @Deserializable
    public String productCode = null,
            productName = null,
            productScale = null,
            productVendor = null,
            productDescription = null,
            productLine = null;

    @Deserializable
    public int quantityInStock = DEFAULT_QUANTITY;

    public static Product[] asList(int size) {
        Product[] products = new Product[size];

        for (int i = 0; i < products.length; i++) {
            products[i] = new Product();
        }
        return products;
    }
}
