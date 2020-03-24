package models;

import java.sql.Timestamp;

import models.serial.Deserializable;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class Payment {
    @Deserializable
    public int customerNumber = Customer.NO_CUSTOMER_ID;

    @Deserializable
    public String checkNumber = null;

    @Deserializable
    public Timestamp paymentDate = null;

    @Deserializable
    public double amount = 0;

    public static Payment[] asList(int size) {
        Payment[] payments = new Payment[size];

        for (int i = 0; i < payments.length; i++) {
            payments[i] = new Payment();
        }
        return payments;
    }
}
