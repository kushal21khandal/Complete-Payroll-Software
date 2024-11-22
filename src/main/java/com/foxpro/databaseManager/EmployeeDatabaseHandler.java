package com.foxpro.databaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EmployeeDatabaseHandler {

    private static Connection conn = null;

    private static Connection getConnection(String databasePath) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:" + databasePath);
    }

    public static void initiateConnection(String mainDatabasePath) {
        try {
            if (conn == null) {
                conn = getConnection(mainDatabasePath);
                System.out.println("database connection established");
            } else {
                System.out.println("connection already open");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("database connection closed.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }


    public static void execute(String commandPath , String databasePath){
        try {
            Runtime.getRuntime().exec(new String[]{
                "sqlite3 " + databasePath  , ".read " + commandPath
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
