package com.foxpro;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.foxpro.databaseManager.EmployeeDatabaseHandler;
import com.foxpro.fileManager.FileComponentHandler;


class GenerateReport{
    private interface Report{
        void generate();
        void add();
        void close();
    }


    String empDir;

    Establishment est ;


    // emp vars ;

    String memberId , reportName , father_husband_name ,
            name ;
    double total_basic,
            total_hra,
            total_conv,
            total_overtime,
            total_washingAllowance,
            calc_basic,
            calc_hra,
            calc_conv,
            calc_overtime,
            calc_washingAllowance,
            calc_incentive = 0,
            pf_salary, pf_deduction, esic_salary, esic_deduction, total_deduction, calc_salary, netPayableAmount,
            pf_paid_by_employee = 0, pf_paid_by_employer = 0, total_salary, attendance , totalDays ;
    int eps_salary , eps_amount;
    long uan;




    long pfReg;
    String month;
    int daysInMonth;


    String next_month;

    int next_year = 0;


    GenerateReport(long pfReg , int year , String month , String region_op , int daysInMonth , String next_month ){
        // initializing pfReg , month , daysInMonth for to be used inside naming of pf.xlsx ( sheet name )
        this.pfReg = pfReg;
        this.month = month;
        this.next_month = next_month;
        this.daysInMonth = daysInMonth;


        // initialise establishment
        est = EstablishmentFactory.getEstablishment(pfReg);



        // generate enpDir from the constructor or using generateReport function
        empDir = FileComponentHandler.generatePath(Config.getPathMain()  , new String[]{"data" , pfReg + "" , year + "" , region_op == null ? month : month + FileComponentHandler.OS_PATH_DELIMITER + region_op});
        reportName = String.format("%s_%d_%s" , pfReg+"" , year , region_op == null ? month : region_op + "_" + month);



        if ( next_month.equals("JAN")) {
            next_year = year + 1;
        }
        else{
            next_year = year;
        }




        // for creating next month directory and copying the .db file into it
        if (region_op == null) {

            FileComponentHandler.createDir(Config.getPathMain(), new String[]{
                "data", pfReg + "",next_year + "", next_month
            });

            /*
             * copy data from empDir to the next_month dir without calculations
             */
        } else {
            FileComponentHandler.createDir(Config.getPathMain(), new String[]{
                "data", pfReg + "",next_year + "", next_month, region_op
            });

        }

        /* database copied and changes made and connection closed at the same time */
        // Manager.copyDbAndClearCalculatedData(empDir  , month , empDir.replace(month , next_month) , next_month);
        // Manager.copyDbAndClearCalculatedData(empDir  , month , FileComponentHandler.generatePath(Config.getPathMain() , new String[]{"data" , pfReg + "" , next_year + "" , region_op == null ? next_month : next_month + FileComponentHandler.OS_PATH_DELIMITER + region_op}) , next_month);








        /*
         * initiate employees connection and close the connection -> closed from cmd
         */
        Manager.initiateEmployeesConnection(pfReg , year , month , region_op);



    }


    class FinalReport implements Report{

        int ncp_days;

        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        @Override
        public void generate() {
            try {
                fileWriter = new FileWriter(empDir + FileComponentHandler.OS_PATH_DELIMITER + "out_" + reportName + ".txt");
                bufferedWriter = new BufferedWriter(fileWriter);
            } catch (IOException iOException) {
                System.out.println(iOException);
            }
        }

        @Override
        public void add() {
            try{
                eps_salary = (int) ((calc_basic * 8.33) / 100);
                eps_salary = eps_salary > 15000 ? 15000 : eps_salary;

                pf_deduction = (int) pf_deduction;
                eps_amount = (int) ((eps_salary * 15) / 100);

                ncp_days = (int)(totalDays - attendance);
                bufferedWriter.write(uan + "#~#" + name.replaceFirst("M?(r|rs|s)?.?\\s+", "") + "#~#" + (int) calc_salary + "#~#" + (int) pf_salary + "#~#" + (int)eps_salary + "#~#" + (int)eps_salary + "#~#" + (int)pf_deduction + "#~#" + (int)(pf_deduction - eps_amount) + "#~#" + (int)eps_amount + "#~#" + ( ncp_days > 0 ? ncp_days : -1 * ncp_days ) + "#~#0\n");

            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }

        @Override
        public void close() {
            try{

                if (fileWriter != null) {
                    bufferedWriter.close();
                    fileWriter.close();
                }

            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }

    }


    class ESIC_Report implements Report{
        FileOutputStream xlsxFileOutputStream = null;
        XSSFWorkbook workbook = null;
        int esicExcelRowIndex = 0;
        XSSFSheet sheet ;
        Row row ;
        Cell cell ;
        CellStyle style ;

        @Override
        public void generate() {
            try{
            xlsxFileOutputStream = new FileOutputStream(empDir  + FileComponentHandler.OS_PATH_DELIMITER + "esic_" + reportName + ".xlsx");
            workbook = new XSSFWorkbook();

            sheet = workbook.createSheet("ESIC_data");
            sheet.setColumnWidth(0, 10000);
            sheet.setColumnWidth(1, 8000);
            sheet.setColumnWidth(2, 8000);
            sheet.setColumnWidth(3, 8000);
            sheet.setColumnWidth(4, 8000);
            sheet.setColumnWidth(5, 8000);

            Row header = sheet.createRow(esicExcelRowIndex++);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short)6);
            font.setBold(true);
            headerStyle.setFont(font);
            Cell headerCell = null;


            int i  = 0;
            for ( String s : new String[]{ "IP NUMBER" , "IP NAME" , "No. of days for which wages paid/payable during the month" , "TOTAL MONTHLY WAGES" , "Reason Code for Zero Working Days" , "Last Working Day"}){
                headerCell = header.createCell(i++);
                headerCell.setCellValue(s);
                headerCell.setCellStyle(headerStyle);
            }


            style = workbook.createCellStyle();
            style.setWrapText(true);
            }
            catch(FileNotFoundException fileNotFoundException){
                fileNotFoundException.printStackTrace();
            }

        }

