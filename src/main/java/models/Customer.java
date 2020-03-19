package models;

import models.serial.Serializable;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class Customer {
    /**
     * ID of a customer without an ID (default value)
     */
    public static final int NO_CUSTOMER_ID = -1;

    @Serializable
    public int customerNumber = NO_CUSTOMER_ID, salesRepEmployeeNumber;
    @Serializable
    public double creditLimit = 0;

    @Serializable
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

    /**
     * Generates empty customer
     */
    public Customer() {}

    /**
     * Generates a customer with non empty fields
     */
    public Customer(int customerNumber, int salesRepEmployeeNumber, double creditLimit, String customerName, String contactLastName, String contactFirstName, String phone, String addressLine1, String addressLine2, String city, String state, String postalCOde, String country) {
        this.customerNumber = customerNumber;
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
        this.creditLimit = creditLimit;
        this.customerName = customerName;
        this.contactLastName = contactLastName;
        this.contactFirstName = contactFirstName;
        this.phone = phone;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCOde;
        this.country = country;
    }

}
