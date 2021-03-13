package dev.sllcoding.pteromysql4j.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLServer {

    private final String url;
    private final String username;
    private final String password;
    private final int port;
    private final boolean useSSL;

    private Connection connection;

    public MySQLServer(String url, String username, String password) {
        this(url, 3306, username, password);
    }

    public MySQLServer(String url, int port, String username, String password) {
        this(url, port, username, password, false);
    }

    public MySQLServer(String url, String username, String password, boolean useSSL) {
        this(url, 3306, username, password, useSSL);
    }

    public MySQLServer(String url, int port, String username, String password, boolean useSSL) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
        this.useSSL = useSSL;
    }

    public Connection getInformation() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + this.url + ":" + this.port + "/INFORMATION_SCHEMA", this.username, this.password);
        }
        return connection;
    }

    public boolean exists(String statement) throws SQLException {
        return getInformation().prepareStatement(statement).execute();
    }

    public int update(String statement) throws SQLException {
        return getInformation().prepareStatement(statement).executeUpdate();
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    public boolean usesSSL() {
        return useSSL;
    }

    public String getPassword() {
        return password;
    }

}
