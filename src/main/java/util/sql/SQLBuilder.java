package util.sql;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class SQLBuilder {
    /**
     * Constructs an SQL string
     * @param table Name of the SQL Table
     * @return An SQL string that will pull everything from a table
     */
    public static String getAllFromTable(Table table) {
        return "SELECT * FROM " + table.toString();
    }
}
