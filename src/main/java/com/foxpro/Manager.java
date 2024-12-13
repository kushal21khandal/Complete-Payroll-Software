package com.foxpro;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;
import com.foxpro.databaseManager.EstablishmentDatabaseHandler;
import com.foxpro.fileManager.FileComponentHandler;

class Manager {

    static class GenerateReport {
        /*
         * connection established and closed inside generateReport function
         */

        private static String getConsolidatedReportInnerFormat(
            int employeeIndex ,
            long esicRegNumber ,
            String employeeName ,
            double basic ,
            double hra ,
            double conv ,
            double washingAllowance ,
            double hardDuty ,
            double totalWithoutReduction ,
            double pfSalary ,
            double esicAdvance ,
            double pfDeduction ,
            double totalDeduction ,
            double netPayableAmount ,
            String fahtersName ,
            double esicSalary ,
            int pfAccNo ,
            int daysWorked ,
            int actualDays ,
            int complementaryDays ,
            double calcBasic ,
            double calcHra ,
            double calcConv ,
            double calcWashing ,
            double calcHardDuty ,
            double calcTotal ,
            long uanNo ,
            double incentive
            ){

            return String.format(" %d   %d      %s              %f    %f    %f    %f    %f          %f    %f       %f     %f  -          %f      %f\n                     %s                                                                 %f      -       -     -\n               %d %d=%d        +  %d      %f    %f     %f    %f    %f          %f                            -\n%d           0.0                                         %f\n                                                      0    %f\n _________________________________________________________________________________________________________________________________________________________\n"
            , employeeIndex , esicRegNumber , employeeName , basic ,hra , conv , washingAllowance , hardDuty , totalWithoutReduction , pfSalary , esicAdvance , pfDeduction , totalDeduction , netPayableAmount , fahtersName , esicSalary , pfAccNo , daysWorked , actualDays , complementaryDays , calcBasic , calcHra , calcConv , calcWashing , calcHardDuty , calcTotal , uanNo ,  incentive , incentive);
        }

        private static String getConsolidatedReportNetTotal(
            double total_basic ,
            double hra ,
            double conv ,
            double washingAllowance ,
            double hardDuty ,
            double total_salary ,
            double daysWorked ,
            double actualDays ,
            double complementaryDays ,
            double calc_basic ,
            double calc_hra ,
            double calc_conv ,
            double calc_washingAllowance ,
            double calc_hardDuty ,
            double calcTotal ,
            double pfSalary ,
            double esicAdvance ,
            double pf_deduction ,
            double total_deduction ,
            double netPayableAmount ,
            double total_incentive_paid ,
            double total_esic_salary
        ){
            return String.format("\n===================================================================================================================================================================\n                                                 %f   %f  %f   %f   %f         %f\n       NET TOTAL  %f %f+   0.0+  %f    %f   %f  %f   %f   %f         %f  %f     %f   %f      0    %f    %f\n                            0.0+   0.0                    %f                                        %f                0      0\n                                                      0   %f                                                                     0\n ===================================================================================================================================================================\n" ,
             total_basic ,
             hra ,
             conv ,
             washingAllowance ,
             hardDuty ,
             total_salary ,
             daysWorked ,
             actualDays ,
             complementaryDays ,
             calc_basic ,
             calc_hra ,
             calc_conv ,
             calc_washingAllowance ,
             calc_hardDuty ,
             calcTotal ,
             pfSalary ,
             esicAdvance ,
             pf_deduction ,
             total_deduction ,
             netPayableAmount ,
             total_incentive_paid ,
             total_esic_salary ,
            total_incentive_paid
            );
        }

        private static String getConsolidatedReportTileFormat(
            String month ,
            int daysInMonth ,
            int pageIndex ,
            int year ,
            String establishmentName ,
            String address ,
            int estCode
            ){
            /*
             * estCode : pfRegNumber
             */
            return String.format("\n                                                   Register of Salary for the Month of %s %d,%d \n                                                                                                            Page %d\n  Name of the Estt. %s\n  Address     %s\n  Est_Code  : %d\n ===================================================================================================================================================================\n S_n|   Ins.   |PF |  Name of the Employee    |BASIC   |HRA    |CONV   |WASHING|HRD_DT|Arrier |Total| PF SALARY|          Deductions           |  Net   |  Signature\n    |    No.   |A/c|  (Father/Husband Name)   |        |       |       |       |      |(Ot_hr)|     |ESI SALARY|-------|------|------|---------| Amount |  ---------\n    |   POST   |No.|T_day =W.day  +E.L. +H.L. |        |INCENTI|       |       |      |O_time |     |          | E.S.I.| P.F. |I_TAX | Total   | Payable|  Date of\n    |   PF RT  |   |       C.L.   +CCL        |        |       |       |       |      |       |     |          |ADVANCE|P_TAX |S_DPT |Deduction|        |  Payment\n    |  UAN NO  |   |                          |        |       |       |       |      |       |     |          |       |      |S_TAX |         |        |\n===================================================================================================================================================================\n" , month , daysInMonth , year , pageIndex , establishmentName , address , estCode  );
        }


