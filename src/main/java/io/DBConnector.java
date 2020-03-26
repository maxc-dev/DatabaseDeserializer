package io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Max Carter
 * @since 19/03/2020
 */
public class DBConnector {
    private static final String HOST = "jdbc:mysql://localhost:3306/classicmodels", USERNAME = "root", PASSWORD = "";

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
    public DBProcessor connect() {
        try {
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new DBProcessor(connection);
    }
}
