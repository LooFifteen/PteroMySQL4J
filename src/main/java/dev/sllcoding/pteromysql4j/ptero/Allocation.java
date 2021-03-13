package dev.sllcoding.pteromysql4j.ptero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Allocation {

    private final int id;
    private final int nodeId;
    private final String ip;
    private final String alias;
    private final int port;
    private final int serverId;
    private final String notes;
    private final Timestamp created;
    private final Timestamp updated;

    public Allocation(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("id");
        nodeId = resultSet.getInt("node_id");
        ip = resultSet.getString("ip");
        alias = resultSet.getString("ip_alias");
        port = resultSet.getInt("port");
        serverId = resultSet.getInt("server_id");
        notes = resultSet.getString("notes");
        created = resultSet.getTimestamp("created_at");
        updated = resultSet.getTimestamp("updated_at");
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public int getServerId() {
        return serverId;
    }

    public String getNotes() {
        return notes;
    }

    public String getAlias() {
        return alias;
    }

    public String getIp() {
        return ip;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

}
