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
public class CustomerPayments implements ExecutableRequirement {
    private Payment[] payments;
    private Customer[] customers;

    public CustomerPayments(Payment[] payments, Customer[] customers) {
        this.payments = payments;
        this.customers = customers;
    }

    @Override
    public Object execute(Deserializer deserializer, DBUtils dbUtils) {
        Map<Customer, Double> crossCustomerPaymentMap = new HashMap<>();

        //loops through all customers and payments
        for (Customer customer : customers) {
            for (Payment payment : payments) {
                //if the customer number == payments customer number
                if (customer.customerNumber == payment.customerNumber) {
                    //if customer is unique add to hash map, if not update keys value
                    if (!crossCustomerPaymentMap.containsKey(customer)) {
                        crossCustomerPaymentMap.put(customer, payment.amount);
                    } else {
                        crossCustomerPaymentMap.put(customer, (crossCustomerPaymentMap.get(customer) + payment.amount));
                    }
                }
            }
        }

        //returns a map of each customer -> total payment
        return crossCustomerPaymentMap;
    }
}
