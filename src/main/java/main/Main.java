package main;

import io.DBConnector;
import requirements.CustomerPayments;
import requirements.ExecutableRequirement;
import requirements.ProductMSRP;
import requirements.TotalPayments;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class Main {
    private void run() {
        //creates a new database connector
        DBConnector dbConnector = new DBConnector();
        if (!dbConnector.connect()) {
            //exit system if the database cannot connect
            return;
        }

        //requirement C:1
        ExecutableRequirement totalPayments = new TotalPayments(dbConnector);
        totalPayments.execute();

        //requirement C:2
        ExecutableRequirement customerPayments = new CustomerPayments(dbConnector);
        customerPayments.execute();

        //requirement C:3
        ExecutableRequirement productMSRP = new ProductMSRP(dbConnector);
        productMSRP.execute();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}
