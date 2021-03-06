package util.sql;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public enum Table {
    PAYMENTS("payments"),
    CUSTOMERS("customers"),
    PRODUCTS("products"),
    ORDER_DETAILS("orderdetails");

    private String tableName;

    Table(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return this.tableName;
    }
}
