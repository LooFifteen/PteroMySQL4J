package dev.sllcoding.pteromysql4j.ptero;

import dev.sllcoding.pteromysql4j.ptero.auth.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.function.Consumer;

public class ServerDatabase {

    private final Panel panel;

    private final int id;
    private final int serverId;
    private final int databaseHostId;
    private final String database;
    private final String username;
    private final String remote;
    private final Timestamp created;
    private final Timestamp updated;

    public ServerDatabase(ResultSet results, Panel panel) throws SQLException {
        id = results.getInt("id");
        serverId = results.getInt("server_id");
        databaseHostId = results.getInt("database_host_id");
        database = results.getString("database");
        username = results.getString("username");
        remote = results.getString("remote");
        created = results.getTimestamp("created_at");
        updated = results.getTimestamp("updated_at");
        this.panel = panel;
    }

    public String getRemote() {
        return remote;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public Timestamp getCreated() {
        return created;
    }

    public int getServerId() {
        return serverId;
    }

    public int getId() {
        return id;
    }

    public void getPassword(Application account, Consumer<String> response) {
        panel.getRequest("/api/application/servers/" + serverId + "/databases/" + id + "?include=password", account, r -> response.accept(r.getBody().getObject().getJSONObject("attributes").getJSONObject("relationships").getJSONObject("password").getJSONObject("attributes").getString("password")));
    }

    public String getUsername() {
        return username;
    }

    public String getDatabase() {
        return database;
    }

    public int getDatabaseHostId() {
        return databaseHostId;
    }

}