        @Override
        public void add() {
                    row = sheet.createRow(esicExcelRowIndex++);


                    cell = row.createCell(0);
                    cell.setCellValue(uan);
                    cell.setCellStyle(style);

                    cell = row.createCell(1);
                    cell.setCellValue(name);
                    cell.setCellStyle(style);

                    cell = row.createCell(2);
                    cell.setCellValue(attendance);
                    cell.setCellStyle(style);


                    cell = row.createCell(3);
                    cell.setCellValue(esic_salary);
                    cell.setCellStyle(style);



                    if ( attendance == 0){

                        cell = row.createCell(4);
                        cell.setCellValue("1");
                        cell.setCellStyle(style);

                    }

        }

        @Override
        public void close() {
            try{
                workbook.write(xlsxFileOutputStream);
                workbook.close();
                xlsxFileOutputStream.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }

    }


    class PF_Report implements Report{
        /*
         * one single change has to be addressed to all the functions all functions interconnected
         */
        FileOutputStream xlsxFileOutputStream = null;
        XSSFWorkbook workbook = null;
        int pfExcelRowIndex = 0;
        XSSFSheet sheet ;
        Row row ;
        Cell cell ;
        CellStyle style ;

        int serial_no = 1;

        Object[] arr;
        XSSFFont font ;



