
package com.foxpro;

import java.sql.SQLException;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;
import com.foxpro.databaseManager.EstablishmentDatabaseHandler;
import com.foxpro.fileManager.FileComponentHandler;


class Manager {

    private static final String PATH_MAIN = Config.getPathMain();


    public static void initiateMainConnection() {
        EstablishmentDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"main.db"}));
    }

    public static void closeMainConnection() {
        EstablishmentDatabaseHandler.closeConnection();
    }

    public static Object getEstablishmentDetails(long pfRegNumber) throws SQLException {
        return EstablishmentDatabaseHandler.getEstablishmentDetails(pfRegNumber);
    }

    // Establishment
    public static void createEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {

        if (EstablishmentDatabaseHandler.checkForEstablishment(pfRegNumber) == true) {
            System.out.println("establihsment already created");
        } else {
            EstablishmentDatabaseHandler.insertEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", Config.getThisYear() + ""
            });
        }

    }

    public static void updateEstablishment(long pfRegNumber, long esicRegNumber, long phoneNumber, String companyName, String ownerName, String address, String dateOfPfRegistration, String dateOfEsicRegistration) {
        EstablishmentDatabaseHandler.updateEstablishment(pfRegNumber, esicRegNumber, phoneNumber, companyName, ownerName, address, dateOfPfRegistration, dateOfEsicRegistration);
    }

    public static void removeEstablishment(long pfRegNumber) {
        /* no need for removing a establishment , but if this feature necesary , have to delete the entire folder also of that establishment */
        EstablishmentDatabaseHandler.removeEstablishment(pfRegNumber);
    }

    // salary structure
    public static void updateSalaryStructure(long pfRegNumber, double basic, double hra, double convence, double overtime, double washingAllowance, double msl1, double msl2, double msl3) {
        EstablishmentDatabaseHandler.updateSalaryStructure(pfRegNumber, basic, hra, convence, overtime, washingAllowance, msl1, msl2, msl3);
    }

    public static Object getEstablishmentSalaryStructureDetails(long pfRegNumber) throws SQLException {
        return EstablishmentDatabaseHandler.getEstablishmentSalaryStructureDetails(pfRegNumber);
    }

    // employees
    public static void checkAndCreateDir(long pfRegNumber, int year, String month, String regionOptional) {

        if (regionOptional == null) {

            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month
            });
            FileComponentHandler.createFile(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, month + ".db"
            });
        } else {
            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, regionOptional
            });
            FileComponentHandler.createFile(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, regionOptional, month + ".db"
            });

        }

    }

    public static void initiateEmployeesConnection(long pfRegNumber, int year, String month, String region_optional) {

        if (region_optional != null) {
            EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, region_optional, month + ".db"
            }));

        } else {
            EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, month + ".db"
            }));

        }

    }

    public static void closeEmployeeConnection() {
        EmployeeDatabaseHandler.closeConnection();
    }

    public static void executeCreateTableCommand(long pfRegNumber, int year, String month, String region_optional) {
        if (region_optional != null) {
            EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, region_optional, month + ".db"}));
        } else {
            EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, month + ".db"}));

        }
    }

    public static void executeFillTableCommand(long pfRegNumber, int year, String month, String region_optional, String pathToPfCSVFile) {
        if (region_optional != null) {
            EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, region_optional, month + ".db"}), pathToPfCSVFile);
        } else {
            EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, month + ".db"}), pathToPfCSVFile);
        }
    }

}
