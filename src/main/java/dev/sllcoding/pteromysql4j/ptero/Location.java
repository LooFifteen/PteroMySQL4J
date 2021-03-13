package dev.sllcoding.pteromysql4j.ptero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Location {

    private final int id;
    private final String shortName;
    private final String longName;
    private final Timestamp created;
    private final Timestamp updated;

    public Location(ResultSet results) throws SQLException {
        id = results.getInt("id");
        shortName = results.getString("short");
        longName = results.getString("long");
        created = results.getTimestamp("created_at");
        updated = results.getTimestamp("updated_at");
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public int getId() {
        return id;
    }

    public String getLongName() {
        return longName;
    }

    public String getShortName() {
        return shortName;
    }

}
