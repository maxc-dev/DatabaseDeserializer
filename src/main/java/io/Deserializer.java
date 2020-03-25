package io;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import models.serial.Deserializable;
import models.serial.IllegalModifierException;
import util.sql.InconsistentTableSizeException;
import util.sql.SQLBuilder;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 24/03/2020
 */
public class Deserializer {
    private DBProcessor dbProcessor;
    private DBUtils dbUtils;

    public Deserializer(DBProcessor dbProcessor, DBUtils dbUtils) {
        this.dbProcessor = dbProcessor;
        this.dbUtils = dbUtils;
    }

    /**
     * Calls deserializeList without an appending clause.
     */
    public <T> T[] deserializeList(T[] results, Table table) throws IllegalModifierException, InconsistentTableSizeException {
        return deserializeList(results, table, "");
    }

    /**
     * Requires a pre-populated array of an object, then it returns
     * the same array with parsed data from the specified table.
     *
     * @param results Class reference for deserializer
     * @param table   Name of the SQL table
     * @param clause  An additional clause to the sql statement
     * @param <T>     List return results
     * @return List of objects belonging to T
     * @throws IllegalModifierException       If one of the Serial fields of T is not public
     * @throws InconsistentTableSizeException When the size of the results is inconsistent
     *                                        with the size of the table
     */
    public <T> T[] deserializeList(T[] results, Table table, String clause) throws IllegalModifierException, InconsistentTableSizeException {
        ResultSet rs;
        try {
            rs = dbProcessor.getData(SQLBuilder.getAllFromTable(table) + " " + clause);
            ResultSetMetaData metaData = rs.getMetaData();
            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String name = metaData.getColumnName(i);
                    for (Field field : results[rowCount].getClass().getDeclaredFields()) {
                        //ignore fields if they're not serialized
                        if (field.getAnnotation(Deserializable.class) == null) {
                            continue;
                        }
                        //throw exception if the field is not public (otherwise cant be changed)
                        if (!Modifier.isPublic(field.getModifiers())) {
                            throw new IllegalModifierException(Modifier.toString(field.getModifiers()));
                        }
                        //adds a valid field to the results
                        if (field.getName().equals(name) && rs.getObject(field.getName()) != null) {
                            results[rowCount].getClass().getDeclaredField(field.getName()).set(results[rowCount], rs.getObject(field.getName()));
                        }
                    }
                }
                rowCount++;
            }
            //this is usually resulted by referring the wrong util.sql.Table when populating the list
            if (rowCount < results.length - 1) {
                throw new InconsistentTableSizeException(results.length, dbUtils.getTableSize(table));
            }
            return results;

        } catch (SQLException | NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        //default null return
        return null;
    }

}
