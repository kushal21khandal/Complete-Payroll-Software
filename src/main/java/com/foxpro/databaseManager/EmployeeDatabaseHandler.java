package com.foxpro.databaseManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                System.out.println("_EMP... : database connection established");
            } else {
                System.out.println("_EMP... : connection already open");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("_EMP... : database connection closed.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static void executeCreateMonthTable(String commandPath, String databasePath) {

        try {
            // Runtime.getRuntime().exec(new String[]{
            //     "sqlite3 " + databasePath  , ".read " + commandPath
            // });

            /*
             * the runtime sql commands will not work as after opening the sql will open its own cmd window in which the later commands of .open and .read will not be able to send
             */

            // Runtime.getRuntime().exec(new String[]{
            //     "sqlite3", ".open " + databasePath, ".read " + commandPath
            // });

            String createTableQuery = "CREATE TABLE if not exists data(uan INTEGER PRIMARY KEY ,memberId TEXT NOT NULL ,name TEXT NOT NULL,gender TEXT NOT NULL ,dob TEXT NOT NULL ,doj TEXT NOT NULL ,father_husband_name TEXT NOT NULL,relation TEXT NOT NULL,maritialStatus TEXT NOT NULL ,mobile TEXT ,emailId TEXT ,aadhaar TEXT ,pan TEXT ,bankAccNo_IFSCcode TEXT ,nomination TEXT ,isAadharVerified TEXT  ,totalDays INTEGER DEFAULT 0,attendance INTEGER DEFAULT 0,basic DECIMAL (10,2)  DEFAULT 0,hra DECIMAL (8,2)  DEFAULT 0,convence DECIMAL (5,2)  DEFAULT 0,washingAllowance DECIMAL (5,2)  DEFAULT 0,overtime DECIMAL (5,2) DEFAULT 0.0 ,totalSalary DECIMAL DEFAULT 0,calc_basic DECIMAL (10,2)  DEFAULT 0,calc_hra DECIMAL (10,2)  DEFAULT 0,calc_convence DECIMAL (10,2)  DEFAULT 0,calc_overtime DECIMAL (10,2)  DEFAULT 0,calc_washingAllowance DECIMAL (10,2)  DEFAULT 0,calc_incentive DECIMAL ( 10 , 2) DEFAULT 0,calc_salary DECIMAL ( 10 , 2) DEFAULT 0,msl1 DECIMAL DEFAULT 0,msl2 DECIMAL DEFAULT 0,msl3 DECIMAL DEFAULT 0,pf_salary DECIMAL (10,2)  DEFAULT 0,esic_salary DECIMAL (10,2)  DEFAULT 0,pfDeduction DECIMAL DEFAULT 0 ,esicDeduction DECIMAL DEFAULT 0 ,totalDeduction DECIMAL DEFAULT 0,pfPaidByEmployee DECIMAL DEFAULT 0 ,pfPaidByEmployer DECIMAL DEFAULT 0,netPayableAmount DECIMAL DEFAULT 0)";
            Statement statement = conn.createStatement();
            statement.execute(createTableQuery);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeFillMonthTable(String pathToDb, String pathToPfCSV) {
        // try {
        //     // Runtime.getRuntime().exec(new String[]{
        //     //     "sqlite3 " + pathToDb , ".import --csv --skip 1 " + pathToPfCSV + " data"
        //     // });
        //     // Runtime.getRuntime().exec(new String[]{
        //     //     "sqlite3", ".open " + pathToDb, ".import --csv --skip 1 " + pathToPfCSV + " data"
        //     // });
        //     // Runtime.getRuntime().exec(new String[]{
        //     //     "sqlite3 .open " + pathToDb  + " .import --csv --skip 1 " + pathToPfCSV + " data"
        //     // });
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
            FileReader reader = null;
            String line ;
            String[] lineArr;

            try{
                reader = new FileReader(pathToPfCSV);
                BufferedReader bufferedReader = new BufferedReader(reader);


                // igoring the csv file column headers
                bufferedReader.readLine();
                while(( line = bufferedReader.readLine() ) != null){
                    lineArr = line.split(",");
                    EmployeeDatabaseMain.fillMonthTable(conn , lineArr);
                }
                bufferedReader.close();
            }
            catch(IOException | SQLException exception){
                exception.printStackTrace();
            }
            finally{
                if ( reader != null){
                    try{
                        reader.close();
                    }
                    catch(IOException exception){
                        exception.printStackTrace();
                    }
                }
            }
    }

    public static void printEmployeeDetails(String uanNumber) {
        try {
            EmployeeDatabaseMain.printEmployeeDetails(conn, Integer.parseInt(uanNumber));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long printEmployeeDetailsByName(String name) {
        long uan = 0;
        try {
            uan = EmployeeDatabaseMain.printEmployeeDetailsByName(conn, name);
        } catch (SQLException e) {
            System.out.println("INCORRENT NAME");
            System.out.println(e);
        }
        return uan;
    }

    public static Object getAllEmployeeDetails() {
        Object res = null;
        try {
            res = EmployeeDatabaseMain.getAllEmployeeDetails(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static Object getSumTotal() {
        Object res = null;
        try {
            res = EmployeeDatabaseMain.getSumTotal(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void insertEmployeeDetails(double[] arr, long uan) {
        try {
            EmployeeDatabaseMain.insertEmployeeDetails(conn, arr, uan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
