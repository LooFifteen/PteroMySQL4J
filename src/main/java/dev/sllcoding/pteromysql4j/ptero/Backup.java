package dev.sllcoding.pteromysql4j.ptero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class Backup {

    private final int id;
    private final int serverId;
    private final UUID uuid;
    private final boolean successful;
    private final String name;
    private final String ignoredFiles;
    private final String disk;
    private final String checksum;
    private final long bytes;
    private final Timestamp completed;
    private final Timestamp created;
    private final Timestamp updated;
    private final Timestamp deleted;

    public Backup(ResultSet results) throws SQLException {
        id = results.getInt("id");
        serverId = results.getInt("server_id");
        uuid = UUID.fromString(results.getString("uuid"));
        successful = results.getBoolean("is_successful");
        name = results.getString("name");
        ignoredFiles = results.getString("ignored_files");
        disk = results.getString("disk");
        checksum = results.getString("checksum");
        bytes = results.getLong("bytes");
        completed = results.getTimestamp("completed_at");
        created = results.getTimestamp("created_at");
        updated = results.getTimestamp("updated_at");
        deleted = results.getTimestamp("deleted_at");
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
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

    public long getBytes() {
        return bytes;
    }

    public String getChecksum() {
        return checksum;
    }

    public String getDisk() {
        return disk;
    }

    public String getIgnoredFiles() {
        return ignoredFiles;
    }

    public Timestamp getCompleted() {
        return completed;
    }

    public Timestamp getDeleted() {
        return deleted;
    }

}
