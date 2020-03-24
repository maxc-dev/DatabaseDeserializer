package util.sql;

/**
 * @author Max Carter
 * @since 24/03/2020
 */
public class InconsistentTableSizeException extends Exception {
    public InconsistentTableSizeException(int tableSizeProvided, int actualTableSize) {
        super("The table size provided [" + tableSizeProvided + "] is inconsistent with the amount of records in the database [" + actualTableSize + "].");
    }
}
