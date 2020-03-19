package requirements;

import java.util.List;

import io.DBConnector;
import models.Customer;
import models.serial.IllegalModifierException;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 2, desc = "List the amount paid by each customer.")
public class CustomerPayments implements ExecutableRequirement {
    private DBConnector dbConnector;

    /**
     * Creates a new CustomerPayments object for requirement C:2
     */
    public CustomerPayments(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void execute() {
        List<Customer> customers = null;
        try {
            customers = dbConnector.deserialize(new Customer(), Table.CUSTOMERS);
        } catch (IllegalModifierException ex) {
            ex.printStackTrace();
        }
        if (customers == null) {
            return;
        }
        for (Customer customer : customers) {
            //TODO("cross examine against payments")
        }
    }
}
