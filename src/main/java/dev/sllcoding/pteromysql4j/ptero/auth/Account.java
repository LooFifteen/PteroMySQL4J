package dev.sllcoding.pteromysql4j.ptero.auth;

public class Account {

    private final String key;

    public Account(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
