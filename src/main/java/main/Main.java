package main;

import io.DBConnector;
import io.DBProcessor;
import io.DBUtils;
import io.Deserializer;
import models.Customer;
import models.Payment;
import models.Product;
import models.serial.IllegalModifierException;
import requirements.CustomerPayments;
import requirements.ExecutableRequirement;
import requirements.ProductMSRP;
import requirements.TotalPayments;
import util.sql.InconsistentTableSizeException;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class Main {
    /**
     * Demo method that runs all the requirements.
     */
    private void run() {
        //creates a new database connector
        DBConnector dbConnector = new DBConnector();
        DBProcessor dbProcessor = dbConnector.connect();
        DBUtils dbUtils = new DBUtils(dbProcessor);
        Deserializer deserializer = new Deserializer(dbProcessor, dbUtils);

        //creating pre-generic-object-populated arrays
        Payment[] payments = Payment.asList(dbUtils.getTableSize(Table.PAYMENTS));
        Customer[] customers = Customer.asList(dbUtils.getTableSize(Table.CUSTOMERS));
        Product[] products = Product.asList(dbUtils.getTableSize(Table.PRODUCTS));

        //deserialize data from database into the arrays
        try {
            payments = deserializer.deserializeList(payments, Table.PAYMENTS);
            customers = deserializer.deserializeList(customers, Table.CUSTOMERS);
            products = deserializer.deserializeList(products, Table.PRODUCTS);
        } catch (IllegalModifierException | InconsistentTableSizeException ex) {
            ex.printStackTrace();
        }

        //initiates all requirements and executes them
        for (ExecutableRequirement requirement : new ExecutableRequirement[]{
                new TotalPayments(payments), new CustomerPayments(payments, customers), new ProductMSRP(products)
        }) {
            requirement.execute(deserializer, dbUtils);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}
