package dev.sllcoding.pteromysql4j.exceptions;

public class TableDoesntExistException extends Exception {

    public TableDoesntExistException(String message) {
        super(message);
    }

}
