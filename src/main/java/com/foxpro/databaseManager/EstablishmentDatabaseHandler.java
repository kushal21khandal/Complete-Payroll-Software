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

    /*
     * establishment functions :
     * insertEstablishment -> void
     * checkForEstablishment -> boolean
     * getEstablishmentDetails -> HashMap< String , String >
     */
    public static Object getEstablishmentDetails(int pfRegNumber) throws SQLException {
        return EstablishmentDatabaseMain.getEstablishmentDetails(conn, pfRegNumber);

    }

    public static boolean checkForEstablishment(int pfRegNumber) {

        boolean res = false;
        try {
            res = EstablishmentDatabaseMain.checkForEstablishment(conn, pfRegNumber);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public static void insertEstablishment(int pfRegNumber, int esicRegNumber, int phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        try {
            EstablishmentDatabaseMain.insertEstablishment(conn, pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEstablishment(int pfRegNumber, int esicRegNumber, int phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        try {
            EstablishmentDatabaseMain.updateEstablishment(conn, pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeEstablishment(int pfRegNumber) {
        try {
            EstablishmentDatabaseMain.removeEstablishment(conn, pfRegNumber);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
