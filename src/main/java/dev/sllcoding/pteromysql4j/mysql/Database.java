package dev.sllcoding.pteromysql4j.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private final MySQLServer server;
    private final String database;

    private Connection connection;

    public Database(MySQLServer server, String database) {
        this.server = server;
        this.database = database;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + server.getUrl() + ":" + server.getPort() + "/" + this.database + "?autoReconnect=true", server.getUsername(), server.getPassword());
        }
        return connection;
    }

    public boolean exists(String statement) throws SQLException {
        return getConnection().prepareStatement(statement).execute();
    }

    public ResultSet get(String statement) throws SQLException {
        return getConnection().prepareStatement(statement).executeQuery();
    }

    public int update(String statement) throws SQLException {
        return getConnection().prepareStatement(statement).executeUpdate();
    }

    public String getDatabase() {
        return database;
    }

    public MySQLServer getServer() {
        return server;
    }

    public Table getTable(String name) throws SQLException {
        if (server.exists("SELECT * FROM TABLES WHERE TABLE_SCHEMA='" + database + "' AND TABLE_NAME='" + name + "'")) {
            return new Table(name, this);
        }
        return null;
    }

    public Table createTable(String name, Column firstColumn) throws SQLException {
        int rowsUpdated = update("CREATE TABLE " + name + " (" + firstColumn.getName() + " " + firstColumn.getType() + ")");
        if (rowsUpdated > 0) {
            return new Table(name, this);
        }
        throw new SQLException("No rows were changed, check your permissions are correct.");
    }

    public Table createTable(String name, Column firstColumn, Column... columns) throws SQLException {
        StringBuilder statement = new StringBuilder("CREATE TABLE " + name + " (" + firstColumn.getName() + " " + firstColumn.getType());
        for (Column column : columns) {
            statement.append(", ").append(column.getName()).append(" ").append(column.getType());
        }
        statement.append(")");
        int rowsUpdated = update(statement.toString());
        if (rowsUpdated > 0) {
            return new Table(name, this);
        }
        throw new SQLException("No rows were changed, check your permissions are correct.");
    }

}
