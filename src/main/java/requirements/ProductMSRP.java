package requirements;

import io.DBConnector;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
@Requirement(code = 'C', position = 3, desc = "List the names of products sold at less than 80% of the MSRP.")
public class ProductMSRP implements ExecutableRequirement {
    private DBConnector dbConnector;

    /**
     * Creates a new ProductMSRP object for requirement C:3
     */
    public ProductMSRP(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void execute() {

    }
}
