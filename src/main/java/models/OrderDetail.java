package models;

import models.serial.Deserializable;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 26/03/2020
 */
public class OrderDetail {
    public static final int NO_KEY = -1;
    public static final int DEFAULT_ORDER_LINE = 0;

    @Deserializable
    public int orderNumber = NO_KEY,
            quantityOrdered = Product.DEFAULT_QUANTITY,
            orderLineNumber = DEFAULT_ORDER_LINE;

    @Deserializable
    public String productCode = null;

    @Deserializable
    public double priceEach = Product.DEFAULT_PRICE;

    public static OrderDetail[] asList(int size) {
        OrderDetail[] orderDetails = new OrderDetail[size];

        for (int i = 0; i < orderDetails.length; i++) {
            orderDetails[i] = new OrderDetail();
        }
        return orderDetails;
    }
}
