package com.foxpro.databaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

class EstablishmentDatabaseMain {

    private static Logger logging = Logger.getLogger(EstablishmentDatabaseMain.class.getName());

    static boolean checkForEstablishment(Connection conn, int pfRegNumber) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM establishments WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, pfRegNumber);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); // first always true

        return resultSet.next();
    }

    static void insertEstablishment(Connection conn, int pfRegNumber, int esicRegNumber, int phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) throws SQLException {
        String query = "INSERT INTO establishments VALUES ( ? , ? , ? , ? , ? , ? , ? , ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, pfRegNumber);
        preparedStatement.setInt(2, esicRegNumber);
        preparedStatement.setString(3, companyName);
        preparedStatement.setString(4, ownerName);
        preparedStatement.setInt(5, phoneNumber);
        preparedStatement.setString(6, address);
        preparedStatement.setString(7, dateOfPfRegistration);
        preparedStatement.setString(8, dateOfEsicRegistration);

        preparedStatement.executeUpdate();
    }

    static void updateEstablishment(Connection conn, int pfRegNumber, int esicRegNumber, int phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) throws SQLException {
        String query = "UPDATE establishments SET esicRegNumber = ? , companyName = ? , ownerName = ? , phoneNumber = ? , address = ? ,dateOfPfRegistration = ? , dateOfEsicRegistration = ? where pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, esicRegNumber);
        preparedStatement.setString(2, companyName);
        preparedStatement.setString(3, ownerName);
        preparedStatement.setInt(4, phoneNumber);
        preparedStatement.setString(5, address);
        preparedStatement.setString(6, dateOfPfRegistration);
        preparedStatement.setString(7, dateOfEsicRegistration);

        preparedStatement.setInt(8, pfRegNumber);
        preparedStatement.executeUpdate();

    }

    static void removeEstablishment(Connection conn, int pfRegNumber) throws SQLException {
        String query = "DELETE FROM establishments WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, pfRegNumber);
        preparedStatement.executeUpdate();
    }

    static ResultSet getEstablishmentDetails(Connection conn, int pfRegNumber) throws SQLException {
        String query = "SELECT * FROM establishments WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, pfRegNumber);

        return preparedStatement.executeQuery();
    }
}
