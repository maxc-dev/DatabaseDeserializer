package requirements;

import java.util.List;

import io.DBConnector;
import models.Payment;
import models.serial.IllegalModifierException;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 1, desc = "Report total payments for October 28, 2004.")
public class TotalPayments implements ExecutableRequirement {
    private DBConnector dbConnector;

    /**
     * Creates a new TotalPayments object for requirement C:1
     */
    public TotalPayments(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void execute() {
        try {
            List<Payment> payments = dbConnector.deserialize(new Payment(), Table.PAYMENTS);
        } catch (IllegalModifierException ex) {
            ex.printStackTrace();
        }
    }
}
