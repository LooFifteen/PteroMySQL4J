package dev.sllcoding.pteromysql4j.ptero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Egg {

    private final Panel panel;

    private final int id;
    private final UUID uuid;
    private final int nestId;
    private final String author;
    private final String name;
    private final String description;
    private final String dockerImage;
    private final String configFiles;
    private final String configStartup;
    private final String configLogs;
    private final String configStop;
    private final int configFrom;
    private final String startup;
    private final String scriptContainer;
    private final int copyScriptFrom;
    private final String scriptEntry;
    private final boolean scriptIsPrivileged;
    private final String scriptInstall;
    private final Timestamp created;
    private final Timestamp updated;

    public Egg(Panel panel, ResultSet results) throws SQLException {
        this.panel = panel;

        id = results.getInt("id");
        uuid = UUID.fromString(results.getString("uuid"));
        nestId = results.getInt("nest_id");
        author = results.getString("author");
        name = results.getString("name");
        description = results.getString("description");
        dockerImage = results.getString("docker_image");
        configFiles = results.getString("config_files");
        configStartup = results.getString("config_startup");
        configLogs = results.getString("config_logs");
        configStop = results.getString("config_stop");
        configFrom = results.getInt("config_from");
        startup = results.getString("startup");
        scriptContainer = results.getString("script_container");
        copyScriptFrom = results.getInt("copy_script_from");
        scriptEntry = results.getString("script_container");
        scriptIsPrivileged = results.getBoolean("script_is_privileged");
        scriptInstall = results.getString("script_install");
        created = results.getTimestamp("created_at");
        updated = results.getTimestamp("updated_at");
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNestId() {
        return nestId;
    }

    public int getConfigFrom() {
        return configFrom;
    }

    public int getCopyScriptFrom() {
        return copyScriptFrom;
    }

    public String getAuthor() {
        return author;
    }

    public String getConfigFiles() {
        return configFiles;
    }

    public String getConfigLogs() {
        return configLogs;
    }

    public String getConfigStartup() {
        return configStartup;
    }

    public String getConfigStop() {
        return configStop;
    }

    public String getDockerImage() {
        return dockerImage;
    }

    public String getScriptContainer() {
        return scriptContainer;
    }

    public String getScriptEntry() {
        return scriptEntry;
    }

    public String getScriptInstall() {
        return scriptInstall;
    }

    public String getStartup() {
        return startup;
    }

    public boolean isScriptPrivileged() {
        return scriptIsPrivileged;
    }

    public List<Variable> getVariables() throws SQLException {
        ResultSet results = panel.getDatabase().getTable("egg_variable").where("egg_id", "=", id).get();
        List<Variable> variables = new ArrayList<>();
        if (results.isFirst()) {
            variables.add(new Variable(results));
            while (results.next()) {
                variables.add(new Variable(results));
            }
        }
        return variables;
    }

    public static class Variable {

        private final int id;
        private final int eggId;
        private final String name;
        private final String description;
        private final String envVariable;
        private final String defaultValue;
        private final boolean userViewable;
        private final boolean userEditable;
        private final String rules;
        private final Timestamp created;
        private final Timestamp updated;

        public Variable(ResultSet results) throws SQLException {
            id = results.getInt("id");
            eggId = results.getInt("egg_id");
            name = results.getString("name");
            description = results.getString("description");
            envVariable = results.getString("env_variable");
            defaultValue = results.getString("default_value");
            userViewable = results.getBoolean("user_viewable");
            userEditable = results.getBoolean("user_editable");
            rules = results.getString("rules");
            created = results.getTimestamp("created_at");
            updated = results.getTimestamp("updated_at");
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public Timestamp getUpdated() {
            return updated;
        }

        public Timestamp getCreated() {
            return created;
        }

        public int getEggId() {
            return eggId;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public String getEnvVariable() {
            return envVariable;
        }

        public String getRules() {
            return rules;
        }

        public boolean isUserEditable() {
            return userEditable;
        }

        public boolean isUserViewable() {
            return userViewable;
        }

    }

}
