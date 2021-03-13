package dev.sllcoding.pteromysql4j.ptero.utils;

public class Response<E> {

    private final boolean success;
    private final E response;

    public Response(boolean success, E response) {
        this.success = success;
        this.response = response;
    }

    public Response(boolean success) {
        this.success = success;
        this.response = null;
    }

    public E getResponse() {
        return response;
    }

    public boolean isSuccessful() {
        return success;
    }

}
