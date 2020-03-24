package models;

import models.serial.Deserializable;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class Customer {
    /**
     * ID of a customer without an ID (default value)
     */
    public static final int NO_CUSTOMER_ID = -1;

    @Deserializable
    public int customerNumber = NO_CUSTOMER_ID, salesRepEmployeeNumber;

    @Deserializable
    public double creditLimit = 0;

    @Deserializable
    public String customerName = null,
            contactLastName = null,
            contactFirstName = null,
            phone = null,
            addressLine1 = null,
            addressLine2 = null,
            city = null,
            state = null,
            postalCode = null,
            country = null;

    @Deserializable
    public Object customerLocation = null;

    public static Customer[] asList(int size) {
        Customer[] customers = new Customer[size];

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer();
        }
        return customers;
    }
}
