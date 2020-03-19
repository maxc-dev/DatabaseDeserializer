package models;

import java.util.Date;

import models.serial.Serializable;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class Payment {
    @Serializable
    public int customerNumber = Customer.NO_CUSTOMER_ID, checkNumber = -1;

    @Serializable
    public Date paymentDate = null;

    @Serializable
    public double amount = 0;

    /**
     * Generates empty payment
     */
    public Payment() {}

    /**
     *
     * @param customerNumber Reference to the customer ID
     * @param checkNumber Check number of payment
     * @param paymentDate Date of payment transaction
     * @param amount The amount procesed in the payment
     */
    public Payment(int customerNumber, int checkNumber, Date paymentDate, double amount) {
        this.customerNumber = customerNumber;
        this.checkNumber = checkNumber;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }
}
