package requirements;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import io.DBConnector;
import io.DBProcessor;
import io.DBUtils;
import io.Deserializer;
import models.Customer;
import models.Payment;
import models.serial.Deserializable;
import models.serial.IllegalModifierException;
import util.sql.InconsistentTableSizeException;
import util.sql.Table;

import static org.junit.Assert.*;

public class CustomerPaymentsTest {

    @Test
    public void execute() {
        //init database connection
        DBConnector dbConnector = new DBConnector();
        assertNotEquals(dbConnector, null);

        DBProcessor dbProcessor = dbConnector.connect();
        assertNotEquals(dbProcessor, null);

        DBUtils dbUtils = new DBUtils(dbProcessor);
        assertNotEquals(dbUtils, null);

        Deserializer deserializer = new Deserializer(dbProcessor, dbUtils);
        assertNotEquals(deserializer, null);

        final String clause = " JOIN `customers` WHERE `payments`.`customerNumber` = `customers`.`customerNumber`";

        //create actual and expected list with pre-populated empty objects
        Payment[] paymentsActual = Payment.asList(dbUtils.getTableSize(Table.PAYMENTS));
        Customer[] customersActual = Customer.asList(dbUtils.getTableSize(Table.CUSTOMERS));
        CustomerPaymentDeserialized[] paymentCustomerExpected = CustomerPaymentDeserialized.asList(dbUtils.getTableSize(Table.PAYMENTS, clause));
        try {
            //populate lists with deserialized objects
            customersActual = deserializer.deserializeList(customersActual, Table.CUSTOMERS);
            paymentsActual = deserializer.deserializeList(paymentsActual, Table.PAYMENTS);
            paymentCustomerExpected = deserializer.deserializeList(paymentCustomerExpected, Table.PAYMENTS, clause);
        } catch (IllegalModifierException | InconsistentTableSizeException ex) {
            ex.printStackTrace();
        }

        Map<String, Double> customerPaymentsActual = new CustomerPayments(paymentsActual, customersActual).execute(deserializer, dbUtils);
        Map<String, Double> customerPaymentsExpected = new CustomerPaymentExecutor(paymentCustomerExpected).execute(deserializer, dbUtils);

        assertEquals(customerPaymentsExpected, customerPaymentsActual);
    }

    /*

    What I have done here, is I have created a new deserialized class
    called [CustomerPaymentDeserialized] whose attributes are derived from
    calling the SQL statement in the test method with the clause appended.

     */
    public static class CustomerPaymentDeserialized {
        @Deserializable
        public String customerName = null;

        @Deserializable
        public double amount = 0;

        public static CustomerPaymentDeserialized[] asList(int size) {
            CustomerPaymentDeserialized[] customerPayments = new CustomerPaymentDeserialized[size];

            for (int i = 0; i < customerPayments.length; i++) {
                customerPayments[i] = new CustomerPaymentDeserialized();
            }
            return customerPayments;
        }
    }

    /*

    This class is essentially the same as CustomerPayments.java except
    it uses the above class instead of separate lists for Customer.java
    and Payment.java. The functionality is the same however in the execute()
    method because it returns a list of the same type.

     */
    private static final class CustomerPaymentExecutor implements ExecutableRequirement<HashMap<String, Double>> {
        private CustomerPaymentDeserialized[] transactions;

        public CustomerPaymentExecutor(CustomerPaymentDeserialized[] transactions) {
            this.transactions = transactions;
        }

        @Override
        public HashMap<String, Double> execute(Deserializer deserializer, DBUtils dbUtils) {
            HashMap<String, Double> crossCustomerPaymentMap = new HashMap<>();

            //loops through all customers and payments
            for (CustomerPaymentDeserialized transaction : transactions) {
                if (!crossCustomerPaymentMap.containsKey(transaction.customerName)) {
                    crossCustomerPaymentMap.put(transaction.customerName, transaction.amount);
                } else {
                    crossCustomerPaymentMap.put(transaction.customerName, (crossCustomerPaymentMap.get(transaction.customerName) + transaction.amount));
                }
            }

            //returns a map of each customer -> total payment
            return crossCustomerPaymentMap;
        }
    }

}