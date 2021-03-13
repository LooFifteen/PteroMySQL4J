package dev.sllcoding.pteromysql4j.ptero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DatabaseHost {

    private final int id;
    private final String name;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    // TODO: Replace password with api gotten password.
    private final int maxDatabases;
    private final int nodeId;
    private final Timestamp created;
    private final Timestamp updated;

    public DatabaseHost(ResultSet results) throws SQLException {
        id = results.getInt("id");
        name = results.getString("name");
        host = results.getString("host");
        port = results.getInt("port");
        username = results.getString("username");
        password = results.getString("password");
        maxDatabases = results.getInt("max_databases");
        nodeId = results.getInt("node_id");
        created = results.getTimestamp("created_at");
        updated = results.getTimestamp("updated_at");
    }

    public String getUsername() {
        return username;
    }

    @Deprecated
    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public int getPort() {
        return port;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getName() {
        return name;
    }

    public int getMaxDatabases() {
        return maxDatabases;
    }

    public String getHost() {
        return host;
    }

}
