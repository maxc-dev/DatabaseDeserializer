package requirements;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.DBConnector;
import io.DBProcessor;
import io.DBUtils;
import io.Deserializer;
import models.OrderDetail;
import models.Product;
import models.serial.Deserializable;
import models.serial.IllegalModifierException;
import util.sql.InconsistentTableSizeException;
import util.sql.Table;

import static org.junit.Assert.*;

public class ProductMSRPTest {
    @Test
    public void execute() {
        //init database connection
        DBConnector dbConnector = new DBConnector();
        DBProcessor dbProcessor = dbConnector.connect();
        DBUtils dbUtils = new DBUtils(dbProcessor);
        Deserializer deserializer = new Deserializer(dbProcessor, dbUtils);

        final String clause = "JOIN products ON products.productCode = orderdetails.productCode WHERE priceEach < MSRP*0.8";

        //create actual and expected list with pre-populated empty objects
        Product[] productsActual = Product.asList(dbUtils.getTableSize(Table.PRODUCTS));
        OrderDetail[] orderDetailsActual = OrderDetail.asList(dbUtils.getTableSize(Table.ORDER_DETAILS));
        OrderDetailsDeserialized[] orderDetailsExpected = OrderDetailsDeserialized.asList(dbUtils.getTableSize(Table.ORDER_DETAILS, clause));
        try {
            //populate lists with deserialized objects
            productsActual = deserializer.deserializeList(productsActual, Table.PRODUCTS);
            orderDetailsActual = deserializer.deserializeList(orderDetailsActual, Table.ORDER_DETAILS);
            orderDetailsExpected = deserializer.deserializeList(orderDetailsExpected, Table.ORDER_DETAILS, clause);
        } catch (IllegalModifierException | InconsistentTableSizeException ex) {
            ex.printStackTrace();
        }

        List<String> productListActual = new ProductMSRP(productsActual, orderDetailsActual).execute(deserializer, dbUtils);
        List<String> productListExpected = new OrderDetailsExecutor(orderDetailsExpected).execute(deserializer, dbUtils);

        assertEquals(productListExpected, productListActual);
    }

    /*
    The same thing in CustomerPaymentsTest.java is happening here,
    we're essentially using the API I made to create a custom
    deserialized table from the database from the join statement
    specified in the clause.
     */

    public static class OrderDetailsDeserialized {
        @Deserializable
        public String productName = null;

        @Deserializable
        public double priceEach = 0, MSRP = 0;

        public static ProductMSRPTest.OrderDetailsDeserialized[] asList(int size) {
            ProductMSRPTest.OrderDetailsDeserialized[] customerPayments = new ProductMSRPTest.OrderDetailsDeserialized[size];

            for (int i = 0; i < customerPayments.length; i++) {
                customerPayments[i] = new ProductMSRPTest.OrderDetailsDeserialized();
            }
            return customerPayments;
        }
    }

    private static final class OrderDetailsExecutor implements ExecutableRequirement<List<String>> {
        private ProductMSRPTest.OrderDetailsDeserialized[] transactions;

        public OrderDetailsExecutor(ProductMSRPTest.OrderDetailsDeserialized[] transactions) {
            this.transactions = transactions;
        }

        @Override
        public List<String> execute(Deserializer deserializer, DBUtils dbUtils) {
            List<String> crossCustomerPaymentMap = new ArrayList<>();

            //loops through all customers and payments
            for (ProductMSRPTest.OrderDetailsDeserialized transaction : transactions) {
                if (!crossCustomerPaymentMap.contains(transaction.productName)) {
                    crossCustomerPaymentMap.add(transaction.productName);
                }
            }

            //returns a map of each customer -> total payment
            return crossCustomerPaymentMap;
        }
    }
}