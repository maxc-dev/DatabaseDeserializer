package io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Max Carter
 * @since 24/03/2020
 */
public class DBProcessor {
    private Connection connection;

    /**
     * Creates a new connection handler.
     *
     * @param connection Connection instance.
     */
    public DBProcessor(Connection connection) {
        if (connection == null) {
            throw new NullPointerException();
        }
        this.connection = connection;
    }

    /**
     * Gets the data from the database with a prepared statement.
     *
     * @param statement statement to execute.
     * @return Results of the connection.
     * @throws SQLException If there is a problem with the connection.
     */
    protected ResultSet getData(String statement) throws SQLException {
        return connection.prepareStatement(statement).executeQuery();
    }
}
