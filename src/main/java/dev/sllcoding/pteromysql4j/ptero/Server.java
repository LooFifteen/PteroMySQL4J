package dev.sllcoding.pteromysql4j.ptero;

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class Server {

    private final Panel panel;

    private final int id;
    private final Object externalId;
    private final UUID uuid;
    private final String identifier;
    private final int nodeId;
    private final String name;
    private final String description;
    private final boolean skipsScripts;
    private final boolean suspended;
    private final int ownerId;
    private final int memory;
    private final int swap;
    private final int disk;
    private final int io;
    private final int cpu;
    private final boolean oomDisabled;
    private final int allocationId;
    private final int nestId;
    private final int eggId;
    private final String startupCommand;
    private final String image;
    private final boolean installed;
    private final int allocationLimit;
    private final int databaseLimit;
    private final Timestamp created;
    private final Timestamp updated;

    public Server(Panel panel, ResultSet results) throws SQLException {
        this.panel = panel;

        id = results.getInt("id");
        externalId = results.getObject("external_id");
        uuid = UUID.fromString(results.getString("uuid"));
        identifier = results.getString("uuidShort");
        nodeId = results.getInt("node_id");
        name = results.getString("name");
        description = results.getString("description");
        skipsScripts = results.getBoolean("skip_scripts");
        suspended = results.getBoolean("suspended");
        ownerId = results.getInt("owner_id");
        memory = results.getInt("memory");
        swap = results.getInt("swap");
        disk = results.getInt("disk");
        io = results.getInt("io");
        cpu = results.getInt("cpu");
        oomDisabled = results.getBoolean("oom_disabled");
        allocationId = results.getInt("allocation_id");
        nestId = results.getInt("nest_id");
        eggId = results.getInt("egg_id");
        startupCommand = results.getString("startup");
        image = results.getString("image");
        installed = results.getBoolean("installed");
        allocationLimit = results.getInt("allocation_limit");
        databaseLimit = results.getInt("database_limit");
        created = results.getTimestamp("created_at");
        updated = results.getTimestamp("updated_at");
    }

    public void getApplicationController(Consumer<ApplicationServer> consumer) {
        panel.getApplication().retrieveServerById(getId()).executeAsync(consumer);
    }

    public void getClientController(Consumer<ClientServer> consumer) {
        panel.getClient().retrieveServerByIdentifier(getIdentifier()).executeAsync(consumer);
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public int getAllocationLimit() {
        return allocationLimit;
    }

    public int getCpuCores() {
        return cpu;
    }

    public int getDatabaseLimit() {
        return databaseLimit;
    }

    public int getDiskSpace() {
        return disk;
    }

    public int getEggId() {
        return eggId;
    }

    public int getId() {
        return id;
    }

    public int getIo() {
        return io;
    }

    public int getMemory() {
        return memory;
    }

    public int getNestId() {
        return nestId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getSwap() {
        return swap;
    }

    public Object getExternalId() {
        return externalId;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getStartupCommand() {
        return startupCommand;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isInstalled() {
        return installed;
    }

    public boolean isOomDisabled() {
        return oomDisabled;
    }

    public boolean doesSkipScripts() {
        return skipsScripts;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public Allocation getAllocation() throws SQLException {
        ResultSet results = panel.getDatabase().getTable("allocations").where("id", "=", allocationId).where("server_id", "=", id).get();
        if (results.isFirst()) {
            return new Allocation(results);
        }
        return null;
    }

    public List<ServerDatabase> getDatabases() throws SQLException {
        ResultSet databases = panel.getDatabase().getTable("databases").where("server_id", "=", id).get();
        List<ServerDatabase> finishedDatabases = new ArrayList<>();
        if (databases.isFirst()) {
            finishedDatabases.add(new ServerDatabase(databases, panel));

            while (databases.next()) {
                finishedDatabases.add(new ServerDatabase(databases, panel));
            }
        }
        return finishedDatabases;
    }

    public List<Backup> getBackups() throws SQLException {
        ResultSet results = panel.getDatabase().getTable("backups").where("server_id", "=", id).get();
        List<Backup> backups = new ArrayList<>();
        if (results.isFirst()) {
            backups.add(new Backup(results));
            while (results.next()) {
                backups.add(new Backup(results));
            }
        }
        return backups;
    }
}
