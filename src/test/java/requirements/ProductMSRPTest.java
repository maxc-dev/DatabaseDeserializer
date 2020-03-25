package requirements;

import org.junit.Test;

import java.util.List;

import io.DBConnector;
import io.DBProcessor;
import io.DBUtils;
import io.Deserializer;
import models.Payment;
import models.Product;
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

        final String clause = "WHERE buyPrice < MSRP*0.8";

        //create actual and expected list with pre-populated empty objects
        Product[] productsActual = Product.asList(dbUtils.getTableSize(Table.PRODUCTS));
        Product[] productsExpected = Product.asList(dbUtils.getTableSize(Table.PRODUCTS, clause));
        try {
            //populate lists with deserialized objects
            productsActual = deserializer.deserializeList(productsActual, Table.PRODUCTS);
            productsExpected = deserializer.deserializeList(productsExpected, Table.PRODUCTS, clause);
        } catch (IllegalModifierException | InconsistentTableSizeException ex) {
            ex.printStackTrace();
        }

        List<String> totalPaymentsActual = new ProductMSRP(productsActual).execute(deserializer, dbUtils);
        List<String> totalPaymentsExpects = new ProductMSRP(productsExpected).execute(deserializer, dbUtils);

        assertEquals(totalPaymentsExpects, totalPaymentsActual);
    }
}