        @Override
        public void generate() {
            try {
                xlsxFileOutputStream = new FileOutputStream(empDir + FileComponentHandler.OS_PATH_DELIMITER + "pf_" + reportName + ".xlsx");
                workbook = new XSSFWorkbook();


                sheet = workbook.createSheet(String.format("%s %s %d %s" , pfReg+"" , month , daysInMonth , est.companyName  ));
                for ( int i = 0; i < 27; i++){
                    if ( i == 0){
                        sheet.setColumnWidth(i, 550);
                    }
                    else if ( i == 1){
                        sheet.setColumnWidth(i, 1400);
                    }
                    else if ( i == 2){
                        sheet.setColumnWidth(i, 1400);
                    }
                    else if ( i == 4){
                        sheet.setColumnWidth(i, 2400);
                    }
                    else if ( i == 5){
                        sheet.setColumnWidth(i, 1900);
                    }
                    else{
                        sheet.setColumnWidth(i, 900);
                    }

                }


                Row header = sheet.createRow(pfExcelRowIndex++);

                CellStyle headerStyle = workbook.createCellStyle();
                // headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
                // headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                font = ((XSSFWorkbook) workbook).createFont();
                font.setFontName("Arial");
                font.setFontHeightInPoints((short) 5.5);
                font.setBold(true);
                headerStyle.setFont(font);
                headerStyle.setAlignment(HorizontalAlignment.LEFT);
                Cell headerCell = null;

                int i = 0;
                for (String s : new String[]{"S_No" , "ESIC REG" , "UAN" , "PF ACC" , "EMP. NAME" ,"FATH./HUS. NAME" , "ATT." ,   "BASIC" , "C_BASIC" , "HRA" , "C_HRA" , "INC" , "CONV" , "C_CONV" , "WA_ALL" , "C_WA_ALL." , "H_DTY" , "C_H_DTY" , "T_SAL" , "C_SAL" , "PF_SAL" , "ESIC_SAL" , "ESIC_ADV" , "PF_ADV" , "T_DDN" , "N.P.AMT"}) {
                    headerCell = header.createCell(i++);
                    headerCell.setCellValue(s);
                    headerCell.setCellStyle(headerStyle);
                }

                style = workbook.createCellStyle();
                style.setWrapText(true);



                // set CellStyle "style" ( which is used in the add function below ) , same font added because to have uniformity across all the text
                font.setBold(false);
                style.setFont(font);
                style.setAlignment(HorizontalAlignment.LEFT);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

        }


        @Override
        public void add() {
            row = sheet.createRow(pfExcelRowIndex++);

            arr = new Object[]{
                serial_no++ , est.esicRegNumber , uan , (Long.parseLong(memberId.substring(5)))% 100000000 , name , father_husband_name , attendance ,total_basic , calc_basic , total_hra , calc_hra , calc_incentive , total_conv , calc_conv , total_washingAllowance , calc_washingAllowance , total_overtime , calc_overtime , total_salary , calc_salary , pf_salary , esic_salary , esic_deduction , pf_deduction , total_deduction , netPayableAmount
            };


            for ( int i = 0; i < arr.length; i++){
                cell = row.createCell(i);
                cell.setCellValue(arr[i] + "");
                cell.setCellStyle(style);
            }



        }

        @Override
        public void close() {
            try{


                addTotal();


                workbook.write(xlsxFileOutputStream);
                workbook.close();
                xlsxFileOutputStream.close();
            }
            catch(IOException ioException){
                ioException.printStackTrace();
            }
        }


        public void addTotal() {
            try {
                ResultSet emp_sum = (ResultSet) EmployeeDatabaseHandler.getSumTotal();

                int[] arr_sum = new int[]{
                    (int) emp_sum.getDouble("sum_attendance"),
                    (int) emp_sum.getDouble("sum_basic"),
                    (int) emp_sum.getDouble("sum_calc_basic"),
                    (int) emp_sum.getDouble("sum_hra"),
                    (int) emp_sum.getDouble("sum_calc_hra"),
                    (int) emp_sum.getDouble("sum_calc_incentive"),
                    (int) emp_sum.getDouble("sum_convence"),
                    (int) emp_sum.getDouble("sum_calc_convence"),
                    (int) emp_sum.getDouble("sum_washingAllowance"),
                    (int) emp_sum.getDouble("sum_calc_washingAllowance"),
                    (int) emp_sum.getDouble("sum_overtime"),
                    (int) emp_sum.getDouble("sum_calc_overtime"),
                    (int) emp_sum.getDouble("sum_totalSalary"),
                    (int) emp_sum.getDouble("sum_calc_salary"),
                    // (int) emp_sum.getDouble("sum_totalDays"),
                    (int) emp_sum.getDouble("sum_pf_salary"),
                    (int) emp_sum.getDouble("sum_esic_salary"),
                    (int) emp_sum.getDouble("sum_esicDeduction"),
                    (int) emp_sum.getDouble("sum_pfDeduction"),
                    (int) emp_sum.getDouble("sum_totalDeduction"),
                    (int) emp_sum.getDouble("sum_netPayableAmount")
                };

                // adding to excel
                // row = sheet.createRow(pfExcelRowIndex++);
                row = sheet.createRow(pfExcelRowIndex++);
                row = sheet.createRow(pfExcelRowIndex++);

                // cell = row.createCell(0);
                // cell.setCellValue("NET TOTAL");
                // cell.setCellStyle(style);


                for ( int i = 0; i < arr.length; i++){

                    if ( i == 1){
                        cell = row.createCell(i);
                        cell.setCellValue("NET TOTAL");
                        cell.setCellStyle(style);

                    }
                    else if ( i >= 6){
                        cell = row.createCell(i);
                        cell.setCellValue(arr_sum[i-6]);
                        cell.setCellStyle(style);
                    }
                    else {
                        cell = row.createCell(i);
                    }

                }





            }
            catch(SQLException exception){
                exception.printStackTrace();
            }

        }

    }


    public void generateReport() throws SQLException {
        Report[] reports = new Report[]{
            new PF_Report(), new ESIC_Report(), new FinalReport()
        };

        for (Report report : reports) {
            report.generate();
        }

        ResultSet emp = (ResultSet) EmployeeDatabaseHandler.getAllEmployeeDetails();


        while (emp.next()) {
            total_basic = emp.getDouble("basic");
            total_hra = emp.getDouble("hra");
            total_conv = emp.getDouble("convence");
            total_washingAllowance = emp.getDouble("washingAllowance");
            total_overtime = emp.getDouble("overtime");
            total_salary = emp.getDouble("totalSalary");
            pf_salary = emp.getDouble("pf_salary");
            esic_deduction = emp.getDouble("esicDeduction");
            pf_deduction = emp.getDouble("pfDeduction");
            total_deduction = emp.getDouble("totalDeduction");
            netPayableAmount = emp.getDouble("netPayableAmount");
            name = emp.getString("name");
            father_husband_name = emp.getString("father_husband_name");
            esic_salary = emp.getDouble("esic_salary");
            memberId = emp.getString("memberId");
            attendance = (double)(emp.getInt("attendance"));
            calc_basic = emp.getDouble("calc_basic");
            calc_hra = emp.getDouble("calc_hra");
            calc_conv = emp.getDouble("calc_convence");
            calc_washingAllowance = emp.getDouble("calc_washingAllowance");
            calc_overtime = emp.getDouble("calc_overtime");
            calc_salary = emp.getDouble("calc_salary");
            uan =  emp.getLong("uan");
            calc_incentive = emp.getDouble("calc_incentive");

            //sacttering emp.getFunctions into the variables;
            for (Report report : reports) {
                report.add();
            }
        }

        for (Report report : reports) {
            report.close();
        }

    }




}