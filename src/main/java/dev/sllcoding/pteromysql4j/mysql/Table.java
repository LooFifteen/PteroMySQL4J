package dev.sllcoding.pteromysql4j.mysql;

import dev.sllcoding.pteromysql4j.exceptions.NoConditionException;
import dev.sllcoding.pteromysql4j.exceptions.UnspecifiedColumnException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Table {

    private final String name;
    private final Database database;

    private String query;
    private boolean needsWhere = true;
    private boolean where = false;
    private boolean needsSet = false;
    private boolean set = false;
    private boolean needsValues = false;
    private boolean values = false;
    private boolean needsOn = false;
    private boolean on = false;

    public Table(String name, Database database) {
        this.name = name;
        this.database = database;

        query = "FROM `" + name + "`";
    }

    public String getName() {
        return name;
    }

    public Database getDatabase() {
        return database;
    }

    public Table where(String column, String comparator, Object value){
        if (where){
            query += " AND " + column + comparator + convertToSQLString(value);
        } else {
            query += " WHERE " + column + comparator + convertToSQLString(value);
        }

        where = true;
        return this;
    }

    public Table values(String key, Object value){

        if (values){
            String left = query.split(" VALUES ")[0];
            String right = query.split(" VALUES ")[1];
            left += ", " + key;
            right += ", " + convertToSQLString(value);
            query = left + " VALUES " + right;
        }

        else{
            query += " (" + key +" VALUES (" + convertToSQLString(value);
        }
        values = true;
        return this;
    }

    public Table values(Object... values1){
        query += " VALUES (";
        int i = 0;
        for (Object value : values1) {
            if (i - 1 == values1.length) {
                query = query + convertToSQLString(value);
            } else {
                query = query + convertToSQLString(value) + ", ";
            }
            i++;
        }
        values = true;
        return this;
    }

    public Table set(String column, Object value){
        if (set){
            query += ",";
        }

        else{
            query += " SET";
        }

        query += " " + column + "=" + convertToSQLString(value);

        set = true;
        return this;
    }

    public ResultSet get() throws SQLException {
        CheckForCondition();
        query = "SELECT * " + query;
        //System.out.println("[DEBUG] " + query);
        ResultSet resultSet = database.get(query);
        try {
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean exists() throws SQLException {
        CheckForCondition();
        query = "SELECT * " + query;
        System.out.println("[DEBUG] " + query);
        ResultSet resultSet = database.get(query);
        try {
            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String convertToSQLString(Object object){
        if (object == null){
            return "null";
        }
        else if (object instanceof String){
            return "'" + object.toString() + "'";
        }
        else if (object instanceof Date){
            return "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)object) + "'";
        }
        else if (object instanceof Boolean){
            return (Boolean)object ? "1" : "0";
        }
        return object.toString();
    }

    private void CheckForCondition(){
        if (needsWhere && !where){
            throw new NoConditionException("where");
        }

        if (needsOn && !on){
            throw new NoConditionException("on");
        }

        if (needsSet && !set){
            throw new NoConditionException("set");
        }

        if (needsValues && !values){
            throw new NoConditionException("values");
        }
    }

    private void CheckForColumnSpecification(String column){
        if (column.split("\\.").length != 2){
            throw new UnspecifiedColumnException(column);
        }
    }

}
