package dev.sllcoding.pteromysql4j.ptero;

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import dev.sllcoding.pteromysql4j.mysql.Data;
import dev.sllcoding.pteromysql4j.mysql.Database;
import dev.sllcoding.pteromysql4j.ptero.auth.Account;
import dev.sllcoding.pteromysql4j.ptero.auth.Application;
import dev.sllcoding.pteromysql4j.ptero.auth.Client;
import dev.sllcoding.pteromysql4j.ptero.utils.RequestManager;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Panel {

    private final Database database;
    private final String url;
    private final RequestManager requestManager;
    private final PteroApplication application;
    private final PteroClient client;

    public Panel(Database database, String url, Application application, Client client) {
        this.database = database;
        if (url.endsWith("/")) {
            this.url = url.substring(url.length() - 1);
        } else {
            this.url = url;
        }
        this.requestManager = new RequestManager(TimeUnit.SECONDS, 4);
        this.application = PteroBuilder.createApplication(url, application.getKey());
        this.client = PteroBuilder.createClient(url, client.getKey());
    }

    public Database getDatabase() {
        return database;
    }

    public String getUrl() {
        return url;
    }

    public Server getServerByIdentifier(String identifier) throws SQLException {
        ResultSet result = getDatabase().getTable("servers").where("uuidShort", "=", identifier).get();
        if (result.isFirst()) {
            return new Server(this, result);
        }
        return null;
    }

    public Server getServerById(int id) throws SQLException {
        ResultSet resultSet = getDatabase().getTable("servers").where("id", "=", id).get();
        if (resultSet.isFirst()) {
            return new Server(this, resultSet);
        }
        return null;
    }

    public List<Server> getServers() throws SQLException {
        ResultSet resultSet = getDatabase().get("SELECT * FROM servers");
        List<Server> servers = new ArrayList<>();
        if (resultSet.isFirst()) {
            servers.add(new Server(this, resultSet));
            while (resultSet.next()) {
                servers.add(new Server(this, resultSet));
            }
        }
        return servers;
    }

    public void getRequest(String endpoint, Account account, Consumer<HttpResponse<JsonNode>> success) {
        requestManager.get(this, endpoint, account, success);
    }

    public void postRequest(String endpoint, Account account, Consumer<HttpResponse<JsonNode>> success, Data... data) {
        requestManager.post(this, endpoint, account, success, data);
    }

    private static String removeLast(String text, String regex) {
        StringBuilder builder = new StringBuilder();
        int start = builder.lastIndexOf(regex);
        builder.append(text, 0, start);
        builder.append(text.substring(start + regex.length()));
        return builder.toString();
    }

    public PteroApplication getApplication() {
        return application;
    }

    public PteroClient getClient() {
        return client;
    }

}