    //     private static String getStandaloneReportTileFormat(){

    //     }
    //    private static String getStandaloneReportInnerFormat(){

    //     }



        static void generateReport(long pfRegNumber , int year , String month , String regionOptional) throws  SQLException , FileNotFoundException , IOException{

            /*
             * 2 * word documents required to be created
             * 1 : consolidated payslip
             * 2 : per person pay slip
             *
             * for every page in consolidated payslip space for 8 ( i.e 7 persons , 1 for the header )
             * and the ending should include the total of everything , which itself is a separate header
             *
             * for every per person standalone payslip , format not provided yet
             *
             *
             * watch the calculations and them take steps
             *
             *
             * FIRST :
             * get the total format and set the required variables
             * do for consolidated then do for standalone
             */


            int pageIndex = 1;
            int employeeIndex = 0 , employeeIndexAccurator = -1;

            int eps_salary = 0 ;
            int pfDeduction , eps_amount;


            XWPFDocument consolidatedPayslip = new XWPFDocument();
            FileOutputStream fout = null ;


            FileWriter output = null;

            String consolidatedReportTitle;
            String consolidatedInnerData;
            String consolidatedReportNetTotal;

            // Establishment details for header
            ResultSet estab = (ResultSet)Manager.getEstablishmentDetails(pfRegNumber);


            // establishing employee connection before quering
            Manager.initiateEmployeesConnection(pfRegNumber, year, month , regionOptional);
            ResultSet emp = (ResultSet)EmployeeDatabaseHandler.getAllEmployeeDetails();
            ResultSet emp_sum = (ResultSet)EmployeeDatabaseHandler.getSumTotal();


            boolean hasNextEmployee = true;




            try {

                fout = new FileOutputStream( regionOptional == null ? FileComponentHandler.generatePath(PATH_MAIN , new String[]{"data" , pfRegNumber + "" , year + "" , month ,"combined_" + pfRegNumber + "_" + month + "_" + year + ".docx"}) : FileComponentHandler.generatePath(PATH_MAIN , new String[]{ "data" , pfRegNumber + "" , year + "" , month , regionOptional ,"conbined_" + pfRegNumber + "_" + regionOptional + "_" + month + "_" + year + ".docx"}));


                output = new FileWriter( regionOptional == null ? FileComponentHandler.generatePath(PATH_MAIN , new String[]{"data" , pfRegNumber + "" , year + "" , month ,"out_" + pfRegNumber + "_" + month + "_" + year + ".txt"}) : FileComponentHandler.generatePath(PATH_MAIN , new String[]{ "data" , pfRegNumber + "" , year + "" , month , regionOptional ,"out_" + pfRegNumber + "_" + regionOptional + "_" + month + "_" + year + ".txt"}));

                BufferedWriter bufferedWriter = new BufferedWriter(output);


                // while ( emp.next()){
                /* beacause in the execution of first if condition : one employee will get missed */
                while (hasNextEmployee == true){


                    if (employeeIndex % 8 == 0) {
                        consolidatedReportTitle = getConsolidatedReportTileFormat(month, (int) emp.getDouble("totalDays"), pageIndex++, year, estab.getString("companyName"), estab.getString("address"), estab.getInt("pfRegNumber"));


                        XWPFParagraph header = consolidatedPayslip.createParagraph();
                        header.setAlignment(ParagraphAlignment.CENTER);
                        header.createRun().setText(consolidatedReportTitle);

                        employeeIndexAccurator++;
                    }
                    else{
                        consolidatedInnerData = getConsolidatedReportInnerFormat(
                            employeeIndex  - employeeIndexAccurator ,
                            (long) estab.getInt("esicRegNumber") ,
                            emp.getString("name")  ,
                            emp.getDouble("basic") ,
                            emp.getDouble("hra") ,
                            emp.getDouble("convence")  ,
                            emp.getDouble("washingAllowance") ,
                            emp.getDouble("overtime") ,
                            emp.getDouble("totalSalary") ,
                            emp.getDouble("pf_salary") ,
                            emp.getDouble("esicDeduction") ,
                            emp.getDouble("pfDeduction") ,
                            emp.getDouble("totalDeduction") ,
                            emp.getDouble("netPayableAmount") ,
                            emp.getString("father/husband_name") ,
                            emp.getDouble("esic_salary") ,
                            (int) ( emp.getDouble("memberId") % 100000000  ) ,
                            (int) ( emp.getDouble("attendance")) ,
                            (int) ( emp.getDouble("attendance")) ,
                            0 ,
                            emp.getDouble("calc_basic") ,
                            emp.getDouble("calc_hra") ,
                            emp.getDouble("calc_convence") ,
                            emp.getDouble("calc_washingAllowance") ,
                            emp.getDouble("calc_overtime") ,
                            emp.getDouble("calc_salary") ,
                            (long) emp.getInt("uan") ,
                            emp.getDouble("calc_incentive")
                        );
                        XWPFParagraph data = consolidatedPayslip.createParagraph();
                        data.setAlignment(ParagraphAlignment.BOTH);
                        data.createRun().setText(consolidatedInnerData);

                        eps_salary = (int)(emp.getDouble("calc_basic") * 8.33) /100;
                        eps_salary = eps_salary > 15000 ? 15000 : eps_salary ;

                        pfDeduction = (int)emp.getDouble("pfDeduction");
                        eps_amount = (int)( eps_salary * 15) /100;

                        bufferedWriter.write(emp.getInt("uan")+"#~#"+emp.getString("name") +"#~#" +emp.getDouble("calc_salary") + "#~#" + emp.getDouble("pf_salary") + "#~#" + eps_salary + "#~#" +eps_salary + "#~#" + pfDeduction + "#~#" +  (pfDeduction - eps_amount) + "#~#" + eps_amount + "#~#" + ( emp.getDouble("totalDays") - emp.getDouble("attendance")  )+ "#~#0\n");

                        hasNextEmployee = emp.next();
                    }

                    employeeIndex++;

                }






                consolidatedReportNetTotal = getConsolidatedReportNetTotal(
                    emp_sum.getDouble("sum_basic") ,
                    emp_sum.getDouble("sum_hra"),
                    emp_sum.getDouble("sum_convence"),
                    emp_sum.getDouble("sum_washingAllowance"),
                    emp_sum.getDouble("sum_overtime"),
                    emp_sum.getDouble("sum_totalSalary"),
                    emp_sum.getDouble("sum_totalDays"),
                    emp_sum.getDouble("sum_attendance"),
                    0 ,
                    emp_sum.getDouble("sum_calc_basic"),
                    emp_sum.getDouble("sum_calc_hra"),
                    emp_sum.getDouble("sum_calc_convence"),
                    emp_sum.getDouble("sum_calc_washingAllowance"),
                    emp_sum.getDouble("sum_calc_overtime"),
                    emp_sum.getDouble("sum_calc_salary"),
                    emp_sum.getDouble("sum_pf_salary"),
                    emp_sum.getDouble("sum_esicDeduction"),
                    emp_sum.getDouble("sum_pfDeduction"),
                    emp_sum.getDouble("sum_totalDeduction"),
                    emp_sum.getDouble("sum_netPayableAmount"),
                    emp_sum.getDouble("sum_calc_incentive"),
                    emp_sum.getDouble("sum_esic_salary")
                );


                XWPFParagraph sum_total = consolidatedPayslip.createParagraph();
                sum_total.setAlignment(ParagraphAlignment.BOTH);
                sum_total.createRun().setText(consolidatedReportNetTotal);



                consolidatedPayslip.write(fout);








                bufferedWriter.close();


            } catch (SQLException e) {
                e.printStackTrace();
            }
            finally{
                Manager.closeEmployeeConnection();
                if ( fout != null){
                    fout.close();
                }
                consolidatedPayslip.close();

                if ( output != null){
                    output.close();
                }

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









    // employees
    public static void checkAndCreateDir(long pfRegNumber, int year, String month , String regionOptional) {

        if ( regionOptional == null){


            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month
            });
            FileComponentHandler.createFile(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, month + ".db"
            });
        }
        else{
            FileComponentHandler.createDir(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month , regionOptional
            });
            FileComponentHandler.createFile(PATH_MAIN, new String[]{
                "data", pfRegNumber + "", year + "", month, regionOptional ,month + ".db"
            });

        }


    }

    public static void initiateEmployeesConnection(long pfRegNumber, int year, String month, String region_optional) {

        if ( region_optional != null){
            EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
            "data", pfRegNumber+"", year+"", month, region_optional , month+".db"
        }));

        }
        else{
            EmployeeDatabaseHandler.initiateConnection(FileComponentHandler.generatePath(PATH_MAIN, new String[]{
            "data", pfRegNumber+"", year+"", month,  month+".db"
        }));

        }

    }

    public static void closeEmployeeConnection() {
        EmployeeDatabaseHandler.closeConnection();
    }

    public static void executeCreateTableCommand(long pfRegNumber, int year, String month, String region_optional) {
        if ( region_optional != null){
            EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, region_optional,month + ".db"}));
        }
        else{
            EmployeeDatabaseHandler.executeCreateMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"commands", "table_pfSiteCSV.txt"}), FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber + "", year + "", month, month + ".db"}));

        }
    }

    public static void executeFillTableCommand(long pfRegNumber, int year, String month, String region_optional, String pathToPfCSVFile) {
        if ( region_optional != null){
            EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber +"", year +"", month , region_optional, month+".db"}), pathToPfCSVFile);
        }
        else{
            EmployeeDatabaseHandler.executeFillMonthTable(FileComponentHandler.generatePath(PATH_MAIN, new String[]{"data", pfRegNumber +"", year +"", month, month+".db"}), pathToPfCSVFile);
        }
    }

}
