package com.foxpro;

import java.sql.SQLException;

import com.foxpro.databaseManager.EstablishmentDatabaseHandler;
import com.foxpro.fileManager.FileComponentHandler;

class Manager {

    private static final String PATH_MAIN = Config.getPathMain();

    public static void initiateMainConnection() {
        EstablishmentDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN , new String[]{"main.db"}));
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
            FileComponentHandler.createDir(PATH_MAIN , new String[]{
                "data" , pfRegNumber + "", Config.getThisYear() + ""
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
    public static void updateSalaryStructure( long pfRegNumber , double basic , double hra , double convence , double overtime , double washingAllowance , double msl1 , double msl2 , double msl3){
        EstablishmentDatabaseHandler.updateSalaryStructure(pfRegNumber ,basic , hra , convence , overtime , washingAllowance , msl1 , msl2 , msl3);
    }
    public static Object getEstablishmentSalaryStructureDetails(long pfRegNumber) throws  SQLException{
        return EstablishmentDatabaseHandler.getEstablishmentSalaryStructureDetails(pfRegNumber);
    }
}
