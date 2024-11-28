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


    public static void executeCreateMonthTable(String commandPath , String databasePath){
        try {
            Runtime.getRuntime().exec(new String[]{
                "sqlite3 " + databasePath  , ".read " + commandPath
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executeFillMonthTable(String pathToDb , String pathToPfCSV){
        try {
            Runtime.getRuntime().exec(new String[]{
                "sqlite3 " + pathToDb , ".import --csv --skip 1 " + pathToPfCSV + " data"
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printEmployeeDetails(String uanNumber){
        try {
            EmployeeDatabaseMain.printEmployeeDetails( conn ,Integer.parseInt(uanNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printEmployeeDetailsByName(String name){
        try {
            EmployeeDatabaseMain.printEmployeeDetailsByName(conn , name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Object getAllEmployeeDetails(){
        Object res = null;
        try {
            res = EmployeeDatabaseMain.getAllEmployeeDetails(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void insertEmployeeDetails(double[] arr , long uan){
        try {
           EmployeeDatabaseMain.insertEmployeeDetails( conn ,arr, uan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
