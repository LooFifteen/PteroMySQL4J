package dev.sllcoding.pteromysql4j.mysql;

public class Data {

    private final String key;
    private final Object data;

    public Data(String key, Object data) {
        this.data = data;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Object getData() {
        return data;
    }

}
