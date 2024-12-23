package com.foxpro.databaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstablishmentDatabaseHandler {

    /*
     * connection establishment :
     * initiateMainConnection -> void
     * closeConnection -> void
     */
    private static Connection conn = null;

    private static Connection getConnection(String databasePath) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:" + databasePath);
    }

    public static void initiateConnection(String mainDatabasePath) {
        try {
            if (conn == null) {
                conn = getConnection(mainDatabasePath);
                System.out.println("MAIN : _EST... : database connection established");
            } else {
                System.out.println("MAIN : _EST... : connection already open");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("MAIN : _EST... : database connection closed.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    /*
     * establishment functions :
     * insertEstablishment -> void
     * checkForEstablishment -> boolean
     * getEstablishmentDetails -> HashMap< String , String >
     */
    public static Object getEstablishmentDetails(long pfRegNumber) throws SQLException {
        return EstablishmentDatabaseMain.getEstablishmentDetails(conn, pfRegNumber);

    }

    public static boolean checkForEstablishment(long pfRegNumber) {

        boolean res = false;
        try {
            res = EstablishmentDatabaseMain.checkForEstablishment(conn, pfRegNumber);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public static void insertEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        try {
            EstablishmentDatabaseMain.insertEstablishment(conn, pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        try {
            EstablishmentDatabaseMain.updateEstablishment(conn, pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeEstablishment(long pfRegNumber) {
        try {
            EstablishmentDatabaseMain.removeEstablishment(conn, pfRegNumber);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }



    // salary structure
    public static void updateSalaryStructure( long pfRegNumber ,  double basic , double hra , double convence , double overtime , double washingAllowance , double msl1 , double msl2 , double msl3){
        try {

            EstablishmentDatabaseMain.updateSalaryStructure( conn , pfRegNumber , basic , hra , convence , overtime , washingAllowance , msl1 , msl2 , msl3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Object getEstablishmentSalaryStructureDetails(long pfRegNumber) throws SQLException {
        return EstablishmentDatabaseMain.getEstablishmentSalaryStructureDetails(conn, pfRegNumber);

    }

}
