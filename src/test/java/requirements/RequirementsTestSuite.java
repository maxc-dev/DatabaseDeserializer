package requirements;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Max Carter
 * @since 25/03/2020
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        TotalPaymentsTest.class,
        CustomerPaymentsTest.class,
        ProductMSRPTest.class
})

public class RequirementsTestSuite {}
