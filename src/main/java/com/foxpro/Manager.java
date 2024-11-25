package com.foxpro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;
import com.foxpro.databaseManager.EstablishmentDatabaseHandler;
import com.foxpro.fileManager.FileComponentHandler;

class Manager {

    static class GenerateReport {



        private static String getConsolidatedReportInnerFormat( int employeeIndex , long esicRegNumber , String employeeName , double basic , double hra , double conv , double washingAllowance , double hardDuty , double totalWithoutReduction , double pfSalary , double esicAdvance , double pfDeduction , double totalDeduction , double netPayableAmount ,String fahtersName , double esicSalary , int pfAccNo , int daysWorked , int actualDays , int complementaryDays , double calcBasic , double calcHra , double calcConv , double calcWashing , double calcHardDuty , double calcTotal , long uanNo , double incentive){
            return String.format(" %d   %d      %s              %f    %f    %f    %f    %f          %f    %f       %f     %f  -          %f      %f\n                     %s                                                                 %f      -       -     -\n               %d %d=%d        +  %d      %f    %f     %f    %f    %f          %f                            -\n%d           0.0                                         %f\n                                                      0    %f\n _________________________________________________________________________________________________________________________________________________________\n"
            , employeeIndex , esicRegNumber , employeeName , basic ,hra , conv , washingAllowance , hardDuty , totalWithoutReduction , pfSalary , esicAdvance , pfDeduction , totalDeduction , netPayableAmount , fahtersName , esicSalary , pfAccNo , daysWorked , actualDays , complementaryDays , calcBasic , calcHra , calcConv , calcWashing , calcHardDuty , calcTotal , uanNo ,  incentive , incentive);
        }

        private static String getConsolidatedReportNetTotal(){

        }

        private static String getConsolidatedReportTileFormat(String month , int daysInMonth , int pageIndex ,  int year , String establishmentName , String address , int estCode ){
            /*
             * estCode : pfRegNumber
             */
            return String.format("\n                                                   Register of Salary for the Month of %s %d,%d \n                                                                                                            Page %d\n  Name of the Estt. %s\n  Address     %s\n  Est_Code  : %d\n ===================================================================================================================================================================\n S_n|   Ins.   |PF |  Name of the Employee    |BASIC   |HRA    |CONV   |WASHING|HRD_DT|Arrier |Total| PF SALARY|          Deductions           |  Net   |  Signature\n    |    No.   |A/c|  (Father/Husband Name)   |        |       |       |       |      |(Ot_hr)|     |ESI SALARY|-------|------|------|---------| Amount |  ---------\n    |   POST   |No.|T_day =W.day  +E.L. +H.L. |        |INCENTI|       |       |      |O_time |     |          | E.S.I.| P.F. |I_TAX | Total   | Payable|  Date of\n    |   PF RT  |   |       C.L.   +CCL        |        |       |       |       |      |       |     |          |ADVANCE|P_TAX |S_DPT |Deduction|        |  Payment\n    |  UAN NO  |   |                          |        |       |       |       |      |       |     |          |       |      |S_TAX |         |        |\n===================================================================================================================================================================\n" , month , daysInMonth , year , pageIndex , establishmentName , address , estCode  );
        }


        private static String getStandaloneReportTileFormat(){

        }

        private static String getStandaloneReportInnerFormat(){

        }



        static void generateReport(long pfRegNumber , int year , String month , String regionOptional){
            try {

            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }

        }

    }

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

    public static void checkAndCreateDir(long pfRegNumber, int year, String month) {
        FileComponentHandler.createDir(PATH_MAIN, new String[]{
            "data", pfRegNumber + "", year + "", month
        });
        FileComponentHandler.createFile(PATH_MAIN, new String[]{
            "data", pfRegNumber + "", year + "", month, month + ".db"
        });

    }

    // employees
    public static void initiateEmployeesConnection(String pfRegNumber, String year, String month, String databaseName) {
        EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
            "data", pfRegNumber, year, month, databaseName
        }));

    }

    public static void closeEmployeeConnection() {
        EmployeeDatabaseHandler.closeConnection();
    }

    public static void executeCreateTableCommand(long pfRegNumber, int year, String month, String databaseName) {
        EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, month + ".db"}));
    }

    public static void executeFillTableCommand(String pfRegNumber, String year, String month, String dbName, String pathToPfCSVFile) {
        EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber, year, month, dbName}), pathToPfCSVFile);
    }

}
