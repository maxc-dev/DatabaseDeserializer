package io;

import java.sql.ResultSet;
import java.sql.SQLException;

import util.sql.SQLBuilder;
import util.sql.SQLUtils;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 24/03/2020
 */
public class DBUtils {
    private DBProcessor dbProcessor;

    public DBUtils(DBProcessor dbProcessor) {
        this.dbProcessor = dbProcessor;
    }

    /**
     * Gets the size of an SQL table. Cannot use COUNT() in SQL
     * so gets all the data from a table and returns the index
     * of the last row.
     *
     * @param table Table to use.
     * @return Size of a table. if <code>SQLUtil.ERROR_CODE</code>
     * is returned then there has been an internal SQL error.
     */
    public int getTableSize(Table table) {
        ResultSet rs;
        try {
            rs = dbProcessor.getData(SQLBuilder.getAllFromTable(table));
            rs.last();
            return rs.getRow();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //default sql error code return
        return SQLUtils.ERROR_CODE;
    }
}
