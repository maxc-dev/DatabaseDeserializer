package requirements;

import org.junit.Test;

import io.DBConnector;
import io.DBProcessor;
import io.DBUtils;
import io.Deserializer;
import models.Payment;
import models.serial.IllegalModifierException;
import util.sql.InconsistentTableSizeException;
import util.sql.Table;

import static org.junit.Assert.*;

/**
 * @author Max Carter
 * @since 25/03/2020
 */
public class TotalPaymentsTest {
    @Test
    public void testExecute() {
        //init database connection
        DBConnector dbConnector = new DBConnector();
        DBProcessor dbProcessor = dbConnector.connect();
        DBUtils dbUtils = new DBUtils(dbProcessor);
        Deserializer deserializer = new Deserializer(dbProcessor, dbUtils);

        final String clause = "WHERE paymentDate = \"2004-10-28\"";

        //create actual and expected list with pre-populated empty objects
        Payment[] paymentsActual = Payment.asList(dbUtils.getTableSize(Table.PAYMENTS));
        Payment[] paymentsExpected = Payment.asList(dbUtils.getTableSize(Table.PAYMENTS, clause));
        try {
            //populate lists with deserialized objects
            paymentsActual = deserializer.deserializeList(paymentsActual, Table.PAYMENTS);
            paymentsExpected = deserializer.deserializeList(paymentsExpected, Table.PAYMENTS, clause);
        } catch (IllegalModifierException | InconsistentTableSizeException ex) {
            ex.printStackTrace();
        }

        int totalPaymentsActual = new TotalPayments(paymentsActual).execute(deserializer, dbUtils);
        int totalPaymentsExpects = new TotalPayments(paymentsExpected).execute(deserializer, dbUtils);

        assertEquals(totalPaymentsExpects, totalPaymentsActual);
    }
}