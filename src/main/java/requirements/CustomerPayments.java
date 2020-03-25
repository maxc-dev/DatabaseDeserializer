package requirements;

import java.util.HashMap;
import java.util.Map;

import io.DBUtils;
import io.Deserializer;
import models.Customer;
import models.Payment;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 2, desc = "List the amount paid by each customer.")
public class CustomerPayments implements ExecutableRequirement<Map<String, Double>> {
    private Payment[] payments;
    private Customer[] customers;

    public CustomerPayments(Payment[] payments, Customer[] customers) {
        this.payments = payments;
        this.customers = customers;
    }

    @Override
    public Map<String, Double> execute(Deserializer deserializer, DBUtils dbUtils) {
        Map<String, Double> crossCustomerPaymentMap = new HashMap<>();

        //loops through all customers and payments
        for (Customer customer : customers) {
            for (Payment payment : payments) {
                //if the customer number == payments customer number
                if (customer.customerNumber == payment.customerNumber) {
                    //if customer is unique add to hash map, if not update keys value
                    if (!crossCustomerPaymentMap.containsKey(customer.customerName)) {
                        crossCustomerPaymentMap.put(customer.customerName, payment.amount);
                    } else {
                        crossCustomerPaymentMap.put(customer.customerName, (crossCustomerPaymentMap.get(customer.customerName) + payment.amount));
                    }
                }
            }
        }

        //returns a map of each customer -> total payment
        return crossCustomerPaymentMap;
    }
}
