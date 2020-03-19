package io;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.serial.IllegalModifierException;
import models.serial.Serializable;
import util.sql.SQLBuilder;
import util.sql.Table;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class DBConnector {
    private final String HOST = "", USERNAME = "", PASSWORD = "";

    private Connection connection = null;

    /**
     * Creates the database connector.
     */
    public DBConnector() {
    }

    /**
     * Attempts to establish a connection to the database
     *
     * @return False by default or in the case of an exception.
     * A return value of true indicates that the connection
     * request was a success.
     */
    public boolean connect() {
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return connection != null;
    }

    /**
     * Takes in a generic type and a table.
     *
     * @param type  Class reference for deserializer
     * @param table Name of the SQL table
     * @param <T>   List return type
     * @return List of objects belonging to T
     * @throws IllegalModifierException If one of the Serial fields of T is not public
     */
    public <T> List<T> deserialize(T type, Table table) throws IllegalModifierException {
        ResultSet set = null;
        try {
            set = getData(SQLBuilder.getAllFromTable(table));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (set == null) {
            return null;
        }
        //loop fields in class
        for (Field field : type.getClass().getDeclaredFields()) {
            //ignore fields if they're not serialized
            if (field.getAnnotation(Serializable.class) == null) {
                continue;
            }
            //throw exception if the field is not public (otherwise cant be changed)
            if (!Modifier.isPublic(field.getModifiers())) {
                throw new IllegalModifierException(Modifier.toString(field.getModifiers()));
            }
            /*TODO(replace constants with result set columns&values)
             */
            if (field.getName().equals("customerNumber")) {
                try {
                    field.set(type, 3);
                    System.out.println(field.get(type));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        //default null return for now
        return null;
    }

    /**
     * Gets the data from the database with a prepared statement
     *
     * @param statement statement to execute
     * @return Results of the connection
     * @throws SQLException If there is a problem with the connection
     */
    private ResultSet getData(String statement) throws SQLException {
        return connection.prepareStatement(statement).executeQuery();
    }
}
