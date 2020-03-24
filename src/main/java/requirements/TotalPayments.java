package requirements;

import io.DBUtils;
import io.Deserializer;
import models.Payment;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 1, desc = "Report total payments for October 28, 2004.")
public class TotalPayments implements ExecutableRequirement {
    private static final String SQL_DATE = "2004-10-28";

    private Payment[] payments;

    public TotalPayments(Payment[] payments) {
        this.payments = payments;
    }

    @Override
    public Object execute(Deserializer deserializer, DBUtils dbUtils) {
        //loop through all payments
        int paymentCount = 0;
        for (Payment payment : payments) {
            //if payment date as lazy string equals SQL_DATE, increase payment counter
            if (payment.paymentDate.toString().startsWith(SQL_DATE)) {
                paymentCount += payment.amount;
            }
        }
        return paymentCount;
    }
}
