package com.foxpro.databaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class EstablishmentDatabaseMain {


    private static boolean checkForTable(Connection conn , String tableName , long pfRegNumber) throws  SQLException{
        String query = "SELECT COUNT(*) as count FROM ? WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, tableName);
        preparedStatement.setLong(2, pfRegNumber);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next(); // first always true

        return resultSet.next();
    }

    static boolean checkForEstablishment(Connection conn, long pfRegNumber) throws SQLException {
        return checkForTable(conn, "establishments", pfRegNumber);
    }

    static boolean checkForSalaryStructure(Connection conn , long pfRegNumber) throws SQLException{
        return checkForTable(conn, "salaryStructure", pfRegNumber);
    }

    static void insertEstablishment(Connection conn, long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) throws SQLException {
        String query = "INSERT longO establishments VALUES ( ? , ? , ? , ? , ? , ? , ? , ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, pfRegNumber);
        preparedStatement.setLong(2, esicRegNumber);
        preparedStatement.setString(3, companyName);
        preparedStatement.setString(4, ownerName);
        preparedStatement.setLong(5, phoneNumber);
        preparedStatement.setString(6, address);
        preparedStatement.setString(7, dateOfPfRegistration);
        preparedStatement.setString(8, dateOfEsicRegistration);

        preparedStatement.executeUpdate();
    }

    static void updateEstablishment(Connection conn, long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) throws SQLException {
        String query = "UPDATE establishments SET esicRegNumber = ? , companyName = ? , ownerName = ? , phoneNumber = ? , address = ? ,dateOfPfRegistration = ? , dateOfEsicRegistration = ? where pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, esicRegNumber);
        preparedStatement.setString(2, companyName);
        preparedStatement.setString(3, ownerName);
        preparedStatement.setLong(4, phoneNumber);
        preparedStatement.setString(5, address);
        preparedStatement.setString(6, dateOfPfRegistration);
        preparedStatement.setString(7, dateOfEsicRegistration);

        preparedStatement.setLong(8, pfRegNumber);
        preparedStatement.executeUpdate();

    }

    static void removeEstablishment(Connection conn, long pfRegNumber) throws SQLException {
        String query = "DELETE FROM establishments WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, pfRegNumber);
        preparedStatement.executeUpdate();
    }

    static ResultSet getEstablishmentDetails(Connection conn, long pfRegNumber) throws SQLException {
        String query = "SELECT * FROM establishments WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, pfRegNumber);

        return preparedStatement.executeQuery();
    }


    // salary structure
    // pf reg no. nhi daala
    public static void updateSalaryStructure( Connection conn , long pfRegNumber , double basic , double hra , double convence , double overtime , double washingAllowance , double msl1 , double msl2 , double msl3) throws SQLException{

        // updates or inserts entry
        String query = "REPLACE INTO salaryStructure(pfRegNumber , basic , hra , convence , overtime , washingAllowance , msl1 , msl2 , msl3 , lastModifiedOn) VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, pfRegNumber);
        preparedStatement.setDouble(2, basic);
        preparedStatement.setDouble(3, hra);
        preparedStatement.setDouble(4, convence);
        preparedStatement.setDouble(5, overtime);
        preparedStatement.setDouble(6, washingAllowance);
        preparedStatement.setDouble(7, msl1);
        preparedStatement.setDouble(8, msl2);
        preparedStatement.setDouble(9, msl3);
        preparedStatement.setString(10,  DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()));

        preparedStatement.executeUpdate();
    }
    static ResultSet getEstablishmentSalaryStructureDetails(Connection conn, long pfRegNumber) throws SQLException {
        String query = "SELECT * FROM salaryStructure WHERE pfRegNumber = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setLong(1, pfRegNumber);

        return preparedStatement.executeQuery();
    }
}